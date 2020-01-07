package com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity;

import java.math.BigDecimal;

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
public class OrderTakeMoneyStatisticsTwo  {
	
	/**
	 * 取款总金额
	 */
	private BigDecimal takeAmountNum;
	/**
	 * 取款数量
	 */
	private Long takeNum;
	
	
	
	
}
