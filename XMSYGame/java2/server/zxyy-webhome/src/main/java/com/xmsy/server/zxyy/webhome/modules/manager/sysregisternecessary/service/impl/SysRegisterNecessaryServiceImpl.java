package com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.cache.EhCacheName;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.dao.SysRegisterNecessaryDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.entity.SysRegisterNecessaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.service.SysRegisterNecessaryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
@Service("registerNecessaryService")
public class SysRegisterNecessaryServiceImpl extends ServiceImpl<SysRegisterNecessaryDao, SysRegisterNecessaryEntity>
		implements SysRegisterNecessaryService {

	@Override
	@CacheEvict(value = EhCacheName.REGISTER_NECESSARY_CACHE, allEntries = true)
	public boolean insert(SysRegisterNecessaryEntity entity) {
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.REGISTER_NECESSARY_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.REGISTER_NECESSARY_CACHE, allEntries = true)
	public boolean updateById(SysRegisterNecessaryEntity entity) {
		return super.updateById(entity);
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.REGISTER_NECESSARY_CACHE, key = "'RegisterNecessary'", unless = "#result == null || #result.size() == 0")
	public List<SysRegisterNecessaryEntity> getRegisterNecessary() {
		return selectList(new EntityWrapper<SysRegisterNecessaryEntity>(null).orderBy(true, "order_number"));
	}

}
