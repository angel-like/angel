package com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.service.impl;

import java.io.Serializable;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.cache.EhCacheName;
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.dao.SysConfigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.entity.SysConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.service.SysConfigService;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

	@Override
	@CacheEvict(value = EhCacheName.SYSCONFIG_CACHE, allEntries = true)
	public boolean insert(SysConfigEntity entity) {
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.SYSCONFIG_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.SYSCONFIG_CACHE, allEntries = true)
	public boolean updateById(SysConfigEntity entity) {
		return super.updateById(entity);
	}

	@Override
	@Cacheable(value = EhCacheName.SYSCONFIG_CACHE, key = "#id", unless = "#result == null")
	public SysConfigEntity selectById(Serializable id) {
		return super.selectById(id);
	}

	@Override
	@Cacheable(value = EhCacheName.SYSCONFIG_CACHE, key = "#parentKey+'_'+#paramKey", unless = "#result == null")
	public String selectByParamKey(String parentKey, String paramKey) {
		return baseMapper.findSysconfigValue(parentKey, paramKey);
	}

}
