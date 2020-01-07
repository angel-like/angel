package com.xmsy.server.zxyy.webhome.modules.app.shop.param;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ShopParam {
	@NotNull(message = "产品id不能为空")
	private Long productId;
	@NotNull(message = "购买不能为空")
	private Long number;
}
