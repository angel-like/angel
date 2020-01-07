package com.xmsy.server.zxyy.manager.modules.app.login.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.manager.common.validator.group.AddGroup;

import lombok.Data;

/**
 * 注册
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
public class PhoneRegisterParams {

	/**
	 * 账号名称
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
	 * 邀请码
	 */
	private String invitationCode;

	/**
	 * 校验码
	 */
	@NotBlank(message = "校验码")
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
	/**
	 * 设备类型
	 */
	private String deviceType;

	/**
	 * 版本号
	 */
	private String edition;
	/**
	 * 推送id
	 */
	private String jpushRegId;

	/**
	 * 大厅ip
	 */
	@NotNull(message = "大厅id不能为空", groups = AddGroup.class)
	private Long hallId;

}
