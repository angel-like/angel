package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-匹配炸金花
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-03-29 09:33:49
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_pipeizhajinhua")
public class GameRecordPipeizhajinhuaEntity extends BaseEntity<GameRecordPipeizhajinhuaEntity> {
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
    private String cardsInt;
	/**
	 * 牌型
	 */
    private String cardsStr;
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
