package com.xmsy.network.pay.paybase.result;

import com.xmsy.common.bean.payment.ResultData;

import lombok.Getter;

/**
 * .支付请求结果处理
 * 
 * @author aleng
 *
 */

@Getter
public class PayResult {

	// 请求响应码
	private int code;
	// 错误描述
	private String msg;
	// 返回数据
	private ResultData data;

	public PayResult(int code, String msg, ResultData data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
