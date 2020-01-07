package com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.dao.OrderPreferentialDao;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.service.OrderPreferentialService;

@Transactional
@Service("orderPreferentialService")
public class OrderPreferentialServiceImpl extends ServiceImpl<OrderPreferentialDao, OrderPreferentialEntity>
		implements OrderPreferentialService {

	@Override
	@CacheEvict(value = EhCacheName.ORDER_PREFERENTIAL_CACHE, allEntries = true)
	public boolean updateById(OrderPreferentialEntity entity) {
		return super.updateById(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.ORDER_PREFERENTIAL_CACHE, allEntries = true)
	public boolean insert(OrderPreferentialEntity entity) {
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.ORDER_PREFERENTIAL_CACHE, key = "#id")
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	@Override
	public OrderPreferentialEntity selectById(Serializable id) {
		return super.selectById(id);
	}

	@Override
	public List<OrderPreferentialEntity> getPreferentialsByHierarchyId(Long hierarchyId) {
		OrderPreferentialEntity orderPreferentialEntity = new OrderPreferentialEntity().setHierarchyId(hierarchyId);
		return baseMapper.selectList(new EntityWrapper<OrderPreferentialEntity>(orderPreferentialEntity));
	}
}
