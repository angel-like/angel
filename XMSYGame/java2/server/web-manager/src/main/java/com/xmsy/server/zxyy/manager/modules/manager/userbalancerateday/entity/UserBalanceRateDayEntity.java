package com.xmsy.server.zxyy.manager.modules.manager.userbalancerateday.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户余额宝每日利率表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-24 16:35:30
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_balance_rate_day")
public class UserBalanceRateDayEntity extends BaseEntity<UserBalanceRateDayEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 利率
	 */
    private BigDecimal rate;
			/**
	 * 统计日期
	 */
    private Date countDate;
	}
