package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.robot.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-02 11:32:49
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
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
	 * 
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
	 * 代理商盈利金币
	 */
    private Long profitCoins;
			/**
	 * 抽水金额
	 */
    private BigDecimal waterProfit;
			/**
	 * 房卡和金币的兑换比例
	 */
    private BigDecimal exchangeRate;
			/**
	 * 抽水比例
	 */
    private BigDecimal waterRate;
    /**
	 * 局数
	 */
	private Integer round;
			/**
	 * 游戏局号
	 */
    private String gameRoundNo;
			/**
	 * 有效下注
	 */
    private Long validBet;
			/**
	 * 是否机器人
	 */
    private Boolean robot;
    /**
	 * 时间
	 */
    @TableField(exist=false)
    private String queryTime;



	/**
	 * 运营商编码
	 */

//	private  Long providerCode;
	}
