package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.robot.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户金币
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-02 11:44:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserCoinParams {

	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空", groups = AddGroup.class)
	private Long userId;
	/**
	 * 账号名称
	 */
	@NotBlank(message = "用户账号不能为空", groups = AddGroup.class)
	private String userAccount;
	/**
	 * 下注总金币
	 */
	@NotNull(message = "下注总金币不能为空", groups = AddGroup.class)
	private Long betCoins;
	/**
	 * 有效下注
	 */
	@NotNull(message = "有效下注不能为空", groups = AddGroup.class)
	private Long validBet;
	/**
	 * 中奖金币
	 */
	@NotNull(message = "中奖金币不能为空", groups = AddGroup.class)
	private Long prizeCoins;

	/**
	 * 抽水金额
	 */
	// @NotNull(message = "抽水金额不能为空", groups = AddGroup.class)
	private BigDecimal waterProfit;

	/**
	 * 抽水金额
	 */
	// @NotNull(message = "抽水比率不能为空", groups = AddGroup.class)
	private BigDecimal waterRate;
	/**
	 * 是否机器人
	 */
	@NotNull(message = "是否机器人不能为空", groups = AddGroup.class)
	private Boolean robot;

}
