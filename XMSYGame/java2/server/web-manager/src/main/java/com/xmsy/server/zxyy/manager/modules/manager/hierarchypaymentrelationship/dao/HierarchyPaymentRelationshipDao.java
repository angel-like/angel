package com.xmsy.server.zxyy.manager.modules.manager.hierarchypaymentrelationship.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaymentrelationship.entity.HierarchyPaymentRelationshipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.paymenttypeconfiguration.entity.PaymentTypeConfigurationEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 层级支付关系表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-03 18:12:50
 */
@Mapper
public interface HierarchyPaymentRelationshipDao extends BaseMapper<HierarchyPaymentRelationshipEntity> {
	
	/**
	 * 删除（根据层级id）
	 * @param hierarchyId
	 */
	void deleteByHierarchyId(@Param("hierarchyId") Long hierarchyId);
	
	/**
	 * 查询（根据层级id）
	 * @param hierarchyId
	 */
	List<Long> selectByHierarchyId(@Param("hierarchyId") Long hierarchyId);
	List<PaymentTypeConfigurationEntity> getPaymentTypeByHierarchyId(@Param("hierarchyId") Long hierarchyId);
}
