package com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.param;



import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserOperater;

import lombok.Data;

@Data
public class AdminRechargeParam {

	/**
	 * 充值金额
	 */
	private Long amount;
	/**
	 * 操作金币
	 */
	private Long takeCoin;
	/**
	 * 备注
	 */
	private String remake;
	/**
	 * 充值账号
	 */
	private String account;
	/**
	 * 充值层级
	 */
	private String hierarchyId;
	/**
	 * 充值对象
	 */
	private Integer targetObject;
	/**
	 * 优惠打码倍数
	 */
	private BigDecimal discountMultiple;
	/**
	 * 充值打码倍数
	 */
	private BigDecimal rechargeMultiple;
	/**
	 * 操作类型 0：存款   1：取款
	 */
	private Integer operationType;

	/**
	 * 优惠ID
	 */
	private Long discountId;
	/**
	 * 优惠金额
	 */
	private BigDecimal discountAmount;
	/**
	 * VIP优惠金额
	 */
	private BigDecimal vipDiscount;
	/**
	 * 是否指定
	 */
	private boolean designated;
	/**
	 * 是否发送站内信
	 */
	private Boolean sendMessage;
	/**
	 * 站内信标题
	 */
	private String messageTitle;
	/**
	 * 站内信内容
	 */
	private String messageContent;
 /**
	 * 操作内容
	 */
	@TableField(exist = false)
	private UserOperater userOperater;
	/**
	 *
	 * 充值类型
	 */
	private Integer rechargeType;
//
}
