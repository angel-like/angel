package com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.dao.RechargeAmountDao;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.entity.RechargeAmountEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.service.RechargeAmountService;


@Service("rechargeAmountService")
public class RechargeAmountServiceImpl extends ServiceImpl<RechargeAmountDao, RechargeAmountEntity> implements RechargeAmountService {

	@Override
	public Page<RechargeAmountEntity> selectNewPage(	
			RechargeAmountEntity apprechargenavigationchild, PageParam pageParam) {
		Page<RechargeAmountEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		// TODO Auto-generated method stub
		return page.setRecords(this.baseMapper.selectNewPage(page, apprechargenavigationchild));
	}

	@Override
	public void updateOrSave(RechargeAmountEntity rechargeAmountEntity) {

	}
}
