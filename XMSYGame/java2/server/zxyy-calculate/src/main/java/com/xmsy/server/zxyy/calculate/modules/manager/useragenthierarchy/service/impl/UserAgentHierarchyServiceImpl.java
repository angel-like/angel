package com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.dao.UserAgentHierarchyDao;
import com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.service.UserAgentHierarchyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userAgentHierarchyService")
public class UserAgentHierarchyServiceImpl extends ServiceImpl<UserAgentHierarchyDao, UserAgentHierarchyEntity>
		implements UserAgentHierarchyService {

	@Override
	public UserAgentHierarchyEntity getDefaultHierarchy() {
		UserAgentHierarchyEntity entity = new UserAgentHierarchyEntity();
		entity.setType(true);// 是默认层级
		List<UserAgentHierarchyEntity> list = baseMapper
				.selectList(new EntityWrapper<UserAgentHierarchyEntity>(entity));
		if (CollectionUtils.isEmpty(list)) {
			log.info("[selectIdForType] 默认层级为空 需要设置默认层级{}", list);
			return null;
		}
		return list.get(0);
	}
}
