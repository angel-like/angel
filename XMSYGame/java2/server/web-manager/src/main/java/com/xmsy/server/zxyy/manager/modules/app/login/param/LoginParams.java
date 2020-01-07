package com.xmsy.server.zxyy.manager.modules.app.login.param;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.xmsy.server.zxyy.manager.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 登陆
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-02-14 14:18:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginParams {

	/**
	 * 账号名称
	 */
	@NotBlank(message = "账号不能为空", groups = AddGroup.class)
	private String account;
	/**
	 * 设备码
	 */
	private String deviceCode;

	/**
	 * 登陆密码
	 */
	@NotBlank(message = "登陆密码不能为空", groups = AddGroup.class)
	@TableField(exist = false)
	private String loginPassWord;

	/**
	 * 设备类型
	 */
	// @NotBlank(message = "设备类型不能为空", groups = AddGroup.class)
	private String deviceType;

	/**
	 * 大厅id
	 */
	@NotBlank(message = "大厅id不能为空", groups = AddGroup.class)
	private Long hallId;

	/**
	 * 版本号
	 */
	private String edition;

	/**
	 * 登陆IP
	 */
	private String loginIp;
	/**
	 * 推送id
	 */
	private String jpushRegId;

}
