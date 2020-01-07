package com.xmsy.server.zxyy.manager.modules.manager.userbalance.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

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
     * 用户账号
     */
    private String userAccount;
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
     * 计算时间
     */
    private Date countDay;
    /**
     * 需要计算的钱
     */
    private Long countMoney;
	/**
	 *理财产品ID
	 */
	private Long userBalanceProductId;
	/**
	 * 方案名称
	 */
	@TableField(exist = false)
	private String productName;
	/**
	 * 利率
	 */
	@TableField(exist = false)
	private BigDecimal rate;
	/**
	 * 会员名称
	 */
	@TableField(exist = false)
	private String userName;
	/**
	 * 时间查询：开始时间
	 */
	@TableField(exist = false)
	private String startTime;
	/**
	 * 时间查询：结束时间
	 */
	@TableField(exist = false)
	private String endTime;
}
