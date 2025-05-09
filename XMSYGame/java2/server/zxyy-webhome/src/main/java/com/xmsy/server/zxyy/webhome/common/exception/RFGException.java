package com.xmsy.server.zxyy.webhome.common.exception;

/**
 * 自定义异常 拒绝游戏登录
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年10月27日 下午10:11:27
 */
public class RFGException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 409;

    public RFGException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public RFGException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public RFGException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public RFGException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
