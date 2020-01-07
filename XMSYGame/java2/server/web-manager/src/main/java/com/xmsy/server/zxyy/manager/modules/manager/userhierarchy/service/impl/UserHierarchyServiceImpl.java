package com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.app.hierarchy.param.HierarchyRateResult;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.service.HierarchyGameRoleService;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.service.OrderPreferentialService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.dao.UserHierarchyDao;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.manager.modules.manager.userriskrecord.entity.UserRiskRecordEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

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
		if(userHierarchy.getHierarchyType()==SysConstant.HIERARCHYTYPE_0) {
			// 新增层级优惠
			OrderPreferentialEntity entity = new OrderPreferentialEntity();
			entity.setFirstRecharge(SysConstant.ENABLE_TRUE);
			entity.setRechargeAmount(0L);// 充值要求默认为0
			entity.setHierarchyId(hierarchyId);
			orderPreferentialService.insert(entity);// 新增一条首充优惠规则
			entity.setFirstRecharge(SysConstant.ENABLE_FALSE);
			orderPreferentialService.insert(entity);// 新增一条返利优惠规则
		}
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

	@Transactional
	@Override
	public void addUserRiskRecord(UserEntity user) {
		// 增加用户风控记录
		UserRiskRecordEntity entity = new UserRiskRecordEntity();
		entity.setCreateTime(new Date());
		entity.setUserId(user.getId());
		entity.setUserAccount(user.getAccount());
		entity.setCoin(0l);
		entity.setHierarchyId(user.getRiskHierarchyId());
		entity.setRechargeVal(null);
		entity.setRiskType("管理员设置");
		entity.setRiskVal(null);
		entity.insert();

	}

	@Override
	public Map<Long, String> getHierarchyMap() {
		List<UserHierarchyEntity> dataList = this.selectList(new EntityWrapper<UserHierarchyEntity>(new UserHierarchyEntity()));
		Map<Long, String> hierarchyMap=new HashMap<>();
		hierarchyMap.put(0l, "");
		if(null != dataList && !dataList.isEmpty()) {
			for(UserHierarchyEntity data : dataList) {
				hierarchyMap.put(data.getId(), data.getName());
			}
		}
		return hierarchyMap;
	}

	@Override
	public String getHierarchyName(String hierarchyId) {
		return this.baseMapper.getHierarchyName(hierarchyId);
	}

}
