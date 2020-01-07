package com.xmsy.server.zxyy.manager.modules.manager.userpassword.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户密码表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_password")
public class UserPasswordEntity extends BaseEntity<UserPasswordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户ID
	 */
    private Long userId;
			/**
	 * 登陆密码
	 */
    private String loginPassword;
			/**
	 * 取款密码
	 */
    private String bankPassword;
	}
