package com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.cache.EhCacheName;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.hierarchy.param.HierarchyRateResult;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.service.HierarchyGameRoleService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderpreferential.service.OrderPreferentialService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.dao.UserHierarchyDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userHierarchyService")
public class UserHierarchyServiceImpl extends ServiceImpl<UserHierarchyDao, UserHierarchyEntity>
		implements UserHierarchyService {
	@Autowired
	private HierarchyGameRoleService hierarchyGameRoleService;
	@Autowired
	private OrderPreferentialService orderPreferentialService;

	@Override
	@CacheEvict(value = EhCacheName.USER_HIERARCHY_CACHE, allEntries = true)
	public boolean insert(UserHierarchyEntity entity) {
		// TODO Auto-generated method stub
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.USER_HIERARCHY_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.USER_HIERARCHY_CACHE, allEntries = true)
	public boolean updateById(UserHierarchyEntity userHierarchy) {
		Long id = userHierarchy.getId();
		List<Long> list = userHierarchy.getGameIds();
		hierarchyGameRoleService.deleteByHierarchyId(id);
		if (!CollectionUtils.isEmpty(list)) {
			for (Long gameId : list) {
				HierarchyGameRoleEntity entity = new HierarchyGameRoleEntity();
				entity.setGameId(gameId);
				entity.setHierarchyId(id);
				hierarchyGameRoleService.insert(entity);
			}

		}
		return super.updateById(userHierarchy);
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.USER_HIERARCHY_CACHE, key = "#id", unless = "#result == null")
	public UserHierarchyEntity selectById(Serializable id) {
		UserHierarchyEntity entity = super.selectById(id);
		List<Long> gameIdList = new ArrayList<Long>();
		if (entity != null) {
			List<HierarchyGameRoleEntity> list = hierarchyGameRoleService
					.selectList(new EntityWrapper<HierarchyGameRoleEntity>(
							new HierarchyGameRoleEntity().setHierarchyId(entity.getId())));
			if (!CollectionUtils.isEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					gameIdList.add(list.get(i).getGameId());
				}
				if (!CollectionUtils.isEmpty(gameIdList)) {
					entity.setGameIds(gameIdList);
				}
			}
		}
		return entity;
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.USER_HIERARCHY_CACHE, key = "'default'", unless = "#result == null")
	public UserHierarchyEntity getDefaultHierarchy() {
		UserHierarchyEntity entity = new UserHierarchyEntity();
		entity.setType(SysConstant.ENABLE_TRUE);// 是默认层级
		List<UserHierarchyEntity> list = baseMapper.selectList(new EntityWrapper<UserHierarchyEntity>(entity));
		if (CollectionUtils.isEmpty(list)) {
			log.info("[selectIdForType] 默认层级为空 需要设置默认层级{}", list);
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = EhCacheName.USER_HIERARCHY_CACHE, allEntries = true)
	public void setDefault(Long id) {
		this.baseMapper.updateType(null, false);
		this.baseMapper.updateType(id, true);

	}

	@Override
	@CacheEvict(value = EhCacheName.USER_HIERARCHY_CACHE, allEntries = true)
	public void insertHierarchyAndPreferential(UserHierarchyEntity userHierarchy) {
		baseMapper.insertGetId(userHierarchy);
		Long hierarchyId = userHierarchy.getId();
		// 新增层级优惠
		OrderPreferentialEntity entity = new OrderPreferentialEntity();
		entity.setFirstRecharge(SysConstant.ENABLE_TRUE);
		entity.setRechargeAmount(0L);// 充值要求默认为0
		entity.setHierarchyId(hierarchyId);
		orderPreferentialService.insert(entity);// 新增一条首充优惠规则
		entity.setFirstRecharge(SysConstant.ENABLE_FALSE);
		orderPreferentialService.insert(entity);// 新增一条返利优惠规则
		List<Long> list = userHierarchy.getGameIds();
		if (!CollectionUtils.isEmpty(list)) {
			for (Long gameId : list) {
				HierarchyGameRoleEntity roleEntity = new HierarchyGameRoleEntity();
				roleEntity.setGameId(gameId);
				roleEntity.setHierarchyId(hierarchyId);
				hierarchyGameRoleService.insert(roleEntity);
			}
		}
	}

	@Override
	public List<HierarchyRateResult> getHierarchyRateList() {
		UserHierarchyEntity userHierarchy = new UserHierarchyEntity();
		userHierarchy.setHierarchyType(SysConstant.HIERARCHYTYPE_1);
		Wrapper<UserHierarchyEntity> entityWrapper = new EntityWrapper<UserHierarchyEntity>(userHierarchy);
		List<UserHierarchyEntity> dataList = this.selectList(entityWrapper);
		List<HierarchyRateResult> rateList = new ArrayList<>();
		if (dataList != null && !dataList.isEmpty()) {
			HierarchyRateResult rate = null;
			for (UserHierarchyEntity data : dataList) {
				rate = new HierarchyRateResult();
				rate.setHierarchyId(data.getId());
				rate.setRate(data.getGameRate());
				rateList.add(rate);
			}
		}
		return rateList;
	}

}
