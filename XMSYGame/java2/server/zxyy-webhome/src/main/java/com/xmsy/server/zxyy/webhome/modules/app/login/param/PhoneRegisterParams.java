package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 注册
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PhoneRegisterParams{

	/**
	 * 用户账号
	 */
	@NotBlank(message = "手机号不能为空")
	private String account;

	/**
	 * 登陆密码
	 */
	@NotBlank(message = "登陆密码不能为空")
	private String loginPassWord;

	/**
	 * 确认密码
	 */
	@NotBlank(message = "确认密码不能为空")
	private String verificationWord;


	/**
	 * 校验码
	 */
	@NotBlank(message = "校验码")
	private String code;

	/**
	 * ip
	 */
	private String registerIp;
	/**
	 * ip地址
	 */
	private String registerAddress;

	private  BaseLoginParam commonParam;

}
