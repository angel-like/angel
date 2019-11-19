package com.atguigu.springcloud.cfgbeans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



@Configuration
public class ConfigBean {
	/**
	 *  RestTemplate提供了多种便捷访问远程Http服务的方法， 
		是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集
		
		使用restTemplate访问restful接口非常的简单粗暴无脑。
		(url, requestMap, ResponseBean.class)这三个参数分别代表 
		REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
	 * @return
	 */
	@Bean
	@LoadBalanced//开启    负载均衡（客户端）
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	//默认轮询RoundRobinRule
	//RetryRule 先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务
/*	@Bean
	public IRule myrule() {
		return new RandomRule();//随机
	}*/  
	//去自定义策略
}
