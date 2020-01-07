package com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.calculate.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-22 11:12:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("game_record")
public class GameRecordEntity extends BaseEntity<GameRecordEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	* 
	*/
	private String userAccount;
	/**
	 * 游戏id
	 */
	private Long gameId;
	/**
	 * 游戏名
	 */
	private String gameName;
	/**
	 * 场次id
	 */
	private Long gradeId;
	/**
	 * 场次名
	 */
	private String gradeName;
	
	/**
	 * 房间id
	 */
	private Long roomId;
	/**
	 * 房间名
	 */
	private String roomName;
	/**
	 * 下注总金币
	 */
	private Long betCoins;
	/**
	 * 中奖金币
	 */
	private Long prizeCoins;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * 是否小游戏
	 */
	private Boolean mini;
	/**
	 * 是否机器人
	 */
	private Boolean robot;
	/**
	 * 代理商盈利金币
	 */
	private Long profitCoins;
	/**
	 * 抽水金额
	 */
	private BigDecimal waterProfit;
	/**
	 * 抽水比例
	 */
	private BigDecimal waterRate;
	/**
	 * 房卡转金币的汇率
	 */
	private BigDecimal exchangeRate;
	
	/**
	 * 用户余额
	 */
	@TableField(exist = false)
	private BigDecimal money;
	
	/**
	 * 金币余额
	 */
	@TableField(exist = false)
	private Long coin;
	/**
	 * 游戏局号
	 */
	private String gameRoundNo;

	/**
	 * 有效投注
	 */
	private Long validBet;
}
