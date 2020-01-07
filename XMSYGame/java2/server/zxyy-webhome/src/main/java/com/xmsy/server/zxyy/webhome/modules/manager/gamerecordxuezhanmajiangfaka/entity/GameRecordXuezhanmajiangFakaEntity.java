package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-房卡血战麻将
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-06 14:50:44
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_xuezhanmajiang_faka")
public class GameRecordXuezhanmajiangFakaEntity extends BaseEntity<GameRecordXuezhanmajiangFakaEntity> {
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
	 * 游戏模式（1.积分模式2.金币模式）
	 */
    private Integer gameModule;
			/**
	 * 玩法模式
	 */
    private Integer gameType;
			/**
	 * 支付类型 1.房主支付 2.AA支付
	 */
    private Integer payType;
			/**
	 * 底分
	 */
    private Long baseScore;
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
	 * 结算牌型
	 */
    private String settlementCard;
			/**
	 * 胡牌倍数
	 */
    private Integer cardMultiple;
			/**
	 * 是否庄家
	 */
    private Boolean banker;
			/**
	 * 游戏局号
	 */
    private String gameRoundNo;
			/**
	 * 局数
	 */
    private Integer round;
			/**
	 * 房间号
	 */
    private String roomNo;
					}
