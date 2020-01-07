package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairensangong.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairensangong.dao.GameRecordBairensangongDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairensangong.entity.GameRecordBairensangongEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairensangong.service.GameRecordBairensangongService;


@Service("gameRecordBairensangongService")
public class GameRecordBairensangongServiceImpl extends ServiceImpl<GameRecordBairensangongDao, GameRecordBairensangongEntity> 
implements GameRecordBairensangongService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
