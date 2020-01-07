package com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 人工充值
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-17 16:48:50
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("order_administrator_recharge")
public class OrderAdministratorRechargeEntity extends BaseEntity<OrderAdministratorRechargeEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 订单号
	 */
    private String orderNo;
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
	 * 金额
	 */
    private Long coin;
			/**
	 *
	 */
    private String remake;
			/**
	 * 支付人账号
	 */
    private String account;
			/**
	 * 层级id
	 */
    private String hierarchyId;
    /**
	 * 层级名
	 */
    @TableField(exist=false)
    private String hierarchyName;

	/**
	 * 操作类型 0：存款   1：取款
	 */
	private Integer operationType;
	/**
	 * 优惠金额
	 */
	private BigDecimal discountAmount;
	/**
	 * VIP优惠金额
	 */
	private BigDecimal vipDiscount;

	/**
	 * 优惠打码倍数
	 */
	private BigDecimal discountMultiple;
	/**
	 * 充值打码倍数
	 */
	private BigDecimal rechargeMultiple;
	/**
	 * 是否指定
	 */
	private Boolean designated;
	/**
	 * 充值类型
	 */
	private Integer rechargeType;
	@TableField(exist = false)
	private String rechargeTypeName;
	/**
	 * 订单状态
	 */
	@TableField(exist = false)
	private Integer orderStatus;

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
