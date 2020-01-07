package com.xmsy.server.zxyy.webhome.modules.app.balance.param;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SwitchParamNew {
	/**
	 * 转入金币数量
	 */
	@NotNull(message = "金币数量不能为空")
	private Long number;
	/**
     * 理财产品id
     */
	@NotNull(message = "理财产品id不能为空")
    private Long userBalanceProductId;
}
