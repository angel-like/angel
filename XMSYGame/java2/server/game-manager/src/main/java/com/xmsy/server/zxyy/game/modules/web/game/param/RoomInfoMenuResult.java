package com.xmsy.server.zxyy.game.modules.web.game.param;

import java.util.List;

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
public class RoomInfoMenuResult {
	/**
	 * 房间id（匹配房，房卡房，百人房）
	 */
	private Long roomId;
	/**
	 * 房间名称（匹配房，房卡房，百人房）
	 */
	private String name;
	/**
	 * 游戏列表
	 */
	private List<GameInfoMenuResult> gameList;
	
	

}
