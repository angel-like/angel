package com.xmsy.network.pay.paybase.result;

import com.xmsy.network.pay.paybase.define.CodeDef;

import lombok.Getter;

/**
 * .支付查询结果
 * 
 * @author aleng
 *
 */
@Getter
public class QueryOrderResult {

	// 请求响应码
	private Integer code;

	// 错误描述
	private String msg;

	// 返回数据
	private String data;

	private QueryOrderResult(int code) {
		this.code = code;
	}

	private QueryOrderResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private QueryOrderResult(int code, String msg, String data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess() {
		return CodeDef.SUCCESS == this.code;
	}

	public static QueryOrderResult success(String data) {
		return new QueryOrderResult(CodeDef.SUCCESS, null, data);
	}

	public static QueryOrderResult err() {
		return new QueryOrderResult(CodeDef.FAIL, CodeDef.ORDER_QUERY_ERR);
	}

	public static QueryOrderResult err(String msg) {
		return new QueryOrderResult(CodeDef.FAIL, msg);
	}

}
