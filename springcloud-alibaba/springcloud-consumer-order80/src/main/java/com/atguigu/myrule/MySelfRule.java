package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 抓取策略算法  用自定义算法
 * @author Administrator
 *
 */
@Configuration
public class MySelfRule {

	// 默认轮询RoundRobinRule();
	// RetryRule(); 先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务
	//RandomRule();// 随机
	@Bean
	public IRule myrule() {
		return new RandomRule_ZY();// 随机
	}
	// 去自定义策略
	
}