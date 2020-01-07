package com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.dao.ReportPlayerGameDailyDao;
import com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.entity.ReportPlayerGameDailyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.service.ReportPlayerGameDailyService;


@Service("reportPlayerGameDailyService")
public class ReportPlayerGameDailyServiceImpl extends ServiceImpl<ReportPlayerGameDailyDao, ReportPlayerGameDailyEntity> implements ReportPlayerGameDailyService {

	@Override
	public Map<String, Object> querySum(Long userId, Long gradeId,Long gameId) {
		return baseMapper.querySum(userId, gradeId, gameId);
	}

	




}
