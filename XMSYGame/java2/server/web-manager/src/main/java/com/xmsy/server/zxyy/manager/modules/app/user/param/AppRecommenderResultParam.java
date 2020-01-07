package com.xmsy.server.zxyy.manager.modules.app.user.param;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AppRecommenderResultParam {

	private String userAccount;// 用户账号
	private Long userId;// 用户ID
	private String recommendationCode;// 推荐码
	private int num;// 人数
	private int subordinateNum;// 直属人数
	private String QrCode;// 二维码路径
	private BigDecimal commission;// 当前佣金
	private BigDecimal historyCommission;// 历史总佣金
	private BigDecimal lastWeekCommission;// 上周奖励总佣金
	private BigDecimal yesterdayCommission;// 昨日奖励总佣金

}
