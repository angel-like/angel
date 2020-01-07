package com.xmsy.server.zxyy.game.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xmsy.server.zxyy.game.mq.ConfirmCallback;

/**
 * 消息队列配置
 * 
 * @author Administrator
 *
 */
@Configuration
public class RabbitmqConfig {

	// 取款队列
	public static final String GAME_QUEUE = "game";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void initRabbitTemplate() {
		// 设置生产者消息确认
		rabbitTemplate.setConfirmCallback(new ConfirmCallback());
	}

	/**
	 * 声明注册逻辑队列
	 *
	 * @return
	 */
	@Bean
	public Queue gameQueue() {
		// 声明注册逻辑队列
		return new Queue(GAME_QUEUE, true);
	}
	/**
	 * 自定义消息转换器  。传入的到rabbitmq里的实体信息不再是乱码
	 * @return  用了 其他项目  也需要配置
	 */
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
