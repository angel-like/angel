package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-房卡十三水
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-28 16:17:54
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_shisanshui_fangka")
public class GameRecordShisanshuiFangkaEntity extends BaseEntity<GameRecordShisanshuiFangkaEntity> {
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
     * 游戏模式
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
	 * 牌型
	 */
    private String headCardsInt;
			/**
	 * 牌型
	 */
    private String headCardsStr;
			/**
	 * 中墩牌型
	 */
    private String middleCardsInt;
			/**
	 * 中墩牌型
	 */
    private String middleCardsStr;
			/**
	 * 尾墩牌型
	 */
    private String bottomCardsInt;
			/**
	 * 尾墩牌型
	 */
    private String bottomCardsStr;
			/**
	 * 倍数
	 */
    private Integer multiple;
			/**
	 * 是否机器人
	 */
    private Boolean robot;
			/**
	 * 游戏局号
	 */
    private String gameRoundNo;
    /**
     * 局数
     */
    private Integer round;
    
    /**
     * 游戏房间号
     */
    private String roomNo;
	
}
