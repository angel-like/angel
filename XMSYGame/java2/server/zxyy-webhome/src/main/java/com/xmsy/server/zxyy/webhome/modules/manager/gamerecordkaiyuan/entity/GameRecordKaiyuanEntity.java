package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 开源游戏
 * 
 * @author sanqian
 * @email xxxxx
 * @date 2019-12-02 14:25:01
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_kaiyuan")
public class GameRecordKaiyuanEntity extends BaseEntity<GameRecordKaiyuanEntity> {
	private static final long serialVersionUID = 1L;
			/**
	 * 游戏局号列表
	 */
    private String gameId;
			/**
	 * 玩家帐号列表
	 */
    private String accounts;
			/**
	 * 房间 ID 列表
	 */
    private Long serverId;
			/**
	 * 游戏 ID 列表
	 */
    private Long kindId;
			/**
	 * 桌子号列表
	 */
    private Long tableId;
			/**
	 * 椅子号列表
	 */
    private Long chairId;
			/**
	 * 玩家数量列表
	 */
    private Long userCount;
			/**
	 * 手牌公共牌
	 */
    private String cardValue;
			/**
	 * 有效下注
	 */
    private Long cellScore;
			/**
	 * 总下注列表
	 */
    private Long allBet;
			/**
	 * 盈利列表
	 */
    private Long profit;
			/**
	 * 抽水列表
	 */
    private Long revenue;
			/**
	 * 游戏开始时间列表
	 */
    private Date gameStartTime;
			/**
	 * 游戏结束时间列表
	 */
    private Date gameEndTime;
			/**
	 * 渠道 ID 列表
	 */
    private Long channelId;
			/**
	 * 游戏结果对应玩家所属站点
	 */
    private String lineCode;

    private Long  userId;

    private String  gameName;

    private String gradeName;

    private String roomName;

					}
