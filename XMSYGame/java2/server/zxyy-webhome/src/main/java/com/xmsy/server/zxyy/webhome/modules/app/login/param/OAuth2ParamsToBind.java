package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * .第三方登入参数
 * 
 * @author chenjisi
 * @since 2017年8月25日
 */
@Data
public class OAuth2ParamsToBind {

	@NotBlank(message = "授权码不能为空")
	private String code;// 微信授权码
	@NotBlank(message = "授权类型不能为空")
	private String type; // 1：微信 2：qq， 3:新浪

}
