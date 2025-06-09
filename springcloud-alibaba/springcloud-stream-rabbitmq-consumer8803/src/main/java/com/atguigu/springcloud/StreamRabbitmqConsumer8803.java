package com.atguigu.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lixiaolong
 * @date 2020/12/18 16:05
 * @description 支付服务
 */
@SpringBootApplication
public class StreamRabbitmqConsumer8803 {

    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitmqConsumer8803.class, args);
        System.out.println("-------------StreamRabbitmqConsumer8803启动成功-------------");
    }

}
