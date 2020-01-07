package com.xmsy.server.zxyy.game.modules.web.game.param;

import lombok.Data;

/**
 * 
 * 游戏下拉对象
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:51:26
 */
@Data
public class GameInfoSelectResult {
	/**
	 * 游戏名字
	 */
	private Long id;
	/**
	 * 游戏名字
	 */
	private String name;
	
	/**
	 * 显示的scene页面
	 */
	private String sence;

	/**
	 * romm_id
	 */
	private Long room;





}
