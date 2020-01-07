package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 注册
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
public class BindingPhone {

	/**
	 * 账号名称
	 */
	@NotBlank(message = "手机号不能为空")
	private String phone;

	/**
	 * 校验码
	 */
	@NotBlank(message = "校验码")
	private String code;

	

}
