package com.xmsy.server.zxyy.manager.modules.web.homepage.entity;

import lombok.Data;


/**
 * 官网弹窗配置表

 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-03 10:23:36
 */
@Data
public class WebhomeAlertEntity {
	/**
	 * 介绍
	 */
    private String introduction;
			/**
	 * 附件ID
	 */
    private String enclosureUrl;
			/**
	 * 跳转路径
	 */
    private String url;
			/**
	 * 排序号
	 */
    private Integer num;
	}
