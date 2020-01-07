package com.xmsy.network.sms;

public class SMSResult {
	
	private int code;
	private String msg;
	private String obj;
	
	public SMSResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SMSResult(int code) {
		super();
		this.code = code;
	}
	
	public SMSResult(int code,String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public SMSResult(int code, String msg, String obj) {
		super();
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "SMSResult [code=" + code + ", msg=" + msg + ", obj=" + obj + "]";
	}
}
