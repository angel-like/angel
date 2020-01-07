package com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity;

import lombok.Data;

/**
 * 游戏胜率区间参数
 * @author axiong
 *
 */
@Data
public class IntervalGameRateParam {
	private Long gameId;
	private Long id[];
	private String startVal;
	private String endVal;
	private String intervalGameRate;
}
