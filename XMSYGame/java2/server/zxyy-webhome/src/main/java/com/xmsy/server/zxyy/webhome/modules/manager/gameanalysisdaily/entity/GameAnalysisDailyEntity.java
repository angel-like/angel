package com.xmsy.server.zxyy.webhome.modules.manager.gameanalysisdaily.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.utils.SpringUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 每日游戏总下注记录
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-04-18 14:59:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("game_analysis_daily")
public class GameAnalysisDailyEntity extends BaseEntity<GameAnalysisDailyEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 房间id
	 */
	private Long roomId;
	/**
	 * 房间名称
	 */
	@TableField(exist = false)
	private String roomName;
	/**
	 * 游戏id
	 */
	private Long gameId;
	/**
	 * 游戏名称
	 */
	@TableField(exist = false)
	private String gameName;
	/**
	 * 场次id
	 */
	private Long gradeId;
	/**
	 * 场次名称
	 */
	@TableField(exist = false)
	private String gradeName;
	/**
	 * 有效下注
	 */
	private Long validBet;
	/**
	 * 中奖金币
	 */
	private Long prizeCoins;
	/**
	 * 抽水金额
	 */
	private BigDecimal waterProfit;
	/**
	 * 代理商盈利金币
	 */
	private Long profitCoins;
	/**
	 * 用户数量
	 */
	private Integer userNum;
	/**
	 * 结算日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date countDay;
	/**
	 * 会员返水比例
	 */
	private BigDecimal waterRate;
	/**
	 * 是否机器人
	 */
	private Boolean robot;

	public String getRoomName() {
//		return SysConstant.roomMap.get(roomId);
		return SpringUtil.getApplicationContext().getBean(LocalContentCache.class).getRoomName(roomId);
	}

	public String getGameName() {
//		return SysConstant.gameMap.get(gameId);
		return SpringUtil.getApplicationContext().getBean(LocalContentCache.class).getGameName(gameId);
	}

	public String getGradeName() {
//		return SysConstant.gradeMap.get(gradeId);
		return SpringUtil.getApplicationContext().getBean(LocalContentCache.class).getGradeName(gradeId);
	}
}
