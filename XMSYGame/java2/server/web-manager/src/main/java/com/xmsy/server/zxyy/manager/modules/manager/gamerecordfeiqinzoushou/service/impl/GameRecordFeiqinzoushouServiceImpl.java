package com.xmsy.server.zxyy.manager.modules.manager.gamerecordfeiqinzoushou.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfeiqinzoushou.dao.GameRecordFeiqinzoushouDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfeiqinzoushou.entity.GameRecordFeiqinzoushouEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfeiqinzoushou.service.GameRecordFeiqinzoushouService;


@Service("gameRecordFeiqinzoushouService")
public class GameRecordFeiqinzoushouServiceImpl extends ServiceImpl<GameRecordFeiqinzoushouDao, GameRecordFeiqinzoushouEntity> 
implements GameRecordFeiqinzoushouService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
