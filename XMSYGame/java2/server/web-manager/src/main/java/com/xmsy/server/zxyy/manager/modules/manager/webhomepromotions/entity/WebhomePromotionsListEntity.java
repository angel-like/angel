package com.xmsy.server.zxyy.manager.modules.manager.webhomepromotions.entity;

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
public class WebhomePromotionsListEntity  {
			/**
	 * 附件ID
	 */
    private String enclosureUrl;
    
    /**
	 * 时间
	 */
    private Date createTime;
    
			/**
	 * 内容
	 */
    private String content;
	}
