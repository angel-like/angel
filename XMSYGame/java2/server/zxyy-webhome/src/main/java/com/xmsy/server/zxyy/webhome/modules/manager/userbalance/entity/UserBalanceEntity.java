package com.xmsy.server.zxyy.webhome.modules.manager.userbalance.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户余额宝金额表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_balance")
public class UserBalanceEntity extends BaseEntity<UserBalanceEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户ID
	 */
    private Long userId;
			/**
	 * 金额
	 */
    private Long money;
			/**
	 * 未计算收益的金额
	 */
    private Long unprofitMoney;
			/**
	 * 总收益
	 */
    private Long profit;
			/**
	 * 昨日收益
	 */
    private Long profitYesterday;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 计算时间
     */
    private Date countDay;
    /**
     * 需要计算的钱
     */
    private Long countMoney;
    /**
     * 理财产品id
     */
    private Long userBalanceProductId;
	}
