package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .第三方登入参数
 * 
 * @author chenjisi
 * @since 2017年8月25日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OAuth2Params {

	@NotBlank(message = "授权码不能为空")
	private String code;// 微信授权码
	@NotBlank(message = "授权类型不能为空")
	private String type; // 1：微信 2：qq， 3:新浪

	private  BaseLoginParam commonParam;
}
