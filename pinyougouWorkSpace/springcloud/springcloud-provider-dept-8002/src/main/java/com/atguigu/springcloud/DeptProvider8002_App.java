package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient//本服务 启动后  会自动注册进eureka
@EnableDiscoveryClient //服务发现
public class DeptProvider8002_App {
	
	public static void main(String[] args) {
		SpringApplication.run(DeptProvider8002_App.class, args);
	}
	
}
