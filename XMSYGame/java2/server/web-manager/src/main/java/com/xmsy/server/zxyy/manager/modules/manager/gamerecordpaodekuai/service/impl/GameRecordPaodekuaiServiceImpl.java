package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuai.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuai.dao.GameRecordPaodekuaiDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuai.entity.GameRecordPaodekuaiEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuai.service.GameRecordPaodekuaiService;


@Service("gameRecordPaodekuaiService")
public class GameRecordPaodekuaiServiceImpl extends ServiceImpl<GameRecordPaodekuaiDao, GameRecordPaodekuaiEntity> 
implements GameRecordPaodekuaiService , IAppGameRecordService{

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
