package com.xmsy.server.zxyy.webhome.modules.webim.homepage.result;

import java.util.Collection;

import lombok.Data;

/**
 * 33娱乐推荐游戏
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21
 */
@Data
public class ImPromotionalRoomGameResult {
	/**
	 * 房间名称
	 */
	private String roomName;

	/**
	 * 游戏
	 */
	private Collection<ImPromotionalGameResult> gameList;
	
	
}
