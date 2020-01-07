package com.xmsy.server.zxyy.manager.modules.gamemanage;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Data;

@Data
public class GameSelectParam {
	/**
	 * 游戏Id
	 */
	private Long id;
	/**
	 * 游戏名称
	 */
	private String name;
	/**
	 * 游戏场景名称
	 */
	private String sence;
	/**
	 * 开始时间
	 */
	@TableField(exist=false)
	private String startTime;
	/**
	 * 结束时间
	 */
	@TableField(exist=false)
	private String endTime;

	private Long room;
	
}
