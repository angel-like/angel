package com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 取款记录表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-26 10:25:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderRechargeStatisticsThree  {
	
	/**
	 * 存款金额
	 */
	private BigDecimal amount;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	
	
	
}
