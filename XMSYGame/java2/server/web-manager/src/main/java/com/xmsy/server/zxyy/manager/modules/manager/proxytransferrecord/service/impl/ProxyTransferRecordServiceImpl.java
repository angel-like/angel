package com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.dao.ProxyTransferRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.entity.ProxyTransferRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.service.ProxyTransferRecordService;


@Service("proxyTransferRecordService")
public class ProxyTransferRecordServiceImpl extends ServiceImpl<ProxyTransferRecordDao, ProxyTransferRecordEntity> implements ProxyTransferRecordService {

	@Override
	public Map<String, Object> sumTransferMoneyAmount(String startDate, String endDate, Long proxyId) {
		return this.baseMapper.sumTransferMoneyAmount(startDate, endDate, proxyId);
	}

	
}
