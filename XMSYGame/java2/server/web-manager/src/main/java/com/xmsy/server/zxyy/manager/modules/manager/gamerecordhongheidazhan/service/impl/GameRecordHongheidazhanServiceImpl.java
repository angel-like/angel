package com.xmsy.server.zxyy.manager.modules.manager.gamerecordhongheidazhan.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhongheidazhan.dao.GameRecordHongheidazhanDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhongheidazhan.entity.GameRecordHongheidazhanEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhongheidazhan.service.GameRecordHongheidazhanService;


@Service("gameRecordHongheidazhanService")
public class GameRecordHongheidazhanServiceImpl extends ServiceImpl<GameRecordHongheidazhanDao, GameRecordHongheidazhanEntity> 
implements GameRecordHongheidazhanService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
