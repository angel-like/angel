package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuaifangka.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuaifangka.dao.GameRecordPaodekuaiFangkaDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuaifangka.entity.GameRecordPaodekuaiFangkaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuaifangka.service.GameRecordPaodekuaiFangkaService;


@Service("gameRecordPaodekuaiFangkaService")
public class GameRecordPaodekuaiFangkaServiceImpl extends ServiceImpl<GameRecordPaodekuaiFangkaDao, GameRecordPaodekuaiFangkaEntity> 
implements GameRecordPaodekuaiFangkaService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
