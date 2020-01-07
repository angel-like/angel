package com.xmsy.server.zxyy.webhome.modules.webim.logon.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 注册
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
public class WebimPhoneRegisterEntity {

	/**
	 * 用户账号
	 */
	@NotBlank(message = "用户账号不能为空")
	@Pattern(regexp = "[a-zA-Z](?![a-zA-Z]+$)[0-9A-Za-z]{5,9}", message = "账号规则: 首位字母，6-10位数字和字母组合")
	private String account;

	/**
	 * 登陆密码
	 */
	@NotBlank(message = "登陆密码不能为空")
	@Pattern(regexp = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}", message = "密码规则: 6-10位数字和字母组合")
	private String loginPassWord;


	/**
	 * 确认密码
	 */
	@NotBlank(message = "确认密码不能为空")
	private String verificationWord;

	/**
	 * 手机号
	 */
//	@NotBlank(message = "手机号不能为空")
//	@Pattern(regexp = "(13[0-9]|14[0-9]|15[0-9]|166|17[0-9]|18[0-9]|19[8|9])\\d{8}", message = "手机号格式不对")
	private String phone;

	/**
	 * 手机验证码
	 */
//	@NotBlank(message = "验证码不能为空")
	private String code;
	/**
	 * 邀请码
	 */
	private String invitationCode;
	/**
	 * 设备码
	 */
	private String registerDeviceCode;
	/**
	 * ip
	 */
	private String registerIp;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 大厅ID
	 */
	@NotNull(message = "大厅id不能为空")
	private Long hallId;
	/**
	 * 版本号
	 */
	private String edition;

}
