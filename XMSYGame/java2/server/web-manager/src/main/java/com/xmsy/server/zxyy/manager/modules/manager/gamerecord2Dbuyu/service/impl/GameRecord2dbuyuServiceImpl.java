package com.xmsy.server.zxyy.manager.modules.manager.gamerecord2Dbuyu.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord2Dbuyu.dao.GameRecord2dbuyuDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord2Dbuyu.entity.GameRecord2dbuyuEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord2Dbuyu.service.GameRecord2dbuyuService;


@Service("gameRecord2dbuyuService")
public class GameRecord2dbuyuServiceImpl extends ServiceImpl<GameRecord2dbuyuDao, GameRecord2dbuyuEntity> 
	implements GameRecord2dbuyuService , IAppGameRecordService{

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
