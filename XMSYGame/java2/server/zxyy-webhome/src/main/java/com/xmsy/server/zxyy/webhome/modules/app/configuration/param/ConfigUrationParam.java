package com.xmsy.server.zxyy.webhome.modules.app.configuration.param;

import lombok.Data;

@Data

public class ConfigUrationParam {
		/**
	* 客服
	*/
	private String name;
	/**
	* 客服类型
	*/
	private Integer type;
	/**
	* 客服号
	*/
	private String typeNum;
	/**
	* 客服号
	*/
	private String tips;
	/**
	* 图片路径
	*/
	private String icon;
	/**
	* 图片MD5值
	*/
	private String iconMd5;
	
}
