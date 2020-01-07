package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 游客登录登陆
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-02-14 14:18:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TLoginParams{

	/**
	 * 账号名称
	 */
	@NotBlank(message = "游客码不能为空", groups = AddGroup.class)
	private String touristId;
	


	/**
	 * 登陆IP
	 */
	private String loginIp;

	private  BaseLoginParam commonParam;

}
