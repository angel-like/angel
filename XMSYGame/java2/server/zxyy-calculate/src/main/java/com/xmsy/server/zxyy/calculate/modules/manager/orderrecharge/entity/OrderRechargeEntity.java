package com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.calculate.base.BaseEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.param.UserOperater;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 充值订单表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-29 15:55:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_recharge")
public class OrderRechargeEntity extends BaseEntity<OrderRechargeEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 人工充值主订单号
	 */
	private String adminOrderNo;
	/**
	 * 操作人用户名
	 */
	private String sysUserAccount;
	/**
	 * 操作人id
	 */
	private Long sysUserId;
	/**
	 * 金额
	 */
	private Long amount;
	/**
	 * 层级id集合
	 */
	private Long hierarchyId;
	/**
	 * 存款人(表单上用户填的存款人)
	 */
	private String depositName;
	/**
	 * 存款时间
	 */
	private Date depositDate;
	/**
	 * 存款时间
	 */
	private Integer depositType;
	/**
	 * 存款银行
	 */
	private String depositBank;
	/**
	 * 存款银行账号/atm机现金存款的话这边就是流水号
	 */
	private String depositBankAccount;
	/**
	 * 收款银行
	 */
	private String incomeBank;
	/**
	 * 收款银行账号
	 */
	private String incomeBankAccount;
	/**
	 * 收款人
	 */
	private String payee;
	/**
	 * 开户行网点
	 */
	private String openBank;
	/**
	 * 订单状态
	 */
	private Integer status;
	/**
	 * 支付人id
	 */
	private Long userId;
	/**
	 * 支付人账号
	 */
	private String userAccount;
	/**
	 * 充值时间(系统给用户账号加钱的时间)
	 */
	private Date rechargeTime;
	/**
	 * 是否是首充（0：不是，1：是）
	 */
	private Boolean fristrecharge;
	/**
	 * 第三方支付平台订单号
	 */
	private String merchantOrderNo;
	/**
	 * 第三方支付平台
	 */
	private String rechargePlatform;
	/**
	 * 1:后台人工充值,2:第三方支付,3:线下银行卡打款
	 */
	private Integer rechargeType;
	/**
	 * 充值终端WEB,PC
	 */
	private String rechargeTerminal;
	/**
	 * 充值前主账户金额
	 */
	private BigDecimal preMoney;
	/**
	 * 充值后主账户金额
	 */
	private BigDecimal finalMoney;
	/**
	 * 优惠id
	 */
	private Long discountId;
	/**
	 * 充值渠道
	 */
	private Long rechargeChannel;
	/**
	 * 优惠金额
	 */
	private BigDecimal discountAmount;
	/**
	 * 未撤回的金额
	 */
	private BigDecimal unwithdraw;
	/**
	 * 充值请求ip地址
	 */
	private String ip;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 本次充值需要的打码
	 */
	private BigDecimal userNeedBet;
	/**
	 * 操作内容
	 */
	@TableField(exist = false)
	private UserOperater userOperater;
	
	
	
	
	//=====================================条件查询======================================
	
	/**
	 * 搜索时间
	 */
	@TableField(exist = false)
	private String queryTime;
	/**
	 * 充值最小金额
	 */
	@TableField(exist = false)
	private Integer amountMin;
	/**
	 * 充值最大金额
	 */
	@TableField(exist = false)
	private Integer amountMax;
	/**
	 * 优惠最小金额
	 */
	@TableField(exist = false)
	private BigDecimal discountAmountMin;
	/**
	 * 优惠最大金额
	 */
	@TableField(exist = false)
	private BigDecimal discountAmountMax;
	/**
	 * 收款银行卡号
	 */
	@TableField(exist = false)
	private String bankAccount;
	/**
	 * 排序字段
	 */
	@TableField(exist = false) 
	private String term;
	/**
	 * 升/降序
	 */
	@TableField(exist = false)
	private Boolean enable;
	
}
