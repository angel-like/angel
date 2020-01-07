package com.xmsy.server.zxyy.game.modules.web.game;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 游戏场次等级
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-02-19 13:38:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GameGrade implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 场次id
	 */
	private String gradeId;
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
}
