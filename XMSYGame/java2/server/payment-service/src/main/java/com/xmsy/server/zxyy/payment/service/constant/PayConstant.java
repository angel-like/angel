package com.xmsy.server.zxyy.payment.service.constant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 初始化常量
 * @author axiong
 *
 */
@Slf4j
@Component
public class PayConstant {
	@Value("${pay.service.callbackUrl}")
	private String pay_service_callback_url;

	@PostConstruct
	public void init() {
		PAY_SERVICE_CALLBACK_URL=pay_service_callback_url;
		log.debug(PAY_SERVICE_CALLBACK_URL);
	}

	private static String PAY_SERVICE_CALLBACK_URL=null;

	//获取支付服务回调地址的默认前缀
	public static String getPAY_SERVICE_CALLBACK_URL() {
		return PAY_SERVICE_CALLBACK_URL;
	}
	public static Map<String,String> CALLBACK_IP;
}
