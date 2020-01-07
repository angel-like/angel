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
public class UserRebateCommissionDateDetailsEntity {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 提供佣金人数
	 */
	private Integer num;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 日期
	 */
	private String recordDate;

	/**
	 * 开始日期
	 */
	private String startDate;
	/**
	 * 结束日期
	 */
	private String endDate;

}
