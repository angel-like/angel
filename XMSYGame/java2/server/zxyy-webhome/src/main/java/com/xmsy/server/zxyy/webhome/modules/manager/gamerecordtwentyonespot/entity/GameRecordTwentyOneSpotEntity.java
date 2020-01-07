package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-21点
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 17:05:40
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_twenty_one_spot")
public class GameRecordTwentyOneSpotEntity extends BaseEntity<GameRecordTwentyOneSpotEntity> {
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
	 * 场次编号
	 */
    private Integer gradeNumber;
			/**
	 * 房间id
	 */
    private Long roomId;
			/**
	 * 房间名称
	 */
    private String roomName;
			/**
	 * 底注
	 */
    private Long baseScore;
			/**
	 * 额外加注
	 */
    private Long additionalScore;
			/**
	 * 牌型盈亏
	 */
    private Long cardsPrize;
			/**
	 * 保险盈亏
	 */
    private Long insurancePrize;
			/**
	 * 总盈亏
	 */
    private Long prizeCoins;
			/**
	 * 玩家牌型
	 */
    private String playerCard;
			/**
	 * 玩家牌型字符
	 */
    private String playerCardStr;
			/**
	 * 庄家牌型
	 */
    private String bankerCard;
			/**
	 * 庄家牌型字符
	 */
    private String bankerCardStr;
			/**
	 * 下注前金币 
	 */
    private Long coinsBefore;
			/**
	 * 下注金币
	 */
    private Long betCoins;
			/**
	 * 赢输金币
	 */
    private Long coins;
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
					}
