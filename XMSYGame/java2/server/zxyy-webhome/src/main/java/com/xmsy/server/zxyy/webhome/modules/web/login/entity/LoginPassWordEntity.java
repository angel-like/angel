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
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class LoginPassWordEntity  {
	

	/**
	 * 新的登陆密码
	 */
    @NotBlank(message = "新的登陆密码不能为空", groups = AddGroup.class)
	@TableField(exist = false)
	private String loginPassWord;
    /**
	 * 原登陆密码
	 */
    @NotBlank(message = "原登陆密码不能为空", groups = AddGroup.class)
	@TableField(exist = false)
	private String verificationWord;
   
   
   
	
	}
