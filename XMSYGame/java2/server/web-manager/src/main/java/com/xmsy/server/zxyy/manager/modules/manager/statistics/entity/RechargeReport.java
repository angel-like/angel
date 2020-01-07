package com.xmsy.server.zxyy.manager.modules.manager.statistics.entity;

import java.math.BigDecimal;

import lombok.Data;


/**
 * 充值报表
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-02-09 10:17:38
 */
@Data

public class RechargeReport  {

    /**
	 * 充值总额
	 */
    private BigDecimal rechargeAmount;
	/**
	 * 人工充值总额
	 */
    private BigDecimal adminAmount;
    /**
	 * 第三方充值总额
	 */
    private BigDecimal thirdAmount;
    /**
	 * 银行卡充值总额
	 */
    private BigDecimal bankAmount;

    /**
	 * 待确认总额
	 */
    private BigDecimal unconfirmedAmount;
	/**
	 * 人工待确认总额
	 */
    private BigDecimal adminAnconfirmedAmount;
    /**
	 * 第三方待确认总额
	 */
    private BigDecimal thirdAnconfirmedAmount;
    /**
	 * 银行卡待确认总额
	 */
    private BigDecimal bankAnconfirmedAmount;
	/**
	 * 优惠待确认总额discount_amount
	 */
	private BigDecimal discountAnconfirmedAmount;

    /**
	 * 取消总额
	 */
    private BigDecimal cancelAmount;
	/**
	 * 人工待取消总额
	 */
    private BigDecimal adminCancelAmount;
    /**
	 * 第三方取消总额
	 */
    private BigDecimal thirdCancelAmount;
    /**
	 * 银行卡取消总额
	 */
    private BigDecimal bankCancelAmount;
	/**
	 * 优惠取消总额
	 */
	private BigDecimal discountCancelAmount;
    /**
	 * 确认总额
	 */
    private BigDecimal confirmAmount;
	/**
	 * 人工确认总额
	 */
    private BigDecimal adminConfirmAmount;
    /**
	 * 第三方确认总额
	 */
    private BigDecimal thirdConfirmAmount;
    /**
	 * 银行卡确认总额
	 */
    private BigDecimal bankConfirmAmount;
	/**
	 * 优惠确认总额
	 */
	private BigDecimal discountConfirmAmount;

	/**
	 * 优惠总额
	 */
	private BigDecimal discountAmount;
	/**
	 * 人工优惠总额
	 */
	private BigDecimal adminDiscountAmount;
	   /**
	 * 第三方优惠总额
	 */
	private BigDecimal thirdDiscountAmount;
	   /**
	 * 银行卡优惠总额
	 */
	private BigDecimal bankDiscountAmount;

}
