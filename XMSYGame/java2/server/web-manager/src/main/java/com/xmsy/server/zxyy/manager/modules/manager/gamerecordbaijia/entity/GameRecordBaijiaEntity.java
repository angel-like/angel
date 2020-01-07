package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbaijia.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 游戏记录-百家乐
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-09 15:37:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("game_record_baijia")
public class GameRecordBaijiaEntity extends BaseEntity<GameRecordBaijiaEntity> {
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
	 * 庄家牌型
	 */
	private String bankerCard;
	/**
	 * 庄家牌型字符
	 */
	private String bankerCardStr;
	/**
	 * 闲家牌型
	 */
	private String idleCard;
	/**
	 * 闲家牌型字符
	 */
	private String idleCardStr;
	/**
	 * 下注区域
	 */
	private String betArea;
	/**
	 * 下注区域字符
	 */
	private String betAreaStr;
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
	 * 是否机器人
	 */
	private Boolean robot;
	/**
	 * 游戏局号
	 */
	private String gameRoundNo;
	
	@TableField(exist = false)
	private String startTime;

	@TableField(exist = false)
	private String endTime;
}
