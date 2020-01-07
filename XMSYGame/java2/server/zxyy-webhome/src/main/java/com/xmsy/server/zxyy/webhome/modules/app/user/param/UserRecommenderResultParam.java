package com.xmsy.server.zxyy.webhome.modules.app.user.param;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserRecommenderResultParam {

	private String userAccount;// 用户账号
	private Long userId;// 用户ID
	private Long agentHierarchyId;// 代理等级ID
	private String recommendationCode;//推荐码
	private String agentHierarchyName;// 代理等级
	private int num;// 人数
	private String QrCode;// 二维码路径
	private BigDecimal commission;//当前佣金

}
