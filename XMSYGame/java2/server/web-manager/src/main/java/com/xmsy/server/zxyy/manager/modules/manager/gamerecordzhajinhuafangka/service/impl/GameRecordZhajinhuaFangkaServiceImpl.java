package com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhuafangka.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhuafangka.dao.GameRecordZhajinhuaFangkaDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhuafangka.entity.GameRecordZhajinhuaFangkaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhuafangka.service.GameRecordZhajinhuaFangkaService;


@Service("gameRecordZhajinhuaFangkaService")
public class GameRecordZhajinhuaFangkaServiceImpl extends ServiceImpl<GameRecordZhajinhuaFangkaDao, GameRecordZhajinhuaFangkaEntity> 
implements GameRecordZhajinhuaFangkaService , IAppGameRecordService{

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
