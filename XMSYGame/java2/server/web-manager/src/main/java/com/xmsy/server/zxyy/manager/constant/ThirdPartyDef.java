package com.xmsy.server.zxyy.manager.constant;

/**
 * .第三方密钥变量名定义
 * 
 * @author Administrator
 *
 */
public class ThirdPartyDef {

	/**
	 * ===============================极光推送=================================
	 */
	public static final String JPUSH = "jpush";
	public static final String JPUSH_APPKEY = "appKey";
	public static final String JPUSH_APPSECRET = "appSecret";
	// 批量推送的人数限制
	public static final String JPUSH_BATCH = "batch";
	// 批量推送的人数默认限制1000人
	public static final Integer JPUSH_BATCH_DEFAULT = 1000;
	// 广播 (发送所有人)
	public static final int BROADCAST = 1;
	// 层级 (发送指定层级)
	public static final int HIERARCHY = 2;
	// 个推
	public static final int PERSONAL = 3;
	// 立即 (立即发送)
	public static final int IMMEDIATELY = 1;
	// 定时 (定时发送)
	public static final int TIMING = 2;
	// 循环定时 (循环发送)
	public static final int LOOP_TIMING = 3;
	// 未执行
	public static final int UN_EXECUTE = 0;
	// 成功
	public static final int PUSH_SUCCESS = 1;
	// 失败
	public static final int PUSH_FIAL = 2;

	/**
	 * ===============================七牛云=================================
	 */

	/**
	 * ===============================微信开放平台=================================
	 */
	public static final String WECHAT = "weChat";
	public static final String WECHAT_APPKEY = "appId";
	public static final String WECHAT_APPSECRET = "appSecret";

	/**
	 * ===============================短信平台=================================
	 */

	public static final String BENSI_SMS = "bensiSms";// 本思sms短信平台
	public static final String URL = "url";// 接口url
	public static final String ACCOUNT = "account";// 账号
	public static final String PASSWORD = "password";// 密码
	public static final String EXTNO = "extno";
	public static final String SMS_TEMPLATE = "smsTemplate";// 模板
	/**
	 * ===============================新浪=================================
	 */
	public static final String SINA = "sina";// 新浪
	public static final String REQUEST_URL = "request_url";// 调用地址
	public static final String APPKEY = "appkey";// 调用接口的秘钥
	public static final String ENABLE = "enable";// 是否启用
	/**
	 * ===============================app打包=================================
	 */
	public static final String ABQP = "appPack";// app打包
	public static final String NAME = "name";// 调用名称    --调用地址跟上面的新浪一样
}
