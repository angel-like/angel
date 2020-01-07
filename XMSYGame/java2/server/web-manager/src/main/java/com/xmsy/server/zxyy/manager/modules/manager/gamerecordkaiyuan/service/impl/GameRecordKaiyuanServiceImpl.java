package com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.dao.GameRecordKaiyuanDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.entity.GameRecordKaiyuanEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.service.GameRecordKaiyuanService;


@Service("gameRecordKaiyuanService")
public class GameRecordKaiyuanServiceImpl extends ServiceImpl<GameRecordKaiyuanDao, GameRecordKaiyuanEntity> 
implements GameRecordKaiyuanService, IAppGameRecordService {

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}



}
