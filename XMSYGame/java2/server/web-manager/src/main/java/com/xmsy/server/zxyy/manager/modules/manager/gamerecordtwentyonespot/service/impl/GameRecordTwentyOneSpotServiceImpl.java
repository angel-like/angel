package com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwentyonespot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwentyonespot.dao.GameRecordTwentyOneSpotDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwentyonespot.entity.GameRecordTwentyOneSpotEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwentyonespot.service.GameRecordTwentyOneSpotService;


@Service("gameRecordTwentyOneSpotService")
public class GameRecordTwentyOneSpotServiceImpl extends ServiceImpl<GameRecordTwentyOneSpotDao, GameRecordTwentyOneSpotEntity> 
implements GameRecordTwentyOneSpotService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
