package com.xmsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;



@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableCaching
@Slf4j
public class RobotManagerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RobotManagerApplication.class, args);
		log.info("startup-success");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RobotManagerApplication.class);
	}
}
