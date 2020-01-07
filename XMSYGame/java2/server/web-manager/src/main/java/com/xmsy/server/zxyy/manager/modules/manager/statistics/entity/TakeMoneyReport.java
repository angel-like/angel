package com.xmsy.server.zxyy.manager.modules.manager.statistics.entity;

import java.math.BigDecimal;

import lombok.Data;


/**
 * 取款报表报表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-02-12 18:54:38
 */
@Data

public class TakeMoneyReport  {
	
    /**
	 * 取款总额
	 */
    private BigDecimal takeAmount;
    
    
	/**
	 * 余额取款总额
	 */
    private BigDecimal moneyAmount;
    /**
	 * 余额取款待确认总额
	 */
    private BigDecimal moneyWaitAmount;
    /**
	 * 余额取款取消总额
	 */
    private BigDecimal moneyCancelAmount;
   
    /**
	 * 余额取款出款总额
	 */
    private BigDecimal moneySuccessAmount;
    
    
	/**
	 * 佣金取款总额
	 */
    private BigDecimal commissionAmount;
    /**
	 * 佣金取款待确认总额
	 */
    private BigDecimal commissionWaitAmount;
    /**
	 * 佣金取款取消总额
	 */
    private BigDecimal commissionCancelAmount;
    /**
	 * 佣金取款出款总额
	 */
    private BigDecimal commissionSuccessAmount;
    
    
    
	/**
	 * 手续费总额
	 */
    private BigDecimal poundage;
    /**
	 * 待确认手续费总额
	 */
    private BigDecimal moneyWaitPoundage;
    /**
	 * 取消手续费总额
	 */
    private BigDecimal moneyCancelPoundage;
    /**
	 * 已出款手续费总额
	 */
    private BigDecimal moneySuccessPoundage;
    
    
    
	/**
	 * 余额出款实际出款总额
	 */
    private BigDecimal moneyObtainAmount;
    /**
	 * 余额出款待确认出款总额
	 */
    private BigDecimal moneyWaitObtainAmount;
    /**
	 * 余额出款取消出款总额
	 */
    private BigDecimal moneyCancelObtainAmount;
    
	/**
	 * 余额出款已出款总额
	 */
	private BigDecimal moneySuccessObtainAmount;
	
	    
}
