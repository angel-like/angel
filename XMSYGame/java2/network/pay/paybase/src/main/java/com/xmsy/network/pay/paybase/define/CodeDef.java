package com.xmsy.network.pay.paybase.define;

/**
 * .支付响应码定义
 * 
 * @author aleng
 *
 */
public class CodeDef {

	// 成功
	public static final Integer SUCCESS = 1;
	// 成功
	public static final String SUCCESS_MSG = "ok";
	// 失败
	public static final int FAIL = -1;
	// 支付请求参数非法
	public static final String PARAM_INVALID = "支付请求参数非法";
	// 支付请求参数非法
	public static final String AMOUNT_INVALID = "支付金额必须是大于0的整数";
	// 支付返回异常
	public static final String PAY_ERR = "支付异常";
	// 支付回调异常
	public static final String CALLBACK_ERR = "支付回调异常";
	// 签名校验出错
	public static final String SIGN_VALIDATE_ERR = "签名校验出错";
	// 支付订单查询异常
	public static final String ORDER_QUERY_ERR = "支付订单查询异常";
}
