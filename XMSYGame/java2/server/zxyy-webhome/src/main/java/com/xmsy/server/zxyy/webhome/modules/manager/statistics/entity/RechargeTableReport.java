package com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity;

import java.math.BigDecimal;

import lombok.Data;


/**
 * 充值报表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-04-25 10:17:38
 */
@Data

public class RechargeTableReport  {
	
	/**
	 * 充值平台
	 */
	private String rechargePlatform;
	/**
	 * 充值类型
	 */
	private Integer rechargeType;
	/**
	 * 充值平台名称
	 */
	private String rechargePlatformName;
	/**
	 * 充值类型名称
	 */
	private String rechargeTypeName;
    /**
	 * 未确认充值总额
	 */
    private BigDecimal unconfirmedAmount;
	/**
	 * 取消充值总额
	 */
    private BigDecimal cancelAmount;
    /**
	 * 确认充值总额
	 */
    private BigDecimal confirmedAmount;
    /**
	 * 待确认优惠总额
	 */
    private BigDecimal unconfirmedDiscountAmount;
   
    /**
	 * 取消优惠总额
	 */
    private BigDecimal cancelDiscountAmount;
	/**
	 * 确认优惠总额
	 */
    private BigDecimal confirmedDiscountAmount;
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
