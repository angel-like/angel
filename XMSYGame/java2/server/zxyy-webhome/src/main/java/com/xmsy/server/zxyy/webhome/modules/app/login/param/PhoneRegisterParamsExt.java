package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import javax.validation.constraints.Pattern;

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
public class PhoneRegisterParamsExt{

	/**
	 * 用户账号
	 */
	@Pattern(regexp = "[a-zA-Z](?![a-zA-Z]+$)[0-9A-Za-z]{5,9}", message = "账号规则: 首位字母，6-10位数字和字母组合")
	private String account;

	/**
	 * 登陆密码
	 */
	@Pattern(regexp = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}", message = "密码规则: 首位字母，6-10位数字和字母组合")
	private String loginPassWord;


	/**
	 * 确认密码
	 */
	@Pattern(regexp = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}", message = "密码规则: 首位字母，6-10位数字和字母组合")
	private String verificationWord;


	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 校验码
	 */
	private String code;

	/**
	 * 设备码
	 */
	private String registerDeviceCode;
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
