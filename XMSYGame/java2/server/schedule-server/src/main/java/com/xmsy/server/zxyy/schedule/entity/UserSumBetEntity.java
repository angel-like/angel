package com.xmsy.server.zxyy.schedule.entity;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 用户总打码列表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-10
 */
@Data
public class UserSumBetEntity {

	/**
	 * 用户id(团队创建人)
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 总打码(不包含今日)
	 */
	private Long betCoins;
	/**
	 * 昨日本身总打码
	 */
	private Long yesterDaybetCoins;
	/**
	 * 今日每万返利额
	 */
	private BigDecimal rewardRate;

}
