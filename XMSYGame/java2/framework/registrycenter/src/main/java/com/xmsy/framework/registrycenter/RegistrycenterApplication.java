package com.xmsy.framework.registrycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * 注册中心
 * 
 * @author aleng
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class RegistrycenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(RegistrycenterApplication.class, args);
	}
}
