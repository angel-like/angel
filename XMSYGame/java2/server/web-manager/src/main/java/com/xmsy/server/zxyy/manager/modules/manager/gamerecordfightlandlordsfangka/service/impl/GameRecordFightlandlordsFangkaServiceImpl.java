package com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlordsfangka.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlordsfangka.dao.GameRecordFightlandlordsFangkaDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlordsfangka.entity.GameRecordFightlandlordsFangkaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlordsfangka.service.GameRecordFightlandlordsFangkaService;


@Service("gameRecordFightlandlordsFangkaService")
public class GameRecordFightlandlordsFangkaServiceImpl extends ServiceImpl<GameRecordFightlandlordsFangkaDao, GameRecordFightlandlordsFangkaEntity> 
implements GameRecordFightlandlordsFangkaService , IAppGameRecordService{

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
