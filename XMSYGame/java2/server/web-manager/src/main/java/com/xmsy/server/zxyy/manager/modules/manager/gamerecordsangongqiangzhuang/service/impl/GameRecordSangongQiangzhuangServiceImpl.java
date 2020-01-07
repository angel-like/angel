package com.xmsy.server.zxyy.manager.modules.manager.gamerecordsangongqiangzhuang.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordsangongqiangzhuang.dao.GameRecordSangongQiangzhuangDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordsangongqiangzhuang.entity.GameRecordSangongQiangzhuangEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordsangongqiangzhuang.service.GameRecordSangongQiangzhuangService;


@Service("gameRecordSangongQiangzhuangService")
public class GameRecordSangongQiangzhuangServiceImpl extends ServiceImpl<GameRecordSangongQiangzhuangDao, GameRecordSangongQiangzhuangEntity> 
implements GameRecordSangongQiangzhuangService , IAppGameRecordService{

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
