package com.xmsy.server.zxyy.game.modules.web.game.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 游戏场次等级
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-02-19 13:38:51
 */
@Data
public class GameGradeResult implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	/**
	 * 场次id
	 */
	private Long gradeId;
	/**
	 * 场次名称
	 */
	private String gradeName;

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
	 * 游戏低分
	 */
	private Long bscore;
	/**
	 * 入场限制
	 */
	private Long restrict;
	/**
	 * 限高
	 */
	private Long limitHigh;
	/**
	 * 最高倍率
	 */
	private Long maxTimes;
	/**
	 * 线数
	 */
	private Long maxLines;
	
	/**
	 * 比率
	 */
	private BigDecimal rate;
	/**
	 * 在线人数区间最小值
	 */
	private Long onlineMin;
	/**
	 * 在线人数区间最大值
	 */
	private Long onlineMax;

	/**
	 * 是否维护中
	 */
	private Boolean maintenance;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof GameInfoResult) {
			GameGradeResult o = (GameGradeResult) obj;
			return this.gradeId == o.gradeId;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return gradeId.intValue();
	}

	@Override
	public GameGradeResult clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (GameGradeResult) super.clone();
	}
}
