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
public class GameInfoSelectForRobotResult {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 游戏名字
	 */
	private Long gameId;
	/**
	 * 游戏名字
	 */
	private String gameName;
	
	/**
	 * 场次ID
	 */
	private Long gradeId;
	/**
	 * 场次名字
	 */
	private String gradeName;
	/**
	 * 场次名字
	 */
	private String roomId;

	
	

	

}
