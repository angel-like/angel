package com.xmsy.server.zxyy.webhome.modules.app.user.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateCoinParam {
	
	
	@NotNull(message = "用户id不能为空")
	private Long userId;//金币
	
	@NotNull(message = "金币不能为空")
	private Long coin;//金币
}
