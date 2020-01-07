package com.xmsy.server.serverbase.result;

/**
 * 
 * .结果处理工具
 *
 * @author chenjisi
 * @date 2017年12月1日 上午11:14:48
 * @Description: TODO
 */
//public final class ResultUtils {
//
//	/**
//	 * 
//	 * .全局正确的结果对象
//	 * 
//	 * @date 2017年12月4日 上午11:21:42
//	 * @param resultObj
//	 *            结果对象
//	 * @Description: 构造全局结果对象（查询操作，需要返回结果对象）
//	 * @return 全局结果对象
//	 *
//	 */
//	public static GlobalResult getSuccessObject(Object resultObj) {
//		return new GlobalResult(ResultDef.SUCCESS, ResultDef.EMPTY_STRING,
//				null == resultObj ? ResultDef.EMPTY_OBJECT : resultObj, ResultDef.EMPTY_STRING);
//	}
//
//	/**
//	 * 
//	 * .全局正确的结果对象
//	 * 
//	 * @date 2017年12月4日 上午11:21:42
//	 * @param resultObj
//	 *            结果对象
//	 * @param uri
//	 *            请求uri
//	 * @Description: 构造全局结果对象（查询操作，需要返回结果对象）
//	 * @return 全局结果对象
//	 *
//	 */
//	public static GlobalResult getSuccessObject(Object resultObj, String uri) {
//		return new GlobalResult(ResultDef.SUCCESS, ResultDef.EMPTY_STRING,
//				null == resultObj ? ResultDef.EMPTY_OBJECT : resultObj, null == uri ? ResultDef.EMPTY_STRING : uri);
//	}
//
//	/**
//	 * 
//	 * .全局正确的结果对象
//	 * 
//	 * @date 2017年12月4日 上午11:21:42
//	 * @Description: 构造全局结果对象（查询操作，需要返回结果集合）
//	 * @param resultArray
//	 *            结果集合
//	 * @return 全局结果对象
//	 *
//	 */
//	public static GlobalResult getSuccessArray(Collection<?> resultArray) {
//		return new GlobalResult(ResultDef.SUCCESS, ResultDef.EMPTY_STRING,
//				null == resultArray ? ResultDef.EMPTY_LIST : resultArray, ResultDef.EMPTY_STRING);
//	}
//
//	/**
//	 * 
//	 * .全局正确的结果对象
//	 * 
//	 * @date 2017年12月4日 上午11:21:42
//	 * @Description: 构造全局结果对象（查询操作，需要返回结果集合）
//	 * @param resultArray
//	 *            结果集合
//	 * @param uri
//	 *            请求uri
//	 * @return 全局结果对象
//	 *
//	 */
//	public static GlobalResult getSuccessArray(Collection<?> resultArray, String uri) {
//		return new GlobalResult(ResultDef.SUCCESS, ResultDef.EMPTY_STRING,
//				null == resultArray ? ResultDef.EMPTY_LIST : resultArray, null == uri ? ResultDef.EMPTY_STRING : uri);
//	}
//
//	/**
//	 * 
//	 * .全局正确的结果对象
//	 * 
//	 * @date 2017年12月4日 上午11:21:42
//	 * @Description: 构造全局结果对象（添加修改操作，只需要返回正确的返回码）
//	 * @return 全局结果对象
//	 *
//	 */
//	public static GlobalResult getSuccessResult() {
//		return new GlobalResult(ResultDef.SUCCESS, ResultDef.EMPTY_STRING, ResultDef.EMPTY_OBJECT,
//				ResultDef.EMPTY_STRING);
//	}
//
//	/**
//	 * 
//	 * .全局结果错误结果对象
//	 * 
//	 * @date 2017年12月4日 上午11:21:42
//	 * @Description: 构造全局结果对象（异常情况）
//	 * @param code
//	 *            错误码
//	 * @param errorMsg
//	 *            错误描述
//	 * @param uri
//	 *            请求uri
//	 * @return 全局结果对象
//	 *
//	 */
//	public static GlobalResult getErrorResult(int code, String errorMsg, String uri) {
//		return new GlobalResult(code, null == errorMsg ? ResultDef.EMPTY_STRING : errorMsg, ResultDef.EMPTY_OBJECT,
//				null == uri ? ResultDef.EMPTY_STRING : uri);
//	}
//
//}
