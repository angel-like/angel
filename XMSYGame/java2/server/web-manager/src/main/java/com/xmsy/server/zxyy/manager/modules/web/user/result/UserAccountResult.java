package com.xmsy.server.zxyy.manager.modules.web.user.result;

import java.math.BigDecimal;

import lombok.Data;

/**
 * .用户账户信息
 * 
 * @author Administrator
 *
 */
@Data
public class UserAccountResult {

	// 用户id
	private Long id;
	// 用户余额
	private BigDecimal money;
	// 用户账户金币
	private Long coin;

}
