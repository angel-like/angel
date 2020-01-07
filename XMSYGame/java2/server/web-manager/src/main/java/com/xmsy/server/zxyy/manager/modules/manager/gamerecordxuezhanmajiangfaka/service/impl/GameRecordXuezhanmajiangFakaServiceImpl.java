package com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiangfaka.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiangfaka.dao.GameRecordXuezhanmajiangFakaDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiangfaka.entity.GameRecordXuezhanmajiangFakaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiangfaka.service.GameRecordXuezhanmajiangFakaService;


@Service("gameRecordXuezhanmajiangFakaService")
public class GameRecordXuezhanmajiangFakaServiceImpl extends ServiceImpl<GameRecordXuezhanmajiangFakaDao, GameRecordXuezhanmajiangFakaEntity> 
implements GameRecordXuezhanmajiangFakaService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
