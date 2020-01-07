package com.xmsy.network.jpush.constant;

/**
 * 错误定义
 * 
 * @author Administrator
 *
 */
public enum ErrorDef {

	APPKEY_ERROR(4101, "极光推送appkey错误"), MASTER_SECRET_ERROR(4102, "极光推送错误");

	private int code;
	private String msg;

	ErrorDef(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
