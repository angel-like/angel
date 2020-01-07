package com.xmsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ServletComponentScan
@Slf4j
public class PaymentApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PaymentApplication.class);
		try {
			springApplication.run(args);
		} catch (Exception e) {
			System.out.println("==============================================");
			System.out.println("启动异常 ");
			System.out.println("==============================================");
			e.printStackTrace();
			System.exit(0);
		}
		long maxM = Runtime.getRuntime().maxMemory() / 1000 / 1000;
		long totalM = Runtime.getRuntime().totalMemory() / 1000 / 1000;
		long usedM = Runtime.getRuntime().freeMemory() / 1000 / 1000;
		System.out.println("==============================================");
		System.out.println("maxMemory   = " + maxM);
		System.out.println("totalMemory = " + totalM);
		System.out.println("freeMemory  = " + usedM);
		log.info("startup-success");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PaymentApplication.class);
	}

}
