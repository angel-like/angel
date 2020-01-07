package com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.params;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


/**
 * 层级支付关系表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-03 18:12:50
 */
@Data
public class HierarchyPaymentRelationshipParams implements Serializable {
	private static final long serialVersionUID = 1L;
							/**
	 * 支付类型id
	 */
    private List<Long> paymentId;
			/**
	 * 层级id
	 */
    private Long hierarchyId;
	}
