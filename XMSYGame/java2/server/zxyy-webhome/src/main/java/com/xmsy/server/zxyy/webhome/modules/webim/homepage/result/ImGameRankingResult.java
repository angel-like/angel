package com.xmsy.server.zxyy.webhome.modules.webim.homepage.result;

import lombok.Data;

/**
 * 33娱乐热门游戏排行榜
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21
 */
@Data
public class ImGameRankingResult {
	/**
	 * title
	 */
	private String title;
	/**
	 * 图片路径
	 */
	private String enclosureUrl;
	/**
	 * 连赢场数
	 */
	private int num;
	/**
	 * 排序号
	 */
	private int orderNo;
}
