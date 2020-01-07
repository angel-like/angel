package com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity;

import java.math.BigDecimal;

import lombok.Data;


/**
 * 取款报表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-04-25 10:17:38
 */
@Data

public class TakeMoneyTableReport  {
	
	
	/**
	 * 取款类型
	 */
	private Integer type;
	
	/**
	 * 取款类型名称
	 */
	private String typeName;
    /**
	 * 待出款
	 */
    private BigDecimal moneyWaitAmount;
	/**
	 * 取消出款
	 */
    private BigDecimal moneyCancelAmount;
    /**
	 * 确认出款
	 */
    private BigDecimal moneySuccessAmount;
    /**
	 * 待确认行政费
	 */
    private BigDecimal unconfirmedPoundage;
   
    /**
	 * 取消行政费
	 */
    private BigDecimal cancelPoundage;
	/**
	 * 确认行政费
	 */
    private BigDecimal confirmedPoundage;
    /**
	 * 实际出款
	 */
    private BigDecimal obtainAmount;
    /**
     * 小计
     */
    private BigDecimal totalAmount;
    /**
	 * 未确认订单数
	 */
    private Integer unconfirmedNum;
    /**
	 * 取消的订单数
	 */
    private Integer cancelNum;
    
    /**
	 * 确认的订单数
	 */
    private Integer confirmedNum;
	    
}
