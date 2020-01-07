package com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.dao.IpBlacklistDao;
import com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.entity.IpBlacklistEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.service.IpBlacklistService;

@Service("ipBlacklistService")
public class IpBlacklistServiceImpl extends ServiceImpl<IpBlacklistDao, IpBlacklistEntity>
		implements IpBlacklistService {

	@Override
	@CacheEvict(value = EhCacheName.IP_BLACKLIST_CACHE, allEntries = true)
	public boolean insert(IpBlacklistEntity entity) {
		// TODO Auto-generated method stub
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.IP_BLACKLIST_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.IP_BLACKLIST_CACHE, allEntries = true)
	public boolean updateById(IpBlacklistEntity entity) {
		// TODO Auto-generated method stub
		return super.updateById(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.IP_BLACKLIST_CACHE, key = "#id")
	public IpBlacklistEntity selectById(Serializable id) {
		// TODO Auto-generated method stub
		return super.selectById(id);
	}

	@Override
	public List<IpBlacklistEntity> selectList() {
		// TODO Auto-generated method stub
		return super.selectList(new EntityWrapper<IpBlacklistEntity>(new IpBlacklistEntity()));
	}

}
