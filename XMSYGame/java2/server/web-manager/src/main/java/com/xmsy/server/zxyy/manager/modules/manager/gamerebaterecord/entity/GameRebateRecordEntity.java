package com.xmsy.server.zxyy.manager.modules.manager.gamerebaterecord.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏返利记录表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-02-21 20:14:13
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_rebate_record")
public class GameRebateRecordEntity extends BaseEntity<GameRebateRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 游戏ID
	 */
    private Long gameId;
			/**
	 * 用户ID
	 */
    private Long userId;
			/**
	 * 返利金额
	 */
    private Long rebateCoin;
			/**
	 * 返利时间
	 */
    private Date rebateTime;
	}
