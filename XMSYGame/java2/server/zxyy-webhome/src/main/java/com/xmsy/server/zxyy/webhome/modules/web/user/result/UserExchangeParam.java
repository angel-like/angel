package com.xmsy.server.zxyy.webhome.modules.web.user.result;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * .用户额度转换参数
 * 
 * @author Administrator
 *
 */
@Data
public class UserExchangeParam {

	// 是否余额转金币
	private boolean balanceToCoins;

	// 转换数量
	@Min(value = 1, message = "最小转换金币或金额为1")
	@Max(value = 100000000, message = "单次最大转换金币或金额为1000万")
	@NotNull(message = "转换数量不能为空")
	private Long num;

	// 取款密码
	@NotEmpty(message = "银行密码不能为空")
	private String bankPassword;

}
