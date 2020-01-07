package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuai.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-跑得快
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 11:33:22
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_paodekuai")
public class GameRecordPaodekuaiEntity extends BaseEntity<GameRecordPaodekuaiEntity> {
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
	 * 剩余张数
	 */
    private Integer surplusCardsNum;
			/**
	 * 被炸弹数
	 */
    private Integer coverBombNum;
			/**
	 * 所出炸弹数
	 */
    private Integer bombNum;
    
		/**
	 * 总炸弹数
	 */
	private Integer totalBombNum;
			/**
	 * 包赔数
	 */
    private Integer compensateNum;
			/**
	 * 总盈亏
	 */
    private Long prizeCoins;
			/**
	 * 赢输金币
	 */
    private Long coins;
			/**
	 * 下注金币
	 */
    private Long betCoins;
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
    
    @TableField(exist=false)
    private String startTime;
    
    @TableField(exist=false)
    private String endTime;
					}
