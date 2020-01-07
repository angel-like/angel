package com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.entity;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 用户佣金返利记录
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-18
 */
@Data
public class UserRebateCommissionSumEntity {
	/**
	 * 指定日期佣金总额
	 */
	private BigDecimal dateCommission;
	/**
	 * 区间佣金总额
	 */
	private BigDecimal intervalCommission;
	/**
	 * 佣金总额
	 */
	private BigDecimal totalCommission;

}
