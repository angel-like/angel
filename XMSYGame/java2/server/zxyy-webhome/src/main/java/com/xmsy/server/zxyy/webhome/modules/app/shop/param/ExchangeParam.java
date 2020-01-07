package com.xmsy.server.zxyy.webhome.modules.app.shop.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class ExchangeParam {
	@NotNull(message = "兑换数量不能为空")
	private Long number;
}
