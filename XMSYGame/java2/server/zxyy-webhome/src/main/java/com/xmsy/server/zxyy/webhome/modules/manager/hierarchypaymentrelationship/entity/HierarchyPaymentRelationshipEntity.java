package com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 层级支付关系表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-03 18:12:50
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("hierarchy_payment_relationship")
public class HierarchyPaymentRelationshipEntity extends BaseEntity<HierarchyPaymentRelationshipEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 支付类型id
	 */
    private Long paymentId;
			/**
	 * 层级id
	 */
    private Long hierarchyId;
	}
