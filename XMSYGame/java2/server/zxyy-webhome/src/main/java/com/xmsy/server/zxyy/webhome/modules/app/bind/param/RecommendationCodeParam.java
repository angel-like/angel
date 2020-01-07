package com.xmsy.server.zxyy.webhome.modules.app.bind.param;

import javax.validation.constraints.NotNull;


import lombok.Data;
@Data
public class RecommendationCodeParam {
	/**
	 * 推荐码
	 */
	@NotNull(message = "推荐码不能为空")
	String recommendationCode;
}
