package com.xmsy.network.pay.dingdingpay.def;

import java.util.Map;

public class Config {

	// 支付公司名称
	public static final String name = "dingdingpay";

	// 回调成功返回商家成功码1：预下单成功 2：预下单失败 3：交易成功 4：交易失败 5：交易超时 6：交易中
	public static final String CALLBACK_SUCCESS = "000000";
	// 判断预下单成功
	public static final String PRE_ORDER_SUCCESS = "1";
	// 判端下单成功
	public static final String ORDER_SUCCESS = "3";

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
