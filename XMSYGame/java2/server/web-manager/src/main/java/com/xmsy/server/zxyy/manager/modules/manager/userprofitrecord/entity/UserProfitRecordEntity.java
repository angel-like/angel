package com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户账户金额收益记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 17:48:37
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_profit_record")
public class UserProfitRecordEntity extends BaseEntity<UserProfitRecordEntity> {
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
    private BigDecimal money;
			/**
	 * 利率
	 */
    private BigDecimal rate;
			/**
	 * 收益
	 */
    private BigDecimal profit;
			/**
	 * 收益日期
	 */
    private Date incomeDate;
    @TableField(exist = false)
    private String productName;
    @TableField(exist = false)
    private  Integer settlementType;
	@TableField(exist = false)
	private String startTime;
	@TableField(exist = false)
	private  String endTime;
	@TableField(exist = false)
	private  Integer userBalanceProductId;
	}
