package com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.dao.UserAgentHierarchyDao;
import com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.service.UserAgentHierarchyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userAgentHierarchyService")
public class UserAgentHierarchyServiceImpl extends ServiceImpl<UserAgentHierarchyDao, UserAgentHierarchyEntity>
		implements UserAgentHierarchyService {

	public static final String KEY_CACHE = "defaultAgentHierarchy";

	@Override
	@CacheEvict(value = EhCacheName.USER_AGENT_HIERARCHY_CACHE, allEntries = true)
	public boolean insert(UserAgentHierarchyEntity entity) {
		// TODO Auto-generated method stub
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.USER_AGENT_HIERARCHY_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.USER_AGENT_HIERARCHY_CACHE, allEntries = true)
	public boolean updateById(UserAgentHierarchyEntity entity) {
		// TODO Auto-generated method stub
		return super.updateById(entity);
	}

	@Override
	public UserAgentHierarchyEntity selectById(Serializable id) {
		// TODO Auto-generated method stub
		return super.selectById(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = EhCacheName.USER_AGENT_HIERARCHY_CACHE, allEntries = true)
	public void setDefault(Long id) {
		log.info("[setDefault] id {}", id);
		this.baseMapper.updateType(null, false);
		this.baseMapper.updateType(id, true);
	}

	@Override
	public UserAgentHierarchyEntity getDefaultHierarchy() {
		UserAgentHierarchyEntity entity = new UserAgentHierarchyEntity();
		entity.setType(SysConstant.ENABLE_TRUE);// 是默认层级
		List<UserAgentHierarchyEntity> list = baseMapper
				.selectList(new EntityWrapper<UserAgentHierarchyEntity>(entity));
		if (CollectionUtils.isEmpty(list)) {
			log.info("[selectIdForType] 默认层级为空 需要设置默认层级{}", list);
			return null;
		} else {
			return list.get(0);
		}
	}
}
