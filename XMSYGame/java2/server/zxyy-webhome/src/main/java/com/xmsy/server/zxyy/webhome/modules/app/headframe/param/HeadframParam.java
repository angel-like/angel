package com.xmsy.server.zxyy.webhome.modules.app.headframe.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class HeadframParam {
	@NotNull(message = "头像框id不能为空")
	private Long headframId;
}
