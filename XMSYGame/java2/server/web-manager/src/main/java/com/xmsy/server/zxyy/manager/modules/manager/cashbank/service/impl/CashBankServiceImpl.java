package com.xmsy.server.zxyy.manager.modules.manager.cashbank.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.modules.manager.cashbank.dao.CashBankDao;
import com.xmsy.server.zxyy.manager.modules.manager.cashbank.entity.CashBankEntity;
import com.xmsy.server.zxyy.manager.modules.manager.cashbank.service.CashBankService;

@Transactional
@Service("sysBankService")
public class CashBankServiceImpl extends ServiceImpl<CashBankDao, CashBankEntity> implements CashBankService {

	@Override
	@CacheEvict(value = "cashBankCache", allEntries = true)
	public boolean updateById(CashBankEntity entity) {
		return super.updateById(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.CASH_BANK_CACHE, allEntries = true)
	public boolean insert(CashBankEntity entity) {
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.CASH_BANK_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	@Override
	public CashBankEntity selectById(Serializable id) {
		return super.selectById(id);
	}

	@Override

	public Page<CashBankEntity> findCashBankPage(PageParam pageParam, CashBankEntity cashBankEntity) {
		Page<CashBankEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(baseMapper.findCashBanks(page,cashBankEntity));
	}

	@Override
	public List<CashBankEntity> findCashBankByHierarchyId(String hierarchyId) {
		return baseMapper.selectList(
				new EntityWrapper<CashBankEntity>(new CashBankEntity().setEnable(true).setHierarchyId(hierarchyId)));
	}
}
