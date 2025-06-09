package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lixiaolong
 */
@SpringBootApplication
@EnableFeignClients // 启动 feign
@EnableHystrix // 启动 hystrix
public class OpenFeignHystrixOrder80 {

    public static void main(String[] args) {
        SpringApplication.run(OpenFeignHystrixOrder80.class, args);
        System.out.println("-------------OpenFeignHystrixOrder80启动成功-------------");

    }

}
