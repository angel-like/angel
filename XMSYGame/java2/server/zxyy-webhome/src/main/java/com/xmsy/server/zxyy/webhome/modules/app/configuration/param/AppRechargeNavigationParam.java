package com.xmsy.server.zxyy.webhome.modules.app.configuration.param;

import lombok.Data;

@Data
public class AppRechargeNavigationParam {
		/**
	* id
	*/
	private Long id;
	/**
	* 导航名称
	*/
	private String name;
	/**
	* 类型
	*/
	private Integer type;
	/**
	* 图片MD5值
	*/
	private String md5;
	/**
	* 图片路径
	*/
	private String enclosureUrl;
	
}
