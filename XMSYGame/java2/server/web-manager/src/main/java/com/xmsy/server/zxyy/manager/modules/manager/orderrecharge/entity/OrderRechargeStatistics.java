package com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 充值订单表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-26 10:10:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderRechargeStatistics  {
	/**
	 * 充值总金额
	 */
	private Long amount;
	/**
	 * 支付人id
	 */
	private Long userId;
	/**
	 * 支付人账号
	 */
	private String userAccount;
	/**
	 * 优惠金额
	 */
	private BigDecimal discountAmount;
	/**
	 * 充值数量
	 */
	private Long rechargeNum;
	
	
	
	
	
}
