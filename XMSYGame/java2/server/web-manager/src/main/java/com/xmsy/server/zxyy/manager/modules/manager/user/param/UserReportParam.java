package com.xmsy.server.zxyy.manager.modules.manager.user.param;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


/**
 * 用户信息表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
public class UserReportParam {
	public UserReportParam() {
		super();
	}
	public UserReportParam(Date cdate) {
		this.createTime = cdate;
		this.takeMoneySum = BigDecimal.ZERO;
		this.orderRechargeSum = BigDecimal.ZERO;
		this.commissionSum = BigDecimal.ZERO;
		this.commissionOutSum = BigDecimal.ZERO;
		this.commissionEnterSum = BigDecimal.ZERO;
		this.betCoins = 0l;
		this.prizeCoins = 0l;
	}
	/**
	 * 取款金额
	 */
    private BigDecimal takeMoneySum;
	/**
	 * 存款金额
	 */
    private BigDecimal orderRechargeSum;
	/**
	 * 佣金
	 */
    private BigDecimal commissionSum;
    /**
	 * 佣金转出
	 */
    private BigDecimal commissionOutSum;
    
    /**
     * 佣金转入
     */
    private BigDecimal commissionEnterSum;
    /**
     * 时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
   	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    
    /**
	 * 下注金额
	 */
    private Long betCoins;
	/**
	 * 输赢金额
	 */
    private Long prizeCoins;

}