package com.xmsy.server.zxyy.manager.modules.manager.orderbankrecharge.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 银行卡充值
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-08 16:22:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_bank_recharge")
public class OrderBankRechargeEntity {
	/**
	 * 存款人
	 */
	@NotEmpty(message = "存款人不能为空")
	private String name;
	/**
	 * 存款时间
	 */
	private Date depositDate;
	/**
	 * 存款金额
	 */
	@NotNull(message = "存款金额不能为空")
	@Min(value = 10, message = "最小充值金额10块钱")
	@Max(value = 10000000, message = "最大充值金额1000万")
	private Long amount;

	/**
	 * 存款类型
	 */
	@NotNull(message = "存款类型不能为空")
	private Integer depositType;
	/**
	 * 存款银行
	 */
	private String depositBank;
	/**
	 * 存款银行卡号
	 */
	private String depositBankAccount;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 充值时间
	 */
	private Date rechargeDate;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 银行信息ID
	 */
	@NotNull(message = "银行信息ID不能为空")
	private Long bankId;
	/**
	 * 账号
	 */
	private String account;

	/**
	 * 充值端:mobile,pc
	 */
	private String rechargeTerminal;
}
