package com.xmsy.server.zxyy.game.modules.manager.game.entity.result;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

/**
 * 游戏封装返回
 * 
 * @author Administrator
 *
 */
@Data
public class GameResult {
	
	/**
	 * id
	 */
	private Long id;

	/**
	 * 游戏id
	 */
	private Long gameId;
	/**
	 * 游戏名字
	 */
	private String gameName;
	/**
	 * 房间id（匹配房，房卡房，百人房）
	 */ 
	private Long roomId;
	/**
	 * 房间名
	 */
	private String roomName;
	/**
	 * 游戏场次id
	 */
	private Long gradeId;
	/**
	 * 游戏场次名称
	 */
	private String gradeName;
	/**
	 * 是否启用
	 */
	private Boolean enable;
	/**
	 * 是否显示
	 */
	private Boolean display;
	/**
	 * 显示的scene页面
	 */
	private String sence;
	/**
	 * 抽水比率
	 */
	private BigDecimal rate;
	/**
	 * 游戏低分
	 */
	private Long bscore;
	/**
	 * 入场限制
	 */
	private Long restrict;
	/**
	 * 最高倍率
	 */
	private Long maxTimes;
	/**
	 * 线数
	 */
	private Long maxLines;
	/**
	 * 是否维护中
	 */
	private Boolean maintenance;
	/**
	 * 限低，踢出房间
	 */
	private Long limitLower;
	/**
	 * 限高
	 */
	private Long limitHigh;
	/**
	 * 在线人数区间最小值
	 */
	private Long onlineMin;
	/**
	 * 在线人数区间最大值
	 */
	private Long onlineMax;
	
	/**
	 * 是否完成
	 */
	private Boolean finished;
	/**
	 * 游戏配置属性
	 */
	private Map<String, Object> gameConfig;

	private Long provider;
	
}
