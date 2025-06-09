package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // 启动feign
public class OpenFeignOrder80 {

    public static void main(String[] args) {
        SpringApplication.run(OpenFeignOrder80.class, args);
        System.out.println("-------------OpenFeignOrder80启动成功-------------");

    }

}
