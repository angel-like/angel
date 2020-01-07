package com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 官网图片管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-31 16:23:23
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class WebHomeImageManagementEntity {
	/**
	 * id
	 */
    private Long id;
	/**
	 * 图片类型（LOG,轮播图，对联）
	 */
    private String type;
			/**
	 * 状态(启用，禁用)
	 */
    private Boolean enable;
			/**
	 * 排序号
	 */
    private Integer num;
    	/**
	 * 图片名称
	 */
    private String name;
    	/**
	 * 图片路径
	 */
    private String url;
    /**
	 * 跳转路径
	 */
    private String returnUrl;
	}
