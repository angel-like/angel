package com.xmsy.server.zxyy.webhome.modules.gamemanage;

import lombok.Data;

@Data
public class GameSelectParam {
	/**
	 * 游戏Id
	 */
	private Long id;
	/**
	 * 游戏名称
	 */
	private String name;
	/**
	 * 游戏场景名称
	 */
	private String sence;

	/**
	 * romm_id
	 */
	private Long room;
}
