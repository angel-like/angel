package com.xmsy.server.webhome;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMQTest {
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	AmqpAdmin amqpAdmin;
//	@Autowired
//	private LuckyConfigService luckyConfigService;
	/**
	 * 发送
	 */
	@Test
	public void contextLoads() {
		//1.自己构造message  定义消息体内容和消息头
		//rabbitTemplate.send(exchange, routingKey, message);
		
		//2.object默认为消息体    传入发送对象，自动序列化发送给rabbitMQ
										//转换器 exchang.fanout	路由键            内容
		//rabbitTemplate.convertAndSend(exchange , routingKey, object);
		
		Map<String,Object> map=new HashMap<>();
		map.put("java单播", "atguigu.news能接受");
		map.put("集合", "sb");			
		rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",map);
		
		//rabbitTemplate.convertAndSend("exchange.direct","atguigu.news","实体类对象");
	}
	
	/**
	 * 接收
	 */
	@Test
	public void receive() {
		Object o = rabbitTemplate.receiveAndConvert("atguigu.news");
		System.out.println(o.getClass());
		System.out.println(o);
	}
	
	
	/**
	 * 创建
	 */
	@Test
	public void create() {
		amqpAdmin.declareExchange(new DirectExchange("amqpAdmin.exchange"));
		
		amqpAdmin.declareQueue(new Queue("amqpAdmin.queue",true));
		
		amqpAdmin.declareBinding(	//队列			类型
				new Binding("amqpAdmin.queue",Binding.DestinationType.QUEUE,
						//	转换器				路由键 			信息
						"amqpAdmin.exchange","amqp.luyoujian",null));
	}
	
	
}
