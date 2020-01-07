package com.xmsy.common.define.exception;

import com.xmsy.common.define.constant.ResultDef;

/**
 * 
 * .表单重复提交异常
 *
 * @author aleng
 * @date 2017年12月1日 下午2:35:31
 * @Description: TODO
 */
public class OrderRepeatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION = ResultDef.UNKNOW_ERROR_MSG;

	private String msg = ResultDef.ORDER_REPEAT_MSG;

	private int code = ResultDef.ORDER_REPEAT_CODE;

	public OrderRepeatException() {
		super(EXCEPTION);
	}

	public OrderRepeatException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public OrderRepeatException(Integer code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public OrderRepeatException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderRepeatException(Throwable cause) {
		super(cause);
	}

	public OrderRepeatException(String message, Throwable cause, boolean enableSuppression,
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
