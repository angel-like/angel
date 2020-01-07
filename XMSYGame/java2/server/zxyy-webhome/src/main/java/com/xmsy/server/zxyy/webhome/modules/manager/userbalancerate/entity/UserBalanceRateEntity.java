package com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户余额宝利率表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-17 15:56:57
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_balance_rate")
public class UserBalanceRateEntity extends BaseEntity<UserBalanceRateEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 利率
	 */
    private BigDecimal rate;
			/**
	 * 生效时间
	 */
    private Date effectDate;
    @TableField(exist=false)
    private String startTime;
    @TableField(exist=false)
    private String endTime;
	}
