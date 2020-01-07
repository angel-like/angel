package com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.dao.HierarchyPaychannelconfigRelationshipDao;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.entity.HierarchyPaychannelconfigRelationshipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.service.HierarchyPaychannelconfigRelationshipService;


@Service("hierarchyPaychannelconfigRelationshipService")
public class HierarchyPaychannelconfigRelationshipServiceImpl extends ServiceImpl<HierarchyPaychannelconfigRelationshipDao, HierarchyPaychannelconfigRelationshipEntity> implements HierarchyPaychannelconfigRelationshipService {

	@Override
	public void deleteByPaychannelconfigId(Long paychannelconfigId) {
		this.baseMapper.deleteByPaychannelconfigId(paychannelconfigId);
		
	}

	@Override
	public List<Long> selectByPaychannelconfigId(Long paychannelconfigId) {
		return this.baseMapper.selectByPaychannelconfigId(paychannelconfigId);
	}


}
