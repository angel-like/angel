package com.xmsy.server.zxyy.manager.mq;

import java.util.UUID;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.common.bean.message.FortuneMessage;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.common.bean.message.RegisterMessage;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.server.zxyy.manager.config.RabbitmqConfig;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * .mq客户端，发送信息
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class MqClient {

	public static final int retry_time = 3;

	@Autowired
	private RabbitTemplate rabbitTemplate;


	@PostConstruct
	public void initRabbitTemplate() {
		// 设置生产者消息确认
		rabbitTemplate.setConfirmCallback(new ConfirmCallback());
	}

	/**
	 * 系统配置变更消息推送
	 * 
	 * @param message
	 */
	public void sysConfigPush(SysConfigMessage message) {
		String messageId = UUID.randomUUID().toString();
		message.setMessageId(messageId);
		message.setCreateTime(DateUtil.now());
		int retry = 0;
		while (retry < retry_time) {
			try {
				rabbitTemplate.convertAndSend("", RabbitmqConfig.SYSCONFIG_QUEUE, message,
						new CorrelationData(messageId));
				break;
			} catch (Exception e) {
				retry++;
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					log.error("[系统配置变更] sysConfigPush message {}", message, e);
				}
				log.error("[系统配置变更] Thread.sleep(500L); ");
			}
		}
		log.info("[系统配置变更] message {}", message);
	}

	/**
	 * 注册消息推送
	 * 
	 * @param message
	 */
	public void registerPush(RegisterMessage message) {
		String messageId = UUID.randomUUID().toString();
		message.setMessageId(messageId);
		message.setCreateTime(DateUtil.now());
		int retry = 0;
		while (retry < retry_time) {
			try {
				rabbitTemplate.convertAndSend("", RabbitmqConfig.REGISTER_QUEUE, message,
						new CorrelationData(messageId));
				break;
			} catch (Exception e) {
				retry++;
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					log.error("[注册推送] registerPush message {}", message, e);
				}
				log.error("[注册推送] Thread.sleep(500L); ");
			}
		}
		log.info("[注册推送] message {}", message);
	}

	/**
	 * 充值消息推送处理
	 * 
	 * @param message
	 */
	public boolean orderRechargePush(CallbackMessage message) {
		log.info("[充值消息mq推送处理] message {}", message);
		String messageId = UUID.randomUUID().toString();
		message.setMessageId(messageId);
		message.setCreateTime(DateUtil.now());
		int retry = 0;
		while (retry < retry_time) {
			try {
				rabbitTemplate.convertAndSend("", RabbitmqConfig.RECHARGE_QUEUE, message,
						new CorrelationData(messageId));
				return true;
			} catch (Exception e) {
				retry++;
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e1) {
					log.error("[充值消息mq推送处理] orderHandle message {}", message, e);
				}
				log.error("[充值消息mq推送处理] Thread.sleep(500L); ");
			}
		}
		return false;
	}

	/**
	 * 代理商返佣推送
	 * 
	 * @param message
	 */
	public boolean agentRebatePush(RechargeRebateMessage message) {
		log.info("[代理商返佣推送处理] message {}", message);
		String messageId = UUID.randomUUID().toString();
		message.setMessageId(messageId);
		message.setCreateTime(DateUtil.now());
		int retry = 0;
		while (retry < retry_time) {
			try {
				rabbitTemplate.convertAndSend("", RabbitmqConfig.RECHARGE_REBATE_QUEUE, message,
						new CorrelationData(messageId));
				return true;
			} catch (Exception e) {
				retry++;
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e1) {
					log.error("[充值消息mq推送处理] orderHandle message {}", message, e);
				}
				log.error("[充值消息mq推送处理] Thread.sleep(500L); ");
			}
		}
		return false;
	}

	/**
	 * 用户充值Vip等级升级推送
	 * 
	 * @param message
	 */
	public boolean userVipPush(RechargeRebateMessage message) {
		log.info("[用户充值Vip等级升级推送处理] message {}", message);
		String messageId = UUID.randomUUID().toString();
		message.setMessageId(messageId);
		message.setCreateTime(DateUtil.now());
		int retry = 0;
		while (retry < retry_time) {
			try {
				rabbitTemplate.convertAndSend("", RabbitmqConfig.RECHARGE_VIP_QUEUE, message,
						new CorrelationData(messageId));
				return true;
			} catch (Exception e) {
				retry++;
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e1) {
					log.error("[用户充值Vip等级升级mq推送处理] orderHandle message {}", message, e);
				}
				log.error("[用户充值Vip等级升级mq推送处理] Thread.sleep(500L); ");
			}
		}
		return false;
	}
	/**
	 * 天降财神 信息推送
	 * 
	 * @param message
	 */
	public boolean fortunePush(FortuneMessage fortuneMessage) {
		String messageIdString = UUID.randomUUID().toString();
		fortuneMessage.setMessageId(messageIdString);
		fortuneMessage.setCreateTime(DateUtil.now());
		int retry = 0;
		while (retry < retry_time) {
			try {//把消息id发送过去
				rabbitTemplate.convertAndSend("", RabbitmqConfig.FORTUNE_QUEUE, fortuneMessage,
						new CorrelationData(messageIdString));
				return true;
			} catch (Exception e) {
				retry++;
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e1) {
					log.error("[天降财神活动mq推送处理] orderHandle messageId {}", fortuneMessage, e);
				}
				log.error("[天降财神活动mq推送处理] Thread.sleep(500L); ");
			}
		}
		return false;
	}

}
