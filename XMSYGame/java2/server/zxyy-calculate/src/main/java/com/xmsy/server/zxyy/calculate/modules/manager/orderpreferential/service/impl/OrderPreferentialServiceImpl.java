package com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.dao.OrderPreferentialDao;
import com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.service.OrderPreferentialService;
@Transactional
@Service("orderPreferentialService")
public class OrderPreferentialServiceImpl extends ServiceImpl<OrderPreferentialDao, OrderPreferentialEntity>
		implements OrderPreferentialService {

	
	@Override
	public List<OrderPreferentialEntity> getPreferentialsByHierarchyId(Long hierarchyId) {
		OrderPreferentialEntity orderPreferentialEntity = new OrderPreferentialEntity().setHierarchyId(hierarchyId);
		return baseMapper.selectList(new EntityWrapper<OrderPreferentialEntity>(orderPreferentialEntity));
	}
}
