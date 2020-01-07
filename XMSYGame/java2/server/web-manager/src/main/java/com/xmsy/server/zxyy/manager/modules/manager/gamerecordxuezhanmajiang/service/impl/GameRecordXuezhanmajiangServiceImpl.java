package com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiang.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiang.dao.GameRecordXuezhanmajiangDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiang.entity.GameRecordXuezhanmajiangEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiang.service.GameRecordXuezhanmajiangService;


@Service("gameRecordXuezhanmajiangService")
public class GameRecordXuezhanmajiangServiceImpl extends ServiceImpl<GameRecordXuezhanmajiangDao, GameRecordXuezhanmajiangEntity> 
implements GameRecordXuezhanmajiangService , IAppGameRecordService{

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
