package com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 联系管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-01 16:52:13
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class WebContactManagementEntity {
		
			/**
	 * 名称
	 */
    private String name;
			/**
	 * 内容
	 */
    private String content;
    /**
	 * 类型
	 */
    private String type;
    
			/**
	 * 图片路径
	 */
    private String url;
    /**
	 * 跳转路径
	 */
    private String jumpUrl;
	}
