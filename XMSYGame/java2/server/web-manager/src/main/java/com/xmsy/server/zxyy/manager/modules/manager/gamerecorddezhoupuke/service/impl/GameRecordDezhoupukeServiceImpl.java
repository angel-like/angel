package com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.dao.GameRecordDezhoupukeDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.entity.GameRecordDezhoupukeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.service.GameRecordDezhoupukeService;


@Service("gameRecordDezhoupukeService")
public class GameRecordDezhoupukeServiceImpl extends ServiceImpl<GameRecordDezhoupukeDao, GameRecordDezhoupukeEntity> 
implements GameRecordDezhoupukeService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}

	@Override
	public List<GameRecordDezhoupukeEntity> findAllCardType(String gameRoundNo) {
		return baseMapper.findAllCardType(gameRoundNo);
	}


}
