package com.xmsy.common.define.exception;

import com.xmsy.common.define.constant.ResultDef;

/**
 * 
 * .内部错误
 *
 * @author chenjisi
 * @date 2017年12月1日 下午2:33:55
 * @Description: TODO
 */
public class InnerErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION = ResultDef.INNER_ERROR_MSG;
	private String msg = ResultDef.INNER_ERROR_MSG;
	private int code = ResultDef.INNER_ERROR_CODE;

	public InnerErrorException() {
		super(EXCEPTION);
	}

	public InnerErrorException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public InnerErrorException(Integer code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public InnerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InnerErrorException(Throwable cause) {
		super(cause);
	}

	public InnerErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

}
