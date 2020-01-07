package com.xmsy.server.zxyy.webhome.constant;

/**
 * .33娱乐素材类别定义
 * 
 * @author aleng
 *
 */
public enum MaterialEnum {

	/**
	 * 新闻
	 */
	NEWS(1, "新闻"),

	/**
	 * 特色
	 */
	SPECIAL(2, "特色"),

	/**
	 * 活动
	 */
	ACTIVITY(3, "活动"),

	/**
	 * 维护
	 */
	MAINTENANCE(4, "维护"),
	/**
	 * 防盗
	 */
	ANTI_THEFT(5, "防盗"),
	/**
	 * 游戏截图
	 */
	GAME_SCREENSHOTS(6, "游戏截图"),
	/**
	 * 新手入门
	 */
	NEW_HAND_START(7, "新手入门"),
	/**
	 * 充值帮助
	 */
	RECHARGE_HELP(8, "充值帮助"),
	/**
	 * 玩家福利
	 */
	PLAYER_BENEFITS(9, "玩家福利"),
	/**
	 * 取款规则
	 */
	WITHDRAWALS_RULE(10, "取款规则"),
	/**
	 * 背景轮播
	 */
	BACKGROUND(11, "背景轮播"),
	/**
	 * VIP段位介绍
	 */
	VIP_INTRODUCTION(12, "段位介绍"),
	
	/**
	 * 最新消息
	 */
	LATEST_NEW(13, "最新消息");

	private Integer id;
	private String name;

	private MaterialEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
