package com.xmsy.network.pay.paybase.result;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.paybase.define.CodeDef;

import lombok.Getter;
import lombok.ToString;

/**
 * 支付平台回调结果封装
 * 
 * @author aleng
 *
 */
@ToString
@Getter
public class CallbackResult {

	// 错误码
	private Integer code;
	// 错误描述
	private String errMsg;
	// 支付回调返回的参数
	private CallbackMessage callbackParam;

	private CallbackResult(int code, String errMsg) {
		super();
		this.code = code;
		this.errMsg = errMsg;
	}

	private CallbackResult(int code, CallbackMessage callbackParam) {
		super();
		this.code = code;
		this.callbackParam = callbackParam;
	}

	private CallbackResult(int code, String errMsg, CallbackMessage callbackParam) {
		super();
		this.code = code;
		this.errMsg = errMsg;
		this.callbackParam = callbackParam;
	}

	public boolean isSuccess() {
		return CodeDef.SUCCESS == this.code;
	}

	public static CallbackResult success(CallbackMessage callbackParam) {
		return new CallbackResult(CodeDef.SUCCESS, callbackParam);
	}

	public static CallbackResult err() {
		return new CallbackResult(CodeDef.FAIL, CodeDef.CALLBACK_ERR);
	}

	public static CallbackResult err(String msg) {
		return new CallbackResult(CodeDef.FAIL, msg);
	}
}
