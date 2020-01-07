package com.xmsy.server.zxyy.webhome.modules.manager.userblacklist.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.cache.EhCacheKey;
import com.xmsy.server.zxyy.webhome.cache.EhCacheName;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.modules.manager.userblacklist.dao.UserBlacklistDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userblacklist.entity.UserBlacklistEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userblacklist.service.UserBlacklistService;

@Transactional
@Service("userBlacklistService")
public class UserBlacklistServiceImpl extends ServiceImpl<UserBlacklistDao, UserBlacklistEntity>
		implements UserBlacklistService {

	@Override
	@CacheEvict(value = EhCacheName.USER_BLACKLIST_CACHE, allEntries = true)
	public boolean insert(UserBlacklistEntity entity) {
		// TODO Auto-generated method stub
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.USER_BLACKLIST_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.USER_BLACKLIST_CACHE, allEntries = true)
	public boolean updateById(UserBlacklistEntity entity) {
		// TODO Auto-generated method stub
		return super.updateById(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.USER_BLACKLIST_CACHE, key = "#id")
	public UserBlacklistEntity selectById(Serializable id) {
		// TODO Auto-generated method stub
		return super.selectById(id);
	}

	@Override
	@Cacheable(value = EhCacheName.USER_BLACKLIST_CACHE, key = EhCacheKey.USER_BLACK_LIST_FOR_INTERCEPTOR, unless = "#result == null || #result.size() == 0")
	public List<UserBlacklistEntity> selectList() {
		return baseMapper.selectList(new EntityWrapper<UserBlacklistEntity>());
	}

	@Override
	@CacheEvict(value = EhCacheName.USER_BLACKLIST_CACHE, allEntries = true)
	public boolean delete(Wrapper<UserBlacklistEntity> wrapper) {
		// TODO Auto-generated method stub
		return super.delete(wrapper);
	}

	@Override
	@Cacheable(value = EhCacheName.USER_BLACKLIST_CACHE, key = EhCacheKey.USER_BLACK_LIST_FOR_BACKSTAGE, unless = "#result == null")
	public PageUtils queryPage(Map<String, Object> params, Boolean isBlack) {
		UserBlacklistEntity entity = new UserBlacklistEntity();
		entity.setType(isBlack);
		if (params != null) {
			if (params.get("userAccount") != null) {
				entity.setUserAccount(params.get("userAccount").toString());
			}
		}
		Wrapper<UserBlacklistEntity> wrapper = new EntityWrapper<UserBlacklistEntity>(entity);
		String sort = params.get("sort") != null ? params.get("sort").toString() : "";
		if (!StringUtils.isEmpty(sort)) {
			String direction = params.get("direction") != null ? params.get("direction").toString() : "";
			if ("desc".equalsIgnoreCase(direction)) {
				wrapper.orderDesc(Arrays.asList(sort.split(",")));
			} else {
				wrapper.orderAsc(Arrays.asList(sort.split(",")));
			}
		}
		Page<UserBlacklistEntity> page = this.selectPage(new Query<UserBlacklistEntity>(params).getPage(), wrapper);

		return new PageUtils(page);
	}

}
