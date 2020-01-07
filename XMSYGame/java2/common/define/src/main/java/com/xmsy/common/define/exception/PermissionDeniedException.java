package com.xmsy.common.define.exception;

import com.xmsy.common.define.constant.ResultDef;

/**
 * 
 * .权限错误
 *
 * @author chenjisi
 * @date 2017年12月1日 下午2:34:20
 * @Description: TODO
 */
public class PermissionDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION = ResultDef.PERMISSION_DENIED_MSG;

	private String msg = ResultDef.PERMISSION_DENIED_MSG;

	private int code = ResultDef.PERMISSION_DENIED_CODE;

	public PermissionDeniedException() {
		super(EXCEPTION);
	}

	public PermissionDeniedException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public PermissionDeniedException(Integer code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public PermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionDeniedException(Throwable cause) {
		super(cause);
	}

	public PermissionDeniedException(String message, Throwable cause, boolean enableSuppression,
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
