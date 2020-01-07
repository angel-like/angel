package com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 用户佣金返利记录
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-15
 */
@Data
public class UserRebateCommissionDetailsEntity {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 账户余额
	 */
	private BigDecimal money;
	/**
	 * 佣金余额
	 */
	private BigDecimal commission;
	/**
	 * 直属下线人数
	 */
	private Integer subordinateNum;
	/**
	 * 下线总人数
	 */
	private Integer num;
	/**
	 * 邀请码
	 */
	private String recommendationCode;
	/**
	 * 有效打码
	 */
	private Long betCoins;
	/**
	 * 代理总佣金
	 */
	private BigDecimal obtainCommission;
	/**
	 * 佣金取款金额
	 */
	private Long takeAmount;

}
