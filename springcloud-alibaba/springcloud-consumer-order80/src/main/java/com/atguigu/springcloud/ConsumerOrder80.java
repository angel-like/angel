package com.atguigu.springcloud;

import com.atguigu.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "springcloud-payment-service", configuration = MySelfRule.class) // 启动该服务时去加载自定义的ribbon配置，改成随机了
//@RibbonClients
public class ConsumerOrder80 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOrder80.class, args);
        System.out.println("-------------ConsumerOrder80启动成功-------------");

    }

}
