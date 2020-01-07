package com.xmsy.server.zxyy.webhome.modules.app.shop.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 礼包兑换码参数
 * @author Administrator
 *
 */
@Data
public class ExchangeCodeParam {
	@NotNull(message = "兑换码不能为空")
	private String exchangeCode;
	/**
	 * 密码
	 */
	private String password;

}
