package com.xmsy.server.zxyy.manager.modules.app.recharge.param;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * .扫码支付方式参数定义
 * 
 * @author Administrator
 *
 */
@Data
public class AppQrcodeRechargeParam {

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 支付金额
	 */
	@NotNull(message = "支付金额不能为空")
	@Min(value = 10, message = "整数,最小金额10元")
	@Max(value = 10000000, message = "最大充值金额1000万")
	private Long amount;

	/**
	 * 充值端:mobile,pc
	 */
	private String rechargeTerminal;

	/**
	 * 充值端:mobile,pc
	 */
	@NotNull(message = "转账时间不能为空")
	private Date rechargeTime;

	/**
	 * 存款昵称不能为空
	 */
	@NotEmpty(message = "存款昵称不能为空")
	private String nickName;

	/**
	 * 存款账号不能为空
	 */
	@NotEmpty(message = "存款账号不能为空")
	private String account;

	/**
	 * 充值了类型
	 */
	@NotNull(message = "充值类型不能为空")
	private Long cashQrcodeId;
}
