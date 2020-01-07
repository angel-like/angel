package com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.service.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.dao.DomainManagementDao;
import com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.entity.DomainManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.service.DomainManagementService;

/**
 * .网址绑定
 * 
 * @author Administrator
 *
 */
@Service("domainManagementService")
public class DomainManagementServiceImpl extends ServiceImpl<DomainManagementDao, DomainManagementEntity>
		implements DomainManagementService {

	@Override
	@CacheEvict(value = EhCacheName.DOMAINMANAGEMENT_CACHE, allEntries = true)
	public boolean insert(DomainManagementEntity entity) {
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.DOMAINMANAGEMENT_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.DOMAINMANAGEMENT_CACHE, allEntries = true)
	public boolean updateById(DomainManagementEntity entity) {
		return super.updateById(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.DOMAINMANAGEMENT_CACHE, key = "#id")
	public DomainManagementEntity selectById(Serializable id) {
		return super.selectById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.DOMAINMANAGEMENT_CACHE, key = "'domainManagement'")
	public String getOfficalDomain() {
		List<DomainManagementEntity> domainManagements = super.selectList(new EntityWrapper<DomainManagementEntity>(
				new DomainManagementEntity().setType(Constant.DomainType.OFFICAL.getValue())));
		for (DomainManagementEntity domainManagement : domainManagements) {
			return domainManagement.getDomain();
		}
		return "";
	}

	@Override
	public String getRecommendationDomain() {
		List<DomainManagementEntity> domainManagements = super.selectList(new EntityWrapper<DomainManagementEntity>(
				new DomainManagementEntity().setType(Constant.DomainType.RECOMMENDATION.getValue())));
		Collections.shuffle(domainManagements);
		if (CollectionUtils.isEmpty(domainManagements)) {
			return "";
		}
		return domainManagements.get(0).getDomain();
	}
}
