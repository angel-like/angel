package com.xmsy.common.define.result;

import com.xmsy.common.define.constant.ResultDef;

/**
 * 
 * .结果处理工具
 *
 * @author aleng
 * @date 2017年12月1日 上午11:14:48
 * @Description: TODO
 */
public final class ResultUtils<T> {

	/**
	 * 
	 * .判断是否成功
	 *
	 */
	public static <T> Boolean isSuccess(GlobalResult<T> globalResult) {
		if (null == globalResult) {
			return false;
		}
		return ResultDef.SUCCESS == globalResult.getCode();
	}

	/**
	 * 
	 * .全局正确的结果对象
	 * 
	 * @date 2017年12月4日 上午11:21:42
	 * @Description: 构造全局结果对象（添加修改操作，只需要返回正确的返回码）
	 * @return 全局结果对象
	 *
	 */
	public static <T> GlobalResult<T> getSuccessResult() {
		return new GlobalResult<T>(ResultDef.SUCCESS, ResultDef.SUCCESS_MSG, null);
	}

	/**
	 * 
	 * .全局正确的结果对象
	 * 
	 * @date 2017年12月4日 上午11:21:42
	 * @param resultObj
	 *            结果对象
	 * @Description: 构造全局结果对象（查询操作，需要返回结果对象）
	 * @return 全局结果对象
	 *
	 */
	public static <T> GlobalResult<T> getSuccessObject(T resultObj) {
		return new GlobalResult<T>(ResultDef.SUCCESS, ResultDef.SUCCESS_MSG, resultObj);
	}

	/**
	 * 
	 * .全局正确的结果对象
	 * 
	 * @date 2017年12月4日 上午11:21:42
	 * @param resultObj
	 *            结果对象
	 * @param uri
	 *            请求uri
	 * @Description: 构造全局结果对象（查询操作，需要返回结果对象）
	 * @return 全局结果对象
	 *
	 */
	public static <T> GlobalResult<T> getSuccessObject(T resultObj, String uri) {
		return new GlobalResult<T>(ResultDef.SUCCESS, ResultDef.SUCCESS_MSG, resultObj, uri);
	}

	/**
	 * 
	 * .全局结果错误结果对象
	 * 
	 * @date 2017年12月4日 上午11:21:42
	 * @Description: 构造全局结果对象（异常情况）
	 * @param errorMsg
	 *            请求uri
	 * @return 全局结果对象
	 *
	 */
	public static <T> GlobalResult<T> getErrorResult(String errorMsg) {
		return new GlobalResult<T>(ResultDef.FAIL, errorMsg, null);
	}

	/**
	 * 
	 * .全局结果错误结果对象
	 * 
	 * @date 2017年12月4日 上午11:21:42
	 * @Description: 构造全局结果对象（异常情况）
	 * @param code
	 *            错误码
	 * @param errorMsg
	 *            错误描述
	 * @param uri
	 *            请求uri
	 * @return 全局结果对象
	 *
	 */
	public static <T> GlobalResult<T> getErrorResult(int code, String errorMsg, String uri) {
		return new GlobalResult<T>(code, errorMsg, null, uri);
	}

}
