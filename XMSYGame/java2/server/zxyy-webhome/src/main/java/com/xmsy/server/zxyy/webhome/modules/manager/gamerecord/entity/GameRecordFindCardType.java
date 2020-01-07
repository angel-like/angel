package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity;

import lombok.Data;

@Data
public class GameRecordFindCardType {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 牌型
	 */
	private String cardsStr;
	
	/**
	 * 局号
	 */
	private String gameRoundNo;
	
	/**
	 * 局数
	 */
	private Integer round;
	
	/**
	 * 下注区域
	 */
	private String betAreaStr;
	
	/**
	 * 玩家牌型
	 */
	private String idleCardStr;
	
	/**
	 * 庄家牌型
	 */
	private String bankerCardStr;
	/**
	 * 丢弃的牌
	 */
	private String openCardStr;
	
	/**
	 * 龙牌
	 */
	private String dragonCardStr;
	
	/**
	 * 虎牌
	 */
	private String tigerCardStr;
	
	
}
