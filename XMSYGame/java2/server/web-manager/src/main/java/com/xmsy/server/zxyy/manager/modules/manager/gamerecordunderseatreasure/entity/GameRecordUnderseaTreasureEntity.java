package com.xmsy.server.zxyy.manager.modules.manager.gamerecordunderseatreasure.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 游戏记录-海底宝藏
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-04-19 11:41:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("game_record_undersea_treasure")
public class GameRecordUnderseaTreasureEntity extends BaseEntity<GameRecordUnderseaTreasureEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 游戏id
	 */
	private Long gameId;
	/**
	 * 游戏名称
	 */
	private String gameName;
	/**
	 * 场次id
	 */
	private Long gradeId;
	/**
	 * 场次名称
	 */
	private String gradeName;
	/**
	 * 房间id
	 */
	private Long roomId;
	/**
	 * 房间名称
	 */
	private String roomName;
	/**
	 * 中奖倍数
	 */
	private Integer pow;
	/**
	 * 压线数
	 */
	private Integer betLines;
	/**
	 * 中奖线情况
	 */
	private String lines;
	/**
	 * 图标情况
	 */
	private String scenes;
	/**
	 * 底分
	 */
	private Long bcoins;
	/**
	 * 下注金币
	 */
	private Long betCoins;
	/**
	 * 输赢金币
	 */
	private Long prizeCoins;
	/**
	 * 下注前金币
	 */
	private Long coinsBefore;
	/**
	 * 结束后金币
	 */
	private Long coinsAfter;
	/**
	 * 小游戏倍数
	 */
	private Integer miniGameMultiple;
	/**
	 * 小游戏奖励
	 */
	private Long miniGameRewards;
	/**
	 * 是否机器人
	 */
	private Boolean robot;
	
	/**
	 * 是否小游戏
	 */
	private Boolean miniGame;
	/**
	 * 游戏局号
	 */
	private String gameRoundNo;

	@TableField(exist = false)
	private String startTime;

	@TableField(exist = false)
	private String endTime;
}
