package com.xmsy.server.zxyy.calculate.listener.rechargerebate;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.server.zxyy.calculate.common.annotation.QrOrderConfirmRepeat;
import com.xmsy.server.zxyy.calculate.common.utils.Constant.OrderStatus;
import com.xmsy.server.zxyy.calculate.common.utils.MathUtil;
import com.xmsy.server.zxyy.calculate.constant.SysConstant;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.calculate.utils.EventRetryUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * .监听充值订单确认操作
 * 
 * @author adu
 *
 */
@Slf4j
@Component
public class UserRechargeRebateListener1 {

	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private EventRetryUtil eventRetryUtil;

	/**
	 * 监听
	 *
	 * @param receiveMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = SysConstant.RECHARGE_REBATE_QUEUE)
	public void receiveMessage(RechargeRebateMessage rechargeRebateMessage, Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			if (userRechargeRebateOperation(rechargeRebateMessage)) {
				// 手动签收
				log.info("UserRechargeRebateListener1  监听充值订单确认事件，接收到消息:{} deliveryTag {}", rechargeRebateMessage, deliveryTag);
				channel.basicAck(deliveryTag, false);
				SysConstant.MESSAGE.remove(rechargeRebateMessage.getMessageId());
			} else {
				eventRetryUtil.retry(deliveryTag, rechargeRebateMessage, channel, "UserRechargeRebateListener1");
			}
		} catch (Exception e) {
			eventRetryUtil.retry(deliveryTag, rechargeRebateMessage, channel, "UserRechargeRebateListener1");
		}
	}

	@QrOrderConfirmRepeat
	public boolean userRechargeRebateOperation(RechargeRebateMessage rechargeRebateMessage) {
		// 修改订单状态
		OrderRechargeEntity param = new OrderRechargeEntity().setOrderNo(rechargeRebateMessage.getOrderNo());
		OrderRechargeEntity result = orderRechargeService.selectOne(new EntityWrapper<OrderRechargeEntity>(param));
		if (null == result) {
			log.error("[userRechargeRebateOperation1] 该订单号对应的订单不存在 RechargeRebateMessage：{}", rechargeRebateMessage);
			return false;
		}
		if (result.getStatus() != OrderStatus.COMPLETE.getValue()) {
			log.info("[userRechargeRebateOperation1] 该订单号对应的订单未被确认  RechargeRebateMessage {} ", rechargeRebateMessage);
			return false;
		}
		try {
			UserEntity agentParam = new UserEntity(); 
			agentParam.setId(rechargeRebateMessage.getAgentId());
			UserEntity agentUser = agentParam.selectById();
			if(agentUser==null) {
				log.info("[userRechargeRebateOperation1] 代理商没有找到  RechargeRebateMessage {} ", rechargeRebateMessage);
				return false;
			}
			if(!agentUser.getAgentEnable()) {
				log.info("[userRechargeRebateOperation1] 代理商已经取消代理  RechargeRebateMessage {} ", rechargeRebateMessage);
				return true;
			}
			Map<String, Object> agentHierachy = userRecommendationService.getAgentHierarchyByUserId(rechargeRebateMessage.getAgentId());
			if(agentHierachy==null) {
				log.info("[userRechargeRebateOperation1] 代理商层级没有找到  RechargeRebateMessage {} ", rechargeRebateMessage);
				return false;
			}
			//充值金额
			BigDecimal amount = MathUtil.getBigDecimal(result.getAmount());
			//充值返佣比例
			BigDecimal rate = MathUtil.getBigDecimal(agentHierachy.get("commission"));
			amount=amount.multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
			try {
				userService.updateAgentCommission(rechargeRebateMessage.getUserId(),agentUser, amount);
			} catch (Exception e) {
				log.error("[userRechargeRebateOperation1] Exception rechargeRebateMessage：{} ExceptionMsg {}", rechargeRebateMessage,
						e.getMessage());
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[userRechargeRebateOperation1] Exception rechargeRebateMessage：{} ExceptionMsg {}", rechargeRebateMessage,
					e.getMessage());
			return false;
		}
		return true;
	}
}
