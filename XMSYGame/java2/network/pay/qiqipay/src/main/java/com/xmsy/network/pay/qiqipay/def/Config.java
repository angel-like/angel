package com.xmsy.network.pay.qiqipay.def;

import java.util.Map;

public class Config {

	// 支付公司名称
	public static final String name = "qiqipay";

	// 回调成功
	public static final String CALLBACK_SUCCESS = "0000";
	// 回调处理失败
	public static final String CALLBACK_FAIL = "";
	// 下单成功
	public static final Integer ORDER_SUCCESS = 1;

	// 支付appid
	public static String APPID;
	public static String APPSECRET;

	public static Map<String, String> PAY_CHANNEL;
	// 下单接口
	public static String ORDER_URL;
}
