package com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity;

import java.io.Serializable;

import lombok.Data;
/**
 * 游戏配置详情参数
 * @author axiong
 *
 */
@Data
public class GameConfigDetailParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long gameId;
	private String initialStock;
	private String currentStock;
	private String goalStock;
	private String intervalGameRate;
	private String maxRobot;
	private String miniRate;
	private String robotWait;
	private String cumulativeStock;
	private String profitValue;
	private String limitRedMax;
	private String vipLimitRed;
	private String chipMin;
	private String chipMax;
	private String differentBetArea;
	private String tenChips;
}
