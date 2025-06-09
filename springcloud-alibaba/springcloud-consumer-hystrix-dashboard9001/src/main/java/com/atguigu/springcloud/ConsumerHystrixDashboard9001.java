package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author lixiaolong
 *  去监控 springcloud-provider-hystrix-payment8001服务 ，url: http://localhost:8001/hystrix.stream
 */
@SpringBootApplication
@EnableHystrixDashboard
public class ConsumerHystrixDashboard9001 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerHystrixDashboard9001.class, args);
        System.out.println("-------------ConsumerHystrixDashboard9001启动成功-------------");

    }

}
