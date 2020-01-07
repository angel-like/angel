package com.xmsy.server.zxyy.schedule.entity;

import lombok.Data;

/**
 * 用户打码及上级关系
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-10
 */
@Data
public class UserBetEntity {
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 用户下级及自己总打码(不包含今日)
	 */
	private Long betCoins;
	/**
	 * 用户昨日总打码
	 */
	private Long yesterDaybetCoins;
	/**
	 * 上级ID
	 */
	private Long parantUserId;
	/**
	 * 上级
	 */
	private String parantUserAccount;
}
