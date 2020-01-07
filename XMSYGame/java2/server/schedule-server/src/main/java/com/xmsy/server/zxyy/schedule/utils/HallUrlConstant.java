package com.xmsy.server.zxyy.schedule.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 全局定义
 * 
 * @author Administrator
 *
 */
@Component
public class HallUrlConstant {
	@Value("${provider.code}")
	private Long providerCode;
	// ======================================开元接入参数 start===============================
	@Value("${kaiyuan.url}")
	private String ky_url;

	@Value("${kaiyuan.agent}")
	private String agent;

	@Value("${kaiyuan.linecode}")
	private String linecode;

	@Value("${kaiyuan.md5key}")
	private String md5key;

	@Value("${kaiyuan.deskey}")
	private String deskey;

	@Value("${kaiyuan.recordUrl}")
	private String recordUrl;

	// ======================================开元接入参数 end===============================


	@PostConstruct
	public void init() {
		KY_URL =ky_url;
		AGENT =agent;
		LINECODE =linecode;
		MD5KEY =md5key;
		DESKEY=deskey;
		RECORDURL =recordUrl;
		PROVIDER_CODE = providerCode;
	}
	/*
	 * =================供应商===============================================
	 *
	 */
	private static Long PROVIDER_CODE = null;
	/*
	 * =================开元===============================================
	 *
	 */
	private static String KY_URL = null;
	private static String AGENT = null;
	private static String LINECODE = null;
	private static String MD5KEY = null;
	private static String DESKEY = null;
	private static String RECORDURL = null;
	public static String getRECORDURL() {
		return RECORDURL;
	}
	public static String getKyUrl() {
		return KY_URL;
	}
	public static String getAGENT() {
		return AGENT;
	}
	public static String getLINECODE() {
		return LINECODE;
	}
	public static String getMD5KEY() {
		return MD5KEY;
	}

	public static String getDESKEY() {
		return DESKEY;
	}
	public static Long getPROVIDER_CODE() {
		return PROVIDER_CODE;
	}

}
