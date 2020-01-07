package com.xmsy.server.zxyy.manager.modules.manager.paymenttypeconfiguration.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 支付类型配置表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-03 14:38:14
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("payment_type_configuration")
public class PaymentTypeConfigurationEntity extends BaseEntity<PaymentTypeConfigurationEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 支付类型名称
	 */
    private String name;
    
	/**
	 * 是否启用
	 */
    private Boolean enable;
    
    /**
	 * 排序
	 */
	private Integer orderNum;
	
	 /**
	 * 充值类型
	 */
	private Integer type;
	
	 /**
	 * 支付类型
	 */
	private Integer paymentType;
	
	 /**
	 * 支付推荐
	 */
	private Integer paymentRecommend;
	/**
	 * 层级id
	 */
	private String hierarchyId;

	/**
	 * 类型名称
	 */
    @TableField(exist=false)
    private String paymentTypeName;
    
    /**
	 * 支付类型名称
	 */
    @TableField(exist=false)
    private String typeName;
    
    /**
	 * 类型名称
	 */
    @TableField(exist=false)
    private Object child;
	/**
	 * 层级名称
	 */
	@TableField(exist=false)
	private String hierarchyName;
}
