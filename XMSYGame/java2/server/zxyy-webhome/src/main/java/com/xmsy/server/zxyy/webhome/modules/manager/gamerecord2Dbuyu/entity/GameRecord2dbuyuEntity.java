package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏记录-2D捕鱼
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-30 11:21:16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_2Dbuyu")
public class GameRecord2dbuyuEntity extends BaseEntity<GameRecord2dbuyuEntity> {
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
	 * 游戏模式（经典模式）
	 */
    private Integer gameModule;
			/**
	 * 击杀次数
	 */
    private String killNumberInt;
			/**
	 * 击杀次数
	 */
    private String killNumberStr;
			/**
	 * 底分
	 */
    private Long baseScore;
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
					}
