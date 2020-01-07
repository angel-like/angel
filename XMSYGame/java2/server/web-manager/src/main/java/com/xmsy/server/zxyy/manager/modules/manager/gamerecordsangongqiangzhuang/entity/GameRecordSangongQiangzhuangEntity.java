package com.xmsy.server.zxyy.manager.modules.manager.gamerecordsangongqiangzhuang.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-抢庄三公
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 15:06:19
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_sangong_qiangzhuang")
public class GameRecordSangongQiangzhuangEntity extends BaseEntity<GameRecordSangongQiangzhuangEntity> {
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
	 * 房间底分
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
	 * 玩家牌型字符串
	 */
    private String playerCardStr;
			/**
	 * 玩家牌型
	 */
    private String playerCard;
			/**
	 * 结算牌型字符串
	 */
    private String settlementCardStr;
			/**
	 * 结算牌型
	 */
    private String settlementCard;
			/**
	 * 总盈亏
	 */
    private Long prizeCoins;
			/**
	 * 下注倍数
	 */
    private Integer betMultiple;
    
	/**
	* 庄家倍数
	*/
	private Integer bankerMultiple;
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
    
    @TableField(exist=false)
    private String startTime;
    
    @TableField(exist=false)
    private String endTime;
					}
