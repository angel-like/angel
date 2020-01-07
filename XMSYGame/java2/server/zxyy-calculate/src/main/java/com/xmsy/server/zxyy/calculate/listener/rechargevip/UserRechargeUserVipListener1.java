package com.xmsy.server.zxyy.calculate.listener.rechargevip;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.server.zxyy.calculate.constant.SysConstant;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.service.UserVipService;
import com.xmsy.server.zxyy.calculate.utils.EventRetryUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * .监听充值订单确认操作
 * 判断用户是否可以升级vip
 * @author adu
 *
 */
@Slf4j
@Component
public class UserRechargeUserVipListener1 {

	@Autowired
	private UserService userService;
	@Autowired
	private UserVipService userVipService;
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
	@RabbitListener(queues = SysConstant.RECHARGE_VIP_QUEUE)
	public void receiveMessage(RechargeRebateMessage rechargeRebateMessage, Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			if (userRechargeUserVipOperation(rechargeRebateMessage)) {
				// 手动签收
				log.info("UserRechargeUserVipListener1  监听充值订单确认事件，接收到消息:{} deliveryTag {}", rechargeRebateMessage, deliveryTag);
				channel.basicAck(deliveryTag, false);
				SysConstant.MESSAGE.remove(rechargeRebateMessage.getMessageId());
			} else {
				eventRetryUtil.retry(deliveryTag, rechargeRebateMessage, channel, "UserRechargeUserVipListener1");
			}
		} catch (Exception e) {
			eventRetryUtil.retry(deliveryTag, rechargeRebateMessage, channel, "UserRechargeUserVipListener1");
		}
	}

	public boolean userRechargeUserVipOperation(RechargeRebateMessage rechargeRebateMessage) {
		try {
			UserEntity user = userService.selectById(rechargeRebateMessage.getUserId());
			if(user==null) {
				log.info("[userRechargeUserVipOperation1] 会员没有找到  RechargeRebateMessage {} ", rechargeRebateMessage);
				return false;
			}
			userVipService.userUpgradeVip(user);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[userRechargeUserVipOperation1] Exception rechargeRebateMessage：{} ExceptionMsg {}", rechargeRebateMessage,
					e.getMessage());
			return false;
		}
		return true;
	}
}
