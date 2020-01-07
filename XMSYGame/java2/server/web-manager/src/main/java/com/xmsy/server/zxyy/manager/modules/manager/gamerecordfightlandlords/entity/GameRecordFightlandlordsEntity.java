package com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlords.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-斗地主
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-21 15:08:39
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_fightlandlords")
public class GameRecordFightlandlordsEntity extends BaseEntity<GameRecordFightlandlordsEntity> {
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
     * 游戏场次名称
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
	 * 底分
	 */
    private Long baseScore;
			/**
	 * 赢输金币
	 */
    private Long coins;
    /**
     * 下注前金币
     */
    private Long coinsBefore;
    /**
     * 结束后金币
     */
    private Long coinsAfter;
			/**
	 * 是否地主
	 */
    private Boolean landlord;
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
    
    @TableField(exist=false)
    private String startTime;
    
    @TableField(exist=false)
    private String endTime;
					}
