package com.xmsy.network.mq.rabbitmq.producer.config;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xmsy.network.mq.rabbitmq.producer.callback.ConfirmCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * rabbitmq 配置
 *
 * @author aleng
 */
@Slf4j
@Configuration
public class ServerConfiguration {

	// 系统配置
	public static final ResourceBundle RESOURCE = ResourceBundle.getBundle("config");
	// 死信队列
	public static final String DLX_QUEUE = RESOURCE.getString("dlx-queue");
	// 消息队列
	public static final String MESSAGE_QUEUE = RESOURCE.getString("message-queue");
	// 测试队列
	public static final String TEST_QUEUE = RESOURCE.getString("test-queue");
	// 未路由消息队列
	public static final String UNROUTE_QUEUE = RESOURCE.getString("unroute-queue");
	// 死信交换机
	public static final String DLX_EXCHANGE = RESOURCE.getString("dlx-exchange");
	// 消息交换机
	public static final String MESSAGE_EXCHANGE = RESOURCE.getString("message-exchange");
	// 未路由消息交换机
	public static final String UNROUTE_EXCHANGE = RESOURCE.getString("unroute-exchange");
	// 路由
	public static final String ROUNTE_KEY = RESOURCE.getString("routing-key");

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void initRabbitTemplate() {
		// 设置生产者消息确认
		rabbitTemplate.setConfirmCallback(new ConfirmCallback());
		log.info("[死信队列 DLX_QUEUE]:{},[消息队列 MESSAGE_QUEUE]:{},[消息队列 TEST_QUEUE]:{},[未路由消息队列 UNROUTE_QUEUE]:{},"
				+ "[死信交换机 DLX_EXCHANGE]:{},[消息交换机 MESSAGE_EXCHANGE]:{},[未路由消息交换机 UNROUTE_EXCHANGE]:{},[路由 ROUNTE_KEY]:{}",
				DLX_QUEUE, MESSAGE_QUEUE, TEST_QUEUE, UNROUTE_QUEUE, DLX_EXCHANGE, MESSAGE_EXCHANGE, UNROUTE_EXCHANGE,
				ROUNTE_KEY);
	}

	/**
	 * 申明消息队列
	 *
	 * @return
	 */
	@Bean
	public Queue messageQueue() {
		Map<String, Object> arguments = new HashMap<>(4);
		// 申明死信交换器
		arguments.put("x-dead-letter-exchange", DLX_EXCHANGE);
		return new Queue(MESSAGE_QUEUE, true, false, false, arguments);
	}

	/**
	 * 申明测试队列
	 *
	 * @return
	 */
	@Bean
	public Queue testQueue() {
		Map<String, Object> arguments = new HashMap<>(4);
		// 申明死信交换器
		arguments.put("x-dead-letter-exchange", DLX_EXCHANGE);
		return new Queue(TEST_QUEUE, true, false, false, arguments);
	}

	/**
	 * 没有路由到的消息将进入此队列
	 *
	 * @return
	 */
	@Bean
	public Queue unRouteQueue() {
		return new Queue(UNROUTE_QUEUE);
	}

	/**
	 * 死信队列
	 *
	 * @return
	 */
	@Bean
	public Queue dlxQueue() {
		return new Queue(DLX_QUEUE);
	}

	/**
	 * 申明交换器
	 *
	 * @return
	 */
	@Bean
	public TopicExchange exchange() {
		Map<String, Object> arguments = new HashMap<>(4);
		// 当发往exchange-rabbit-springboot-advance的消息,routingKey和bindingKey没有匹配上时，将会由exchange-unroute交换器进行处理
		arguments.put("alternate-exchange", UNROUTE_EXCHANGE);
		return new TopicExchange(MESSAGE_EXCHANGE, true, false, arguments);
	}

	@Bean
	public FanoutExchange unRouteExchange() {
		// 此处的交换器的名字要和 exchange() 方法中 alternate-exchange 参数的值一致
		return new FanoutExchange(UNROUTE_EXCHANGE);
	}

	/**
	 * 申明死信交换器
	 *
	 * @return
	 */
	@Bean
	public FanoutExchange dlxExchange() {
		return new FanoutExchange(DLX_EXCHANGE);
	}

	/**
	 * 申明消息队列绑定
	 *
	 * @return
	 */
	@Bean
	public Binding messageBinding() {
		return BindingBuilder.bind(messageQueue()).to(exchange()).with("topic.#");
	}

	/**
	 * 申明测试队列绑定
	 *
	 * @return
	 */
	@Bean
	public Binding testBinding() {
		return BindingBuilder.bind(testQueue()).to(exchange()).with("topic.#");
	}

	@Bean
	public Binding unRouteBinding() {
		return BindingBuilder.bind(unRouteQueue()).to(unRouteExchange());
	}

	@Bean
	public Binding dlxBinding() {
		return BindingBuilder.bind(dlxQueue()).to(dlxExchange());
	}
}
