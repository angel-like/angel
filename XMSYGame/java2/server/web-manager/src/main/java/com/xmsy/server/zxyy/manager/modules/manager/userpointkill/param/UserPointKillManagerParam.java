package com.xmsy.server.zxyy.manager.modules.manager.userpointkill.param;



import lombok.Data;

@Data
public class UserPointKillManagerParam {
	/**
	 * 账号名称
	 */
	private String userAccount;
	/**
	 * 点杀(正常)
	 */
	private Boolean pointKillEnable;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
}
