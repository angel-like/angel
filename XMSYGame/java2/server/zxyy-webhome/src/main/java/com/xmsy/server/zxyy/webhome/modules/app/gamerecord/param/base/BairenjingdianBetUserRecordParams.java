package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .百人经典牛牛下注用户记录信息
 * 
 * @author aleng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BairenjingdianBetUserRecordParams {

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
	 * 赢输金币
	 */
	@NotNull(message = "金币不能为空")
	private Long prizeCoins;
	/**
	 * 是否机器人
	 */
	@NotNull(message = "是否机器人不能为空")
	private Boolean robot;

	/**
	 * 下注前金币
	 */
	private Long coinsBefore;
	/**
	 * 下注金币
	 */
	private Long betCoins;
	/**
	 * 结束后金币
	 */
	private Long coinsAfter;

	/**
	 * 下注区域
	 */

	@NotNull(message = "下注区域")
	private List<Integer> betArea;
}
