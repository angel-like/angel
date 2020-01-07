package com.xmsy.server.zxyy.calculate.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.xmsy.common.define.constant.ResultDef;

/**
 * 返回数据
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", ResultDef.SUCCESS);
		put("msg", "success");
	}

	public R(Map<String, Object> data) {
		put("data", data);
	}

	public static R error() {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
	}

	public static R errorNot(String msg) {
		return error(HttpStatus.NOT_IMPLEMENTED.value(), msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R errorNot(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public R putData(String key, Object value) {
		super.clear();
		super.put(key, value);
		R r = new R();
		r.put("data", this);
		return r;
	}
}
