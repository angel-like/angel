package com.atguigu.springcloud.alibaba.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lixiaolong
 * @date 2020-02-25 16:06
 */
@Configuration
public class ApplicationContextConfig {
    /**
     * 开启@LoadBalanced后，OrderController只能通过PAYMENT_URL去访问，直接通过http://localhost:8001"去访问会报找不
     * 注释掉@LoadBalanced后，只能通过ip去访问了
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
