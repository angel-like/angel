package com.xmsy.server.zxyy.manager.mq;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * 生产者消息确认
 *
 * @author aleng
 */
@Slf4j
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("[生产者消息确认] correlationData: {} ,ack: {} , cause: {}", correlationData, ack, cause);
		if (!ack) {
			log.error("[生产者消息发送失败] correlationData: {} 可能未到达rabbitmq服务器");
		}
	}
}
