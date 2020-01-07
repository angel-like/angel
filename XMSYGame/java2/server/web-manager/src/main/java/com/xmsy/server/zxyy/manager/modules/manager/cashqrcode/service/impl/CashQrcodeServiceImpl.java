package com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.recharge.result.AppQrcodeRechargeResult;
import com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.dao.CashQrcodeDao;
import com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.entity.CashQrcodeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.service.CashQrcodeService;

@Service("cashQrcodeService")
public class CashQrcodeServiceImpl extends ServiceImpl<CashQrcodeDao, CashQrcodeEntity> implements CashQrcodeService {

	@Override
	public CashQrcodeEntity findCashQrcodeByHierarchyId(Long hierarchyId, Long type) {
		List<CashQrcodeEntity> list = baseMapper.findCashQrcodeByHierarchyId(hierarchyId, type);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		int index = (int) (Math.random() * list.size());
		return list.get(index);
	}

	@Override
	public AppQrcodeRechargeResult findCashQrcode(Long hierarchyId, Long type) {
		List<AppQrcodeRechargeResult> list = baseMapper.findCashQrcode(hierarchyId, type);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		int index = (int) (Math.random() * list.size());
		return list.get(index);
	}

}
