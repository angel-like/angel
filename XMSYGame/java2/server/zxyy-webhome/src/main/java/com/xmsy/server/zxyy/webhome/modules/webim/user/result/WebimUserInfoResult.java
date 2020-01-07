package com.xmsy.server.zxyy.webhome.modules.webim.user.result;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 用户信息
 * 
 * @author xiaoliu
 *
 */
@Data
public class WebimUserInfoResult {

	// 用户id
	private String account;
	// 头像
	private String portrait;
	// 用户余额
	private BigDecimal money;
	// 用户账户金币
	private Long coin;
	// 服务器名称
	private String serviceName;
	// 服务器名称
	private String serviceUrl;
	// vip等级
	private String vip;

}
