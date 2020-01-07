package com.xmsy.server.zxyy.webhome.modules.app.ranking.param;

import lombok.Data;

/**
 * 充值排行
 * 
 * @author Administrator
 *
 */
@Data
public class AppRechargeRankingParam {

	/**
	 * 头像
	 */
	private String portrait;
	
	/**
	 * 账号
	 */
	private String userAccount;

	/**
	 * 金币
	 */
	private Long rechargeAmount;

}
