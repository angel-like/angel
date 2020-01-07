package com.xmsy.server.zxyy.webhome.modules.web.login.entity;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 登陆
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginEntity {

	/**
	 * 账号名称
	 */
	@NotBlank(message = "账号不能为空", groups = AddGroup.class)
	private String account;
	/**
	 * 注册设备码
	 */
	private String registerDeviceCode;

	/**
	 * 设备码
	 */
	private String deviceCode;

	/**
	 * 大厅id
	 */
	private Long hallId;

	/**
	 * 版本号
	 */
	private String edition;

	/**
	 * 登陆密码
	 */
	@NotBlank(message = "登陆密码不能为空", groups = AddGroup.class)
	@TableField(exist = false)
	private String loginPassWord;

	/**
	 * 设备类型
	 */
	@NotBlank(message = "设备类型不能为空", groups = AddGroup.class)
	private String deviceType;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 验证码uuid
	 */
	private String codeUuid;

}
