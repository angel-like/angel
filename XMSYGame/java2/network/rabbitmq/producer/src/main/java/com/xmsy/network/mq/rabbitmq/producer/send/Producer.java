package com.xmsy.network.mq.rabbitmq.producer.send;

import java.util.UUID;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xmsy.network.mq.rabbitmq.producer.config.ServerConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息生产者
 *
 * @author aleng
 */
@Slf4j
@Component
public class Producer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void push(String message) {
		rabbitTemplate.convertAndSend(ServerConfiguration.MESSAGE_EXCHANGE, ServerConfiguration.ROUNTE_KEY, message,
				new CorrelationData(UUID.randomUUID().toString()));
		log.info("[发送一条消息] message {}", message);
	}
}
