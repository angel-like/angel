package com.xmsy.server.zxyy.payment.service.mq;

import java.util.UUID;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.server.zxyy.payment.service.config.RabbitmqConfig;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

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

}
