package com.xmsy.common.define.constant;

import java.util.ArrayList;

/**
 * 
 * .错误码定义
 *
 * @author aleng
 * @date 2017年12月1日 下午2:33:42
 * @Description: TODO
 */
public class ResultDef {

	// 第三方返回成功
	public static final int OAUTH_SUCCESS = 0;
	// 成功返回码
	public static final int SUCCESS = 200;
	// 默认失败返回码
	public static final int FAIL = 500;
	// 没有数据返回码
	public static final int NO_DATA = 220;
	// 空json对象
	public static final String EMPTY_OBJECT = "{}";
	// 成功字符串
	public static final String SUCCESS_MSG = "success";
	// 空list
	public static final ArrayList<String> EMPTY_LIST = new ArrayList<>();

	/**
	 * 
	 * ------------------ 未知异常：1000 --------------------------
	 */
	// 未知异常
	public static final int UNKNOW_ERROR_CODE = 1000;
	public static final String UNKNOW_ERROR_MSG = "未知异常";
	/**
	 * 
	 * ------------------ 权限异常：2000-2999 --------------------------
	 */
	public static final int PERMISSION_DENIED_CODE = 2000;
	public static final String PERMISSION_DENIED_MSG = "权限被拒绝";
	public static final int TOKEN_INVALID_CODE = 2001;
	public static final String TOKEN_INVALID_MSG = "token无效";

	/**
	 * 
	 * ------------------ 系统内部异常：3000-3999 --------------------------
	 */
	public static final int INNER_ERROR_CODE = 3000;
	public static final String INNER_ERROR_MSG = "系统内部错误";
	/**
	 * 
	 * ------------------ 网络异常：4000-4999 --------------------------
	 */
	public static final int NETWORK_ERROR_CODE = 4000;
	public static final String NETWORK_ERROR_MSG = "网络错误";
	public static final int URL_ISNULL_CODE = 4001;
	public static final String URL_ISNULL_MSG = "请求url为空";
	public static final int SMS_ERROR_CODE = 4002;
	public static final String SMS_ERROR_MSG = "短信服务异常";
	public static final int REDIS_ERROR_CODE = 4003;
	public static final String REDIS_ERROR_MSG = "redis服务异常";

	/**
	 * 
	 * ------------------ 业务异常：5000-5999（后面从5500开始，不然会和webhome错误码冲突）
	 * --------------------------
	 */
	// 参数不合法
	public static final int PARAM_INVALID_CODE = 5000;
	public static final String PARAM_INVALID_MSG = "参数不合法";

	public static final int ORDER_REPEAT_CODE = 6000;
	public static final String ORDER_REPEAT_MSG = "订单正在处理中，请不要重复提交";
}
