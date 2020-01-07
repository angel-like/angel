package com.xmsy.server.zxyy.manager.modules.app.gamerecord.result;

import lombok.Data;

/**
 * app查询房间游戏
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-09 10:00:25
 */
@Data
public class AppGameParams {

	/**
	 * 游戏Id
	 */
	private Long gameId;
	/**
	 * 游戏名
	 */
	private String gameName;

}
