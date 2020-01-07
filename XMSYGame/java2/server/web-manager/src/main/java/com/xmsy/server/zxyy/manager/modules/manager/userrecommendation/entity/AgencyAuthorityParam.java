package com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity;

import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserOperater;

import lombok.Data;

@Data
public class AgencyAuthorityParam {
	/**
	 * 邀请码id
	 */
	private Long id;
	/**
	 * 会员id
	 */
	private Long userId;
	/**
	 * 代理状态
	 */
	private Boolean agentEnable;
	/**
	 * 操作内容
	 */
	private UserOperater userOperater;
}
