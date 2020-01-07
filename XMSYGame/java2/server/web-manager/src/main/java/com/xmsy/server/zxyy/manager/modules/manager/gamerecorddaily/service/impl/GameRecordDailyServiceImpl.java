package com.xmsy.server.zxyy.manager.modules.manager.gamerecorddaily.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddaily.dao.GameRecordDailyDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddaily.entity.GameRecordDailyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddaily.service.GameRecordDailyService;


@Service("gameRecordDailyService")
public class GameRecordDailyServiceImpl extends ServiceImpl<GameRecordDailyDao, GameRecordDailyEntity> implements GameRecordDailyService {

	@Override
	public PageUtils selectuserPumpTotal(GameRecordDailyEntity gamerecorddaily,PageParam pageParam) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		page.setRecords(this.baseMapper.selectuserPumpTotal(gamerecorddaily, page));
		return new PageUtils(page);
		
	}

	@Override
	public List<GameRecordDailyEntity> selectuserPumpList(GameRecordDailyEntity gamerecorddaily) {
		return this.baseMapper.selectuserPumpList(gamerecorddaily);
	}


}
