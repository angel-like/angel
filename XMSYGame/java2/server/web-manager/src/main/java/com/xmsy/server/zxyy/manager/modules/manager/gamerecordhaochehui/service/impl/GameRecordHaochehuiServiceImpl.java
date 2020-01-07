package com.xmsy.server.zxyy.manager.modules.manager.gamerecordhaochehui.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhaochehui.dao.GameRecordHaochehuiDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhaochehui.entity.GameRecordHaochehuiEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhaochehui.service.GameRecordHaochehuiService;


@Service("gameRecordHaochehuiService")
public class GameRecordHaochehuiServiceImpl extends ServiceImpl<GameRecordHaochehuiDao, GameRecordHaochehuiEntity> 
implements GameRecordHaochehuiService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
