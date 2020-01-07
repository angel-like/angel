package com.xmsy.server.zxyy.manager.modules.web.homepage.entity;

import lombok.Data;


/**
 * 官网热门游戏
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-29 16:07:37
 */
@Data
public class PopularGamesEntity  {
	/**
	 * 名称
	 */
    private Long id;
	/**
	 * 名称
	 */
    private String name;
			/**
	 * 附件ID
	 */
    private String enclosureUrl;
			/**
	 * 介绍
	 */
    private String introduction;
			/**
	 * 类型(menu，game）
	 */
    private String type;
			/**
	 * 类型Id
	 */
    private Long typeId;
			/**
	 * 路径
	 */
    private String url;
			
	}
