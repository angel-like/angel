package com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 资金交易明细
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-21 14:43:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_transaction_record")
public class UserTransactionRecordEntity extends BaseEntity<UserTransactionRecordEntity> {
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
	 * 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 */
	private Integer type;
	/**
	 * 业务ID 订单编号
	 */
	private String orderNo;
	/**
	 * 交易额
	 */
	private BigDecimal amount;
	/**
	 * 备注
	 */
	private String remake;
	/**
	 * 是否是首充（0：不是，1：是）
	 */
	private Boolean fristrecharge;
	/**
	 * 账户余额
	 */
	private BigDecimal money;
	/**
	 * 金币
	 */
	private Long coin;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 具体的类型: 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利
	 * 19推荐返利)
	 */
	private Integer detailType;
	
	/**
	 * 开始时间
	 */
	@TableField(exist=false)
	private String startTime;
	/**
	 * 结束时间
	 */
	@TableField(exist=false)
	private String endTime;

}
