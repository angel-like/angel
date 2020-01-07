package com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.entity.HierarchyPaychannelconfigRelationshipEntity;


/**
 * 层级支付限额关系表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-03 10:59:47
 */
public interface HierarchyPaychannelconfigRelationshipService extends IService<HierarchyPaychannelconfigRelationshipEntity> {
	
	/**
	 * 根据paychannelconfigId删除层级支付关系表
	 * @param paychannelconfigId
	 */
	void deleteByPaychannelconfigId(Long paychannelconfigId);
	
	/**
	 * 根据paychannelconfigId查询
	 * @param paychannelconfigId
	 * @return
	 */
	List<Long> selectByPaychannelconfigId(Long paychannelconfigId);

}

