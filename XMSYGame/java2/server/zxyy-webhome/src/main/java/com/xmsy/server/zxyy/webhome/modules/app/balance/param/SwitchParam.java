package com.xmsy.server.zxyy.webhome.modules.app.balance.param;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SwitchParam {
	/**
	 * 转入金币数量
	 */
	@NotNull(message = "金币数量不能为空")
	private Long number;
}
