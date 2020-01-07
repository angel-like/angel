package com.xmsy.server.zxyy.webhome.modules.webim.homepage.result;

import lombok.Data;

/**
 * 33娱乐推荐游戏
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21
 */
@Data
public class ImPromotionalGameResult {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图片路径
	 */
	private String enclosureUrl;
	
	/**
	 * 游戏
	 */
	private Long gameId;
	
	
}
