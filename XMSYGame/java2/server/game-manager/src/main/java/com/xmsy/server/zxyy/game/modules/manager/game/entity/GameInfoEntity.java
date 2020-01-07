package com.xmsy.server.zxyy.game.modules.manager.game.entity;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.game.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:51:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("game_info")
public class GameInfoEntity extends BaseEntity<GameInfoEntity> implements Cloneable {

	private static final long serialVersionUID = 1L;
	/**
	 * 游戏id
	 */
	private Long gameId;
	/**
	 * 游戏名字
	 */
	private String gameName;
	/**
	 * 房间id（匹配房，房卡房，百人房）
	 */
	private Long roomId;
	/**
	 * 游戏场次id
	 */
	private Long gradeId;
	/**
	 * 是否启用
	 */
	private Boolean enable;
	/**
	 * 是否显示
	 */
	private Boolean display;
	/**
	 * 显示的scene页面
	 */
	private String sence;
	/**
	 * 比率
	 */
	private BigDecimal rate;
	/**
	 * 游戏低分
	 */
	private Long bscore;
	/**
	 * 入场限制
	 */
	private Long restrict;
	/**
	 * 最高倍率
	 */
	private Long maxTimes;
	/**
	 * 线数
	 */
	private Long maxLines;

	/**
	 * 是否维护中
	 */
	private Boolean maintenance;
	/**
	 * 限低
	 */
	private Long limitLower;
	/**
	 * 限高
	 */
	private Long limitHigh;
	/**
	 * 在线人数区间开始
	 */
	private Long onlineMin;
	/**
	 * 在线人数区间结束
	 */
	private Long onlineMax;
	/**
	 * 是否完成
	 */
	private Boolean finished;

	/**
	 * 开元游戏标识 0-本地 1-开元
	 */
	private Long provider;

	/**
	 * 游戏配置属性
	 */
	@TableField(exist = false)
	private Map<String, Object> gameConfig;

	@Override
	public GameInfoEntity clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (GameInfoEntity) super.clone();
	}
}
