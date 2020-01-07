package com.xmsy.network.pay.mingjiepay.def;

import java.util.Map;

public class Config {

	// 支付公司名称
	public static final String name = "mingjiepay";

	// 回调成功返回商家成功码
	public static final String CALLBACK_SUCCESS = "00";
	// 判断下单成功
	public static final String ORDER_SUCCESS = "00";

	public static String PUBLIC_KEY;
	public static String PRIVATE_KEY;

	// 支付appid
	public static String APPID;
	public static String APPSECRET;

	// 支付渠道-PC
	public static Map<String, String> PAY_CHANNEL_PC;

	// 支付渠道-H5
	public static Map<String, String> PAY_CHANNEL_H5;
	// 下单接口
	public static String ORDER_URL;
}
