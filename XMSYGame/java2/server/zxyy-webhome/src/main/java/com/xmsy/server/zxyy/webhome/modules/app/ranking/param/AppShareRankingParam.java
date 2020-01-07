package com.xmsy.server.zxyy.webhome.modules.app.ranking.param;

import lombok.Data;

/**
 * 分享排行
 * 
 * @author Administrator
 *
 */
@Data
public class AppShareRankingParam {

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
	private Long num;

}
