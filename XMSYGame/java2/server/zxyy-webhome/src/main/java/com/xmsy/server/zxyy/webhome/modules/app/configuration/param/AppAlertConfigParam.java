package com.xmsy.server.zxyy.webhome.modules.app.configuration.param;

import lombok.Data;

@Data

public class AppAlertConfigParam {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 路径
	 */
	private String url;
	/**
	 * 图片路径
	 */
	private String enclosureUrl;
	/**
	 * 图片MD5值
	 */
	private String md5;
	/**
	 * 类型
	 */
	private Integer type;

}
