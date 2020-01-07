package com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.entity.HierarchyPaymentRelationshipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.paymenttypeconfiguration.entity.PaymentTypeConfigurationEntity;


/**
 * 层级支付关系表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-03 18:12:50
 */
public interface HierarchyPaymentRelationshipService extends IService<HierarchyPaymentRelationshipEntity> {
	
	/**
	 * 删除（根据层级id）
	 * @param hierarchyId
	 */
	void deleteByHierarchyId(Long hierarchyId);
	
	/**
	 * 查询（根据层级id）
	 * @param hierarchyId
	 */
	List<Long> selectByHierarchyId(Long hierarchyId);
	
	/**
	 * 获取可选的支付列表
	 * 根据层级id
	 * @param hierarchyId
	 * @return
	 */
	List<PaymentTypeConfigurationEntity> getRechargeListByHierarchyId(Long hierarchyId);

}

