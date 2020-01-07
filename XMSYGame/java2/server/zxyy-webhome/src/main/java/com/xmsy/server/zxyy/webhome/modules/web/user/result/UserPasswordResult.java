package com.xmsy.server.zxyy.webhome.modules.web.user.result;

import java.math.BigDecimal;

import lombok.Data;

/**
 * .用户密码信息
 * 
 * @author Administrator
 *
 */
@Data
public class UserPasswordResult {

	// 用户id
	private Long id;
	// 用户余额
	private BigDecimal money;
	// 用户账户金币
	private Long coin;
	// 用户银行密码
	private String bankPassword;

}
