package com.xmsy.server.zxyy.payment.service.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xmsy.server.zxyy.payment.service.mq.ConfirmCallback;

/**
 * 消息队列配置
 * 
 * @author Administrator
 *
 */
@Configuration
public class RabbitmqConfig {

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

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void initRabbitTemplate() {
		// 设置生产者消息确认
		rabbitTemplate.setConfirmCallback(new ConfirmCallback());
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

}
