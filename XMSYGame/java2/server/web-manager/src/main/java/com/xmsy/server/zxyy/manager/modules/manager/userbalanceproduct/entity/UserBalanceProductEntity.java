package com.xmsy.server.zxyy.manager.modules.manager.userbalanceproduct.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 理财产品
 * 
 * @author aye
 * @email xxxxx
 * @date 2019-11-28 11:26:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_balance_product")
public class UserBalanceProductEntity extends BaseEntity<UserBalanceProductEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 方案名称
	 */
	private String productName;
	/**
	 * 结算类型（0:循环结算，1本金结算）
	 */
	private Integer settlementType;
	/**
	 * 最低金额
	 */
	private Long minMoney;
	/**
	 * 最高金额
	 */
	private Long maxMoney;
	/**
	 * 利率
	 */
	private BigDecimal rate;
	/**
	 * 利息上限
	 */
	private Long rateMoneyMax;
	/**
	 * 剩余可买金额
	 */
	private Long remaindBuyNum;
	/**
	 * 打码倍数
	 */
	private Integer betMultiple;
	/**
	 * 会员当天可购买次数
	 */
	private Integer userTodayBuyNum;
	/**
	 * 发行金额
	 */
	private Long issueNum;
	/**
	 * 结算时间
	 */
	private Date issueTime;
	/**
	 * 结算周期
	 */
	private Integer settlementCycle;
	/**
	 * 状态(0:禁用，1:启用)
	 */
	private Boolean enable;
	/**
	 * 排序
	 */
	private Integer orderNum;
}
