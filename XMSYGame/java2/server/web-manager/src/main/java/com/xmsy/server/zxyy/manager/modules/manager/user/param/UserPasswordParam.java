package com.xmsy.server.zxyy.manager.modules.manager.user.param;

import lombok.Data;


/**
 * 用户信息表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
public class UserPasswordParam {
	/**
	 * userid
	 */
    private Long userId;
	/**
	 * 密码
	 */
    private String password;
	/**
	 * 确认密码
	 */
    private String confirmPassword;
    /**
	 * opt
	 */
    private String otp;
    
    /**
     * 操作内容
     */
    private UserOperater userOperater;

}