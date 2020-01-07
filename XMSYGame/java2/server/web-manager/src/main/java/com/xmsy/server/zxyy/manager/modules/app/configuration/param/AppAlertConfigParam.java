package com.xmsy.server.zxyy.manager.modules.app.configuration.param;

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
	
}
