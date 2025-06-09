package com.atguigu.springcloud.alibaba;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lixiaolong
 * @EnableDiscoveryClient 该注解用于向使用consul或者zookeeper作为注册中心时注册服务
 * @date 2020/12/18 16:05
 * @description 支付服务
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelService8401 {

    public static void main(String[] args) {
        SpringApplication.run(SentinelService8401.class, args);
        System.out.println("-------------SentinelService8401启动成功-------------");
    }

}
