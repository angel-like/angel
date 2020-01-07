package com.xmsy.server.zxyy.calculate.constant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局定义
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class HallUrlConstant {

	// ======================================推送服===============================
	@Value("${push.url}")
	private String push_url;

	@PostConstruct
	public void init() {
		PUSH_URL = push_url;
		log.info("push: [url] {}", push_url);

	}
	@Value("${provider.code}")
	private String providerCode;
	/*
	 * =================供应商===============================================
	 *
	 */
	private static String PROVIDER_CODE = null;

	public static String getPROVIDER_CODE() {
		return PROVIDER_CODE;
	}


	/*
	 * =================推送服===============================================
	 */
	private static String PUSH_URL = null;

	public static String getPUSH_URL() {
		return PUSH_URL;
	}

}
