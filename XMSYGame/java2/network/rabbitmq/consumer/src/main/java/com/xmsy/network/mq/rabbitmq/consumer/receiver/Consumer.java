package com.xmsy.network.mq.rabbitmq.consumer.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息消费者
 *
 * @author aleng
 */
@Slf4j
@Component
public class Consumer {

	public static final String QUEUE = "topic.test";

	/**
	 * 监听
	 *
	 * @param receiveMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = QUEUE)
	public void receiveMessage(String receiveMessage, Message message, Channel channel) {
		try {
			// 手动签收
			log.info("接收到消息:[{}]", receiveMessage);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			log.info("接收到消息之后的处理发生异常.", e);
			try {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e1) {
				log.error("签收异常.", e1);
			}
		}
	}
}
