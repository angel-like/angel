package com.xmsy.server.zxyy.manager.modules.app.login.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * .第三方登入参数
 * 
 * @author chenjisi
 * @since 2017年8月25日
 */
@Data
public class OAuth2Params {

	@NotBlank(message = "授权码不能为空")
	private String code;// 微信授权码
	@NotBlank(message = "授权类型不能为空")
	private String type; // 1：微信 2：qq， 3:新浪
	@NotBlank(message = "设备码不能为空")
	private String deviceCode; // 设备码
	@NotBlank(message = "设备类型不能为空")
	private String deviceType; // 设备类型 ios Android pc

	/**
	 * 邀请码
	 */
	private String invitationCode;

	/**
	 * 推送id
	 */
	private String jpushRegId;
	/**
	 * 版本号
	 */
	@NotBlank(message = "版本号不能为空")
	private String edition; // 版本号
	@NotNull(message = "大厅id不能为空")
	private Long hallId; // 大厅id

}
