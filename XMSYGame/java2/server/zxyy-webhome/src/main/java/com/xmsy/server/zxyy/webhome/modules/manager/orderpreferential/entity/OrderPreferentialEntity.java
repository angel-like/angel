package com.xmsy.server.zxyy.webhome.modules.manager.orderpreferential.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 充值优惠
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-25 18:46:23
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("order_preferential")
public class OrderPreferentialEntity extends BaseEntity<OrderPreferentialEntity> {
	private static final long serialVersionUID = 1L;
			/**
	 * 是否首冲
	 */
    private Boolean firstRecharge;
			/**
	 * 充值金额
	 */
    private Long rechargeAmount;
			/**
	 * 返利比例
	 */
    private BigDecimal giftProportion;
    /**
	 * 层级ID
	 */
    private Long hierarchyId;
    /**
	 * 层级名称
	 */

    @TableField(exist = false)
    private String hierarchyName;
    
	}
