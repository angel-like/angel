package com.xmsy.common.define.exception;

import com.xmsy.common.define.constant.ResultDef;

/**
 * 
 * .未知错误
 *
 * @author chenjisi
 * @date 2017年12月1日 下午2:35:31
 * @Description: TODO
 */
public class UnknownErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION = ResultDef.UNKNOW_ERROR_MSG;

	private String msg = ResultDef.UNKNOW_ERROR_MSG;

	private int code = ResultDef.UNKNOW_ERROR_CODE;

	public UnknownErrorException() {
		super(EXCEPTION);
	}

	public UnknownErrorException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public UnknownErrorException(Integer code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public UnknownErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownErrorException(Throwable cause) {
		super(cause);
	}

	public UnknownErrorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

}
