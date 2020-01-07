package com.xmsy.server.zxyy.webhome.common.utils;

import java.util.ResourceBundle;

/**
 * 系统参数相关Key
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2017-03-26 10:33
 */
public class ConfigConstant {
	/**
	 * 云存储配置KEY
	 */
	public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

	public static final ResourceBundle RESOURCE = ResourceBundle.getBundle("definition");

	// 官网注册页面
	public static final String OFFICIAL_URL = RESOURCE.getString("official-url") == null ? null
			: RESOURCE.getString("official-url");
	// 官网客服
	public static final String SUPPORT_URL = RESOURCE.getString("support-url") == null ? null
			: RESOURCE.getString("support-url");
}
