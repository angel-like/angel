package com.xmsy.server.zxyy.calculate.config;


import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 消息队列配置
 * 
 * @author axiong
 *
 */
@Configuration
public class RabbitmqConfig {

	/**
	 * 自定义消息转换器  。传入的到rabbitmq里的实体信息不再是乱码
	 * @return  用了 其他项目  也要转换
	 */
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
