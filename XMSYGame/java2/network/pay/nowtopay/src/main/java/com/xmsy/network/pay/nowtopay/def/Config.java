package com.xmsy.network.pay.nowtopay.def;

import java.util.Map;

/**
 * .支付相关参数
 * 
 * @author aleng
 *
 */
public class Config {

	// 支付公司名称
	public static final String name = "nowtopay";

	// 回调成功
	public static final String CALLBACK_SUCCESS = "1";
	// 下单成功
	public static final Integer ORDER_SUCCESS = 0;

	// 支付appid
	public static String APPID;
	public static String APPSECRET;

	public static Map<String, String> PAY_CHANNEL_H5;

	public static Map<String, String> PAY_CHANNEL_PC;
	// 下单接口
	public static String ORDER_URL;
	// 查询接口
	public static String QUERY_URL;
	// 同步回调接口
	public static String ORDER_RETURN_URL;
	// 异步回调接口
	public static String ORDER_NOTIFY_URL;
}
