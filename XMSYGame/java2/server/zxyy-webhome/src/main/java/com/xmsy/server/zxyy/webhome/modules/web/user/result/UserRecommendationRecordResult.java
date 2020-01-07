package com.xmsy.server.zxyy.webhome.modules.web.user.result;

import java.math.BigDecimal;

import lombok.Data;

/**
 * .用户推荐详情
 * 
 * @author Administrator
 *
 */
@Data
public class UserRecommendationRecordResult {

	// 用户(推荐用户)
	private String userName;
	// 用户(推荐用户)
	private String userId;
	// 邀请时间
	private String createTime;
	// 推广盈利
	private BigDecimal promotingProfit;
	// 盈利金币
	private Long coin;

}
