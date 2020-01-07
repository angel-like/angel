package com.xmsy.server.zxyy.manager.modules.app.gamerecord.result;

import lombok.Data;

/**
 * app查询游戏记录的参数
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-09 10:00:25
 */
@Data
public class AppGameRecordParams {

	/**
	 * 游戏名
	 */
	private String gameName;
	/**
	 * 场次名
	 */
	private String gradeName;
	/**
	 * 下注金额
	 */
	private Long betCoins;
	/**
	 * 盈利金额
	 */
	private Long prizeCoins;
	/**
	 * 游戏时间
	 */
	private String createTime;
	
//	/**
//	 * 是否小游戏
//	 */
//	private Boolean mini;

}
