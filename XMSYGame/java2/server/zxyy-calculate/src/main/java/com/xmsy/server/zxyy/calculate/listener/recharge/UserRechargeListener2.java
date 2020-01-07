package com.xmsy.server.zxyy.calculate.listener.recharge;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.server.zxyy.calculate.common.annotation.QrOrderConfirmRepeat;
import com.xmsy.server.zxyy.calculate.common.utils.Constant;
import com.xmsy.server.zxyy.calculate.common.utils.Constant.OrderStatus;
import com.xmsy.server.zxyy.calculate.constant.SysConstant;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.calculate.modules.manager.push.PushService;
import com.xmsy.server.zxyy.calculate.modules.manager.push.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.service.UserVipService;
import com.xmsy.server.zxyy.calculate.utils.EventRetryUtil;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .监听用户注册操作
 *
 * @author Administrator
 *
 */
@Slf4j
@Component
public class UserRechargeListener2 {

	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private EventRetryUtil eventRetryUtil;
	@Autowired
	private UserVipService userVipService;
	@Autowired
	private PushService pushService;
	@Autowired
	private MessageUserService messageUserService;

	/**
	 * 监听
	 *
	 * @param receiveMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = SysConstant.RECHAGE_QUEUE)
	public void receiveMessage(CallbackMessage callbackMessage, Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			if (userRechargeOperation(callbackMessage)) {
				// 手动签收
				log.info("UserRechargeListener2  注册监听扫码充值事件，接收到消息:{} deliveryTag {}", callbackMessage, deliveryTag);
				channel.basicAck(deliveryTag, false);
				SysConstant.MESSAGE.remove(callbackMessage.getMessageId());
			} else {
				eventRetryUtil.retry(deliveryTag, callbackMessage, channel, "UserRechargeListener2");
			}
		} catch (Exception e) {
			eventRetryUtil.retry(deliveryTag, callbackMessage, channel, "UserRechargeListener2");
		}
	}

	@QrOrderConfirmRepeat
	public boolean userRechargeOperation(CallbackMessage callbackMessage) {
		// 修改订单状态
		OrderRechargeEntity param = new OrderRechargeEntity().setOrderNo(callbackMessage.getOrderNo());
		OrderRechargeEntity result = orderRechargeService.selectOne(new EntityWrapper<OrderRechargeEntity>(param));
		if (null == result) {
			log.error("[userRechargeOperation2] 该订单号对应的订单不存在 callbackMessage：{}", callbackMessage);
			return false;
		}
		if (result.getStatus() == OrderStatus.COMPLETE.getValue()) {
			log.info("[userRechargeOperation2] 该订单号对应的订单已经被确认  callbackMessage {} ", callbackMessage);
			return true;
		}
		result.setMerchantOrderNo(callbackMessage.getMerchantNo());
		result.setStatus(OrderStatus.COMPLETE.getValue());
		result.setRechargeTime(new Date());
		try {
			if (!orderRechargeService.orderRechargeConfirm(result)) {
				return false;
			}
			Date FAIL_TIME = DateUtil.nextWeek(); // 邮件失效时间
			Date EFFECT_TIME = new Date(); // 邮件生效时间
			try {
				// 邮件推送
//				MessageManagementEntity messageManagement = new MessageManagementEntity()
//						.setUserAccount(result.getUserAccount())
//						.setContent(String.format(Constant.SUCCUSS_CONTENT, result.getOrderNo(), result.getAmount()))
//						.setContentType(Constant.CONTENT_TYPE).setTargetObject(Constant.TARGET_OBJECT).setEnable(true)
//						.setTitle(Constant.TITLE).setEffectTime(EFFECT_TIME).setFailureTime(FAIL_TIME);
//				messageManagementService.save(messageManagement, result.getUserId());
				MessageUserEntity messageUserEntity = new MessageUserEntity();
				messageUserEntity
						.setUserAccount(result.getUserAccount())
						.setStatus(false).setDeleteMessage(false).setReceive(true)
						.setContent(String.format(Constant.SUCCUSS_CONTENT, result.getOrderNo(), result.getAmount()+result.getDiscountAmount().longValue()))
						.setContentType(Constant.CONTENT_TYPE).setUserId(result.getUserId()).setMessageId(Long.valueOf(0))
						.setTitle(Constant.TITLE).setEffectTime(EFFECT_TIME).setFailureTime(FAIL_TIME);
				messageUserService.insert(messageUserEntity);
				UserEntity pushMessage = new UserEntity();
				pushMessage.setId(result.getUserId());
				pushMessage.setCoin(new BigDecimal(result.getAmount())
						.add(result.getDiscountAmount() == null ? BigDecimal.ZERO : result.getDiscountAmount())
						.multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
				pushMessage.setUnreadNum(1);
				UserInfoMessage message = new UserInfoMessage(pushMessage);
				log.info("[扫码充值订单确认->用户未读消息-推送消息2] message {}", message);
				pushService.pushExchange(message);
			} catch (Exception e) {
				log.error("[扫码充值订单确认,邮件和消息处理2] Exception", e);
			}
			try {
				userVipService.userUpgradeVip(result.getUserId());
			} catch (Exception e) {
				log.error("[扫码充值订单确认,vip自动升级处理2] Exception", e);
			}
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				log.info("[userRechargeOperation2] 重复回调稽查订单重复");
				return true;
			}
			log.error("[userRechargeOperation2] Exception callbackMessage：{} ExceptionMsg {}", callbackMessage,
					e.getMessage());
			return false;
		}
		return true;
	}
}
