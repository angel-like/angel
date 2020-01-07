package com.xmsy.common.define.exception;

import com.xmsy.common.define.constant.ResultDef;

/**
 * 
 * .非法参数
 *
 * @author chenjisi
 * @date 2017年12月1日 下午2:34:11
 * @Description: TODO
 */
public class ParamInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION = ResultDef.PARAM_INVALID_MSG;

	private String msg = ResultDef.PARAM_INVALID_MSG;

	private int code = ResultDef.PARAM_INVALID_CODE;

	public ParamInvalidException() {
		super(EXCEPTION);
	}

	public ParamInvalidException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public ParamInvalidException(Integer code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public ParamInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamInvalidException(Throwable cause) {
		super(cause);
	}

	public ParamInvalidException(String message, Throwable cause, boolean enableSuppression,
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
