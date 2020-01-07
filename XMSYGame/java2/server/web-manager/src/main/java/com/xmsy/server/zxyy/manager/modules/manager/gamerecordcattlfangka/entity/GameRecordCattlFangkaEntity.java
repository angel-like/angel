package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-房卡牛牛
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-03-29 14:11:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_cattl_fangka")
public class GameRecordCattlFangkaEntity extends BaseEntity<GameRecordCattlFangkaEntity> {
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
	 * 牌型
	 */
    private String cardsInt;
			/**
	 * 牌型
	 */
    private String cardsStr;
	/**
	 * 下注倍数
	 */
	private Integer betMultiple;
	/**
	* 牌倍数
	*/
	private Integer brandMultiple;
			/**
	 * 倍数
	 */
    private Integer multiple;
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
    
    /**
     * 局数
     */
    private Integer round;
    
    @TableField(exist=false)
    private String startTime;
    
    @TableField(exist=false)
    private String endTime;
    
}


