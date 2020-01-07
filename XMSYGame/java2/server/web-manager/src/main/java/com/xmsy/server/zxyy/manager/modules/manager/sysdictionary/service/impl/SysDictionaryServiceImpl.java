package com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.dao.SysDictionaryDao;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;

/**
 * .数据字典
 * 
 * @author aleng
 *
 */
@Transactional
@Service("sysDictionaryService")
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryDao, SysDictionaryEntity>
		implements SysDictionaryService {

	@Override
	@CacheEvict(cacheNames = EhCacheName.SYSDICTIONARY_CACHE, allEntries = true)
	public boolean insert(SysDictionaryEntity entity) {
		// TODO Auto-generated method stub
		return super.insert(entity);
	}

	@Override
	@CacheEvict(cacheNames = EhCacheName.SYSDICTIONARY_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(cacheNames = EhCacheName.SYSDICTIONARY_CACHE, allEntries = true)
	public boolean updateById(SysDictionaryEntity entity) {
		// TODO Auto-generated method stub
		return super.updateById(entity);
	}

	@Override
	public SysDictionaryEntity selectById(Serializable id) {
		// TODO Auto-generated method stub
		return super.selectById(id);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		SysDictionaryEntity entity = new SysDictionaryEntity();
		if (params != null) {
			if (params.get("key") != null) {
				entity.setCode(params.get("key").toString());
			}
			if (params.get("parentCode") != null) {
				entity.setParentCode(params.get("parentCode").toString());
			}
		}
		Wrapper<SysDictionaryEntity> wrapper = new EntityWrapper<SysDictionaryEntity>(entity);
		String sort = params.get("sort") != null ? params.get("sort").toString() : "";
		if (!StringUtils.isEmpty(sort)) {
			String direction = params.get("direction") != null ? params.get("direction").toString() : "";
			if ("desc".equalsIgnoreCase(direction)) {
				wrapper.orderDesc(Arrays.asList(sort.split(",")));
			} else {
				wrapper.orderAsc(Arrays.asList(sort.split(",")));
			}
		}
		Page<SysDictionaryEntity> page = this.selectPage(new Query<SysDictionaryEntity>(params).getPage(), wrapper);

		return new PageUtils(page);
	}

	@Override
	public List<SysDictionaryEntity> findListByParentCode(String parentCode) {
		Wrapper<SysDictionaryEntity> wrapper = new EntityWrapper<SysDictionaryEntity>();
		wrapper.eq("parent_code", parentCode);
		wrapper.eq("enable", true);
		wrapper.orderAsc(Arrays.asList("code".split(",")));
		return this.selectList(wrapper);
	}

	@Override
	public String getName(String parentCode, String code) {
		SysDictionaryEntity entity = new SysDictionaryEntity();
		entity.setParentCode(parentCode);
		entity.setCode(code);
		SysDictionaryEntity sysDictionary = baseMapper.selectOne(entity);
		if (null == sysDictionary) {
			return null;
		}
		// TODO Auto-generated method stub
		return sysDictionary.getName();
	}

}
