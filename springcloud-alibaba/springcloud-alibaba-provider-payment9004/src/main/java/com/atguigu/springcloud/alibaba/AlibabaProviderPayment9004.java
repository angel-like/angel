package com.atguigu.springcloud.alibaba;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lixiaolong
 * @EnableDiscoveryClient 该注解用于向使用consul或者zookeeper作为注册中心时注册服务
 * @date 2020/12/18 16:05
 * @description 支付服务    该项目模块是为了测试断路器sentinel的功能
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AlibabaProviderPayment9004 {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaProviderPayment9004.class, args);
        System.out.println("-------------AlibabaProviderPayment9004启动成功-------------");
    }

}
