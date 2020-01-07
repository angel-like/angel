package com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddezhoupuke.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-德州扑克
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-11-22 17:08:35
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_dezhoupuke")
public class GameRecordDezhoupukeEntity extends BaseEntity<GameRecordDezhoupukeEntity> {
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
	 * 底分（大盲注）
	 */
    private Long bigBaseScore;
    
		/**
	 * 底分（小盲注）
	 */
	private Long smallBaseScore;
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
	 * 公共牌型
	 */
    private String publicCardsInt;
			/**
	 * 公共牌型
	 */
    private String publicCardsStr;
			/**
	 * 牌型
	 */
    private String playCardsInt;
			/**
	 * 牌型
	 */
    private String playCardsStr;
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
