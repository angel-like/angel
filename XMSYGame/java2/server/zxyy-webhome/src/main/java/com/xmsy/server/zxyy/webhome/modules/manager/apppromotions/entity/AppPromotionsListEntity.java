package com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.entity;

import java.sql.Date;

import lombok.Data;


/**
 * 官网优惠活动
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
@Data
public class AppPromotionsListEntity  {
			/**
	 * 标题路径
	 */
    private String enclosureUrl;
    
    /**
	 * 时间
	 */
    private Date createTime;
    
			/**
	 * 内容路径
	 */
    private String content;
	}
