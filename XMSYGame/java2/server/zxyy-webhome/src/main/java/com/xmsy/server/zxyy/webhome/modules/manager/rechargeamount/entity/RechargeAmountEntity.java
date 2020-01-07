package com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 充值内容（金额）
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("recharge_amount")
public class RechargeAmountEntity extends BaseEntity<RechargeAmountEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 金额
	 */
    private Long amount;
			/**
	 * 渠道ID
	 */
    private Long payId;
    /**
	 * 支付方式名称
	 */
	private Long paymentMethodId;
    
    /**
	 * 渠道名称
	 */
    @TableField(exist=false)
    private String payName;
    
	}
