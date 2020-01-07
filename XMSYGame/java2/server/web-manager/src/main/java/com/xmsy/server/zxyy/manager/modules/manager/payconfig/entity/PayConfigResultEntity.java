package com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity;

import lombok.Data;


/**
 * APP/WEB返回参数
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-25 10:09:06
 */
@Data
public class PayConfigResultEntity {
							/**
	 * id
	 */
    private Long id;
			
    /**
	 * 别名
	 */
    private String aliasName;
    /**
	 * 图标路径
	 */
    private String iconUrl;
    /**
	 * 图标路径MD5
	 */
    private String iconMd5;
    /**
	 * 图片路径
	 */
    private String enclosureUrl;
    /**
	 * 图片路径MD5
	 */
    private String enclosureMd5;
    /**
	 * 是否是推荐
	 */
    private String firstPush;
    /**
	 * 是否是推荐
	 */
    private Integer orderNum;
    
    
	}
