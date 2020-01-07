package com.xmsy.common.define.result;

/**
 * 
 * .错误对象定义
 *
 * @author chenjisi
 * @date 2017年12月1日 下午2:17:50
 * @Description: TODO
 */
public final class GlobalResult<T> {

	// 结果返回码
	private int code;
	// 结果错误描述
	private String errorMsg;
	// 结果数据
	private T data;
	// 请求uri
	private String uri;

	public GlobalResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalResult(int code, String errorMsg, T data) {
		super();
		this.code = code;
		this.errorMsg = errorMsg;
		this.data = data;
	}

	public GlobalResult(int code, String errorMsg, T data, String uri) {
		super();
		this.code = code;
		this.errorMsg = errorMsg;
		this.data = data;
		this.uri = uri;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "GlobalResult [code=" + code + ", errorMsg=" + errorMsg + ", data=" + data + ", uri=" + uri + "]";
	}
}
