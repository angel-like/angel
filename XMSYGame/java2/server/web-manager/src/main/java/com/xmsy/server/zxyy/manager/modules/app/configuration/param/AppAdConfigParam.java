package com.xmsy.server.zxyy.manager.modules.app.configuration.param;

import java.sql.Date;

import lombok.Data;

@Data

public class AppAdConfigParam {
		/**
	* 名称
	*/
	private String name;
	/**
	* 跳转类型
	*/
	private Long type;
	/**
	* 跳转路径
	*/
	private String url;
	
	/**
	* 图片路径
	*/
	private String encloseUrl;
	/**
	* 图片MD5值
	*/
	private String md5;
	/**
	* 图片MD5值
	*/
	private Date createTime;
	
	
}
