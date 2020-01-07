package com.xmsy.server.zxyy.manager.modules.manager.sysregisternecessary.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.modules.manager.sysregisternecessary.dao.SysRegisterNecessaryDao;
import com.xmsy.server.zxyy.manager.modules.manager.sysregisternecessary.entity.SysRegisterNecessaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysregisternecessary.service.SysRegisterNecessaryService;

@Transactional
@Service("registerNecessaryService")
public class SysRegisterNecessaryServiceImpl extends ServiceImpl<SysRegisterNecessaryDao, SysRegisterNecessaryEntity>
		implements SysRegisterNecessaryService {

	public static final String KEY_CACHE = "RegisterNecessary";

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
	public List<SysRegisterNecessaryEntity> getRegisterNecessary() {
		return selectList(new EntityWrapper<SysRegisterNecessaryEntity>(null).orderBy(true, "order_number"));
	}

}
