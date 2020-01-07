package com.xmsy.server.zxyy.manager.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xmsy.server.zxyy.manager.mq.ConfirmCallback;

/**
 * 消息队列配置
 * 
 * @author Administrator
 *
 */
@Configuration
public class RabbitmqConfig {

	// 系统配置队列
	public static final String SYSCONFIG_QUEUE = "sysconfig";
	// 注册队列
	public static final String REGISTER_QUEUE = "register";
	// 充值队列
	public static final String RECHARGE_QUEUE = "recharge";
	// 取款队列
	public static final String TAKE_QUEUE = "take";
	// 充值返佣队列
	public static final String RECHARGE_REBATE_QUEUE = "recharge-rebate";
	// 充值Vip队列
	public static final String RECHARGE_VIP_QUEUE = "recharge-vip";
	// 天降财神红包队列 
	public static final String FORTUNE_QUEUE = "fortune";


	/**
	 * 声明注册逻辑队列
	 *
	 * @return
	 */
	@Bean
	public Queue registerQueue() {
		// 声明注册逻辑队列
		return new Queue(REGISTER_QUEUE, true);
	}

	/**
	 * 声明充值逻辑队列
	 *
	 * @return
	 */
	@Bean
	public Queue rechargeQueue() {
		// 声明充值逻辑队列
		return new Queue(RECHARGE_QUEUE, true);
	}

	/**
	 * 声明取款逻辑队列
	 *
	 * @return
	 */
	@Bean
	public Queue takeQueue() {
		// 声明取款逻辑队列
		return new Queue(TAKE_QUEUE, true);
	}
	/**
	 *天降财神  消息队列
	 *
	 * @return
	 */
	@Bean
	public Queue fortuneQueue() {
		// 声明 天降财神  消息队列
		return new Queue(FORTUNE_QUEUE, true);
	}
	/**
	 * 自定义消息转换器  。传入的到rabbitmq里的实体信息不再是乱码
	 * @return  用了 其他项目  监听收到的消息会出错
	 */
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
