package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LabaBetUserRecordParams {
	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空")
	private Long userId;
	/**
	 * 账号名称
	 */
	@NotBlank(message = "用户账号不能为空")
	private String userAccount;
	/**
	 * 下注前金币
	 */
	@NotNull(message = "下注前金币不能为空")
	private Long coinsBefore;
	/**
	 * 下注金币
	 */
	@NotNull(message = "下注金币不能为空")
	private Long betCoins;
	/**
	 * 盈利金币
	 */
	@NotNull(message = "盈利金币不能为空")
	private Long prizeCoins;
	/**
	 * 结束后金币
	 */
	@NotNull(message = "结束后金币不能为空")
	private Long coinsAfter;
	
	/**
	 * 小游戏倍数
	 */
	private Integer miniGameMultiple;
	
	/**
	 * 小游戏奖励
	 */
	private Long miniGameRewards;
	
	/**
	 * 底分
	 */
	@NotNull(message = "底分不能为空")
	private Long bcoins;
	/**
	 * 倍数
	 */
	@NotNull(message = "倍数")
	private Integer pow;
	/**
	 * 中奖线数不能为空
	 */
	@NotNull(message = "中奖线数不能为空")
	private String[] lines;
	/**
	 * 押注线数
	 */
	@NotNull(message = "押注线数不能为空")
	private Integer betLines;
	
	/**
	 * 是否小游戏
	 */
	private Boolean miniGame;
	
	/**
	 * 图标情况不能为空
	 */
	@NotEmpty(message = "图标情况不能为空")
	private String scenes;
	
	/**
	 * 是否机器人
	 */
	@NotNull(message = "是否机器人不能为空")
	private Boolean robot;


}
