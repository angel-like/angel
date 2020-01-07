package com.xmsy.server.zxyy.manager.modules.web.recharge.param;

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
public class QrcodePaymentParam {

	/**
	 * 验证码UUID
	 */
	@NotEmpty(message = "验证码uuid不能为空")
	private String captchaUuid;

	/**
	 * 验证码
	 */
	@NotEmpty(message = "验证码不能为空")
	private String code;

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
	 * 充值通道
	 */
	@NotNull(message = "支付通道不能为空")
	private Long payChannel;

	/**
	 * 支付公司id
	 */
	@NotNull(message = "支付公司不能为空")
	private Long payId;

}
