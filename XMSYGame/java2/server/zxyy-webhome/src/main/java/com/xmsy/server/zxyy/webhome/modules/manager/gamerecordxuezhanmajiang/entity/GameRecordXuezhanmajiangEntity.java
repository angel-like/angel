package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-血战麻将
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-11-29 10:38:57
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_xuezhanmajiang")
public class GameRecordXuezhanmajiangEntity extends BaseEntity<GameRecordXuezhanmajiangEntity> {
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
	 * 总盈亏
	 */
    private Long prizeCoins;
			/**
	 * 胡牌倍数
	 */
    private Integer cardMultiple;
			/**
	 * 是否认输
	 */
    private Boolean defeat;
			/**
	 * 是否机器人
	 */
    private Boolean robot;
			/**
	 * 是否庄家
	 */
    private Boolean banker;
			/**
	 * 游戏局号
	 */
    private String gameRoundNo;
					}
