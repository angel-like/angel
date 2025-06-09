package com.atguigu.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author lixiaolong
 * @date 2020/12/18 16:05
 * @description 支付服务
 */
@SpringBootApplication
//@EnableEurekaClient //这里可以省略该注解
public class StreamRabbitmqProvider8801 {

    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitmqProvider8801.class, args);
        System.out.println("-------------StreamRabbitmqProvider8801启动成功-------------");
    }

}
