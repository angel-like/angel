package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlordsfangka.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-房卡斗地主
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-07-31 10:57:08
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_fightlandlords_fangka")
public class GameRecordFightlandlordsFangkaEntity extends BaseEntity<GameRecordFightlandlordsFangkaEntity> {
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
	 * 玩法模式（1.抢庄模式、2.通比模式）
	 */
    private Integer gameType;
			/**
	 * 支付类型（1.房主支付、2.AA支付）
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
	 * 房间号
	 */
    private String roomNo;
    
    @TableField(exist=false)
    private String startTime;
    
    @TableField(exist=false)
    private String endTime;
					}
