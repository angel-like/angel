package com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.dao.UserHierarchyDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.service.UserHierarchyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userHierarchyService")
public class UserHierarchyServiceImpl extends ServiceImpl<UserHierarchyDao, UserHierarchyEntity>
		implements UserHierarchyService {

	@Override
	public UserHierarchyEntity getDefaultHierarchy() {
		UserHierarchyEntity entity = new UserHierarchyEntity();
		entity.setType(true);// 是默认层级
		List<UserHierarchyEntity> list = baseMapper.selectList(new EntityWrapper<UserHierarchyEntity>(entity));
		if (CollectionUtils.isEmpty(list)) {
			log.info("[selectIdForType] 默认层级为空 需要设置默认层级{}", list);
			return null;
		} 
		return list.get(0);
	}

}
