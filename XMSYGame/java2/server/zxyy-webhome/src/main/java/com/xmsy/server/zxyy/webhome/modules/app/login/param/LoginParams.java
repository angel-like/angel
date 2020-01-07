package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;

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
public class LoginParams{

	/**
	 * 账号名称
	 */
	@NotBlank(message = "账号不能为空", groups = AddGroup.class)
	private String account;

	/**
	 * 登陆密码
	 */
	@NotBlank(message = "登陆密码不能为空", groups = AddGroup.class)
	@TableField(exist = false)
	private String loginPassWord;




	/**
	 * 登陆IP
	 */
	private String loginIp;


	private String touristId;

	private  BaseLoginParam commonParam;

}
