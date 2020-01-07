package com.xmsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;



@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableAsync
@EnableCaching
@Slf4j
public class GameApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
		log.info("startup-success");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GameApplication.class);
	}
}
