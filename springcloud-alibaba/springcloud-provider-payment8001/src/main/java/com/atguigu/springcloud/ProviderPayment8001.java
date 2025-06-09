package com.atguigu.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient // 开启 discovery接口的服务发现功能 支持多种注册中心（如Eureka、Consul、Nacos等）
public class ProviderPayment8001 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderPayment8001.class, args);
        System.out.println("-------------ProviderPayment8001启动成功-------------");
    }

}
