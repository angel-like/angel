package com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity;

import lombok.Data;

@Data
public class GameRecordFindCardType {
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 游戏ID
	 */
	private String gameId;
	
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
	
	/**
	 * 红方牌型
	 */
	private String redCardStr;
	
	/**
	 * 黑方牌型
	 */
	private String blackCardStr;

	/**
	 * 开奖牌型
	 */
	private String lotteryCardStr;
	
	/**
	 * 玩家牌型
	 */
	private String playerCardStr;
	
	/**
	 * 玩家牌型
	 */
	private String playCardsStr;
	
	/**
	 * 公共牌型
	 */
	private String publicCardsStr;
	
	
	/**
	 * 结算牌型
	 */
	private String settlementCardStr;
	
	/**
	 * 结算牌型
	 */
	private String settlementCard;
	
	private  Long   chairId;
	private  String cardString;
	
	
	
	
}
