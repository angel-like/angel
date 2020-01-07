package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlords.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.fightlandlords.FightLandlordsGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.fightlandlords.UserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlords.dao.GameRecordFightlandlordsDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlords.entity.GameRecordFightlandlordsEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlords.service.GameRecordFightlandlordsService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("gameRecordFightlandlordsService")
public class GameRecordFightlandlordsServiceImpl extends ServiceImpl<GameRecordFightlandlordsDao, GameRecordFightlandlordsEntity> 
implements GameRecordFightlandlordsService, IAppGameRecordService {
	@Autowired
	private GameInfoComponent gameInfo;
	@Override
	public void appFightLandlordsSaveGameRecord(FightLandlordsGameRecordParams gameRecordParams) {
		List<GameRecordFightlandlordsEntity> gameRecordList=new ArrayList<>();
		GameRecordFightlandlordsEntity gameRecord=null;
		String gameName=gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName=gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName=gameInfo.getRoomName(gameRecordParams.getRoomId());
		for(UserRecordParams userCoin:gameRecordParams.getUserRecord()) {
			gameRecord=new GameRecordFightlandlordsEntity();
			gameRecord.setUserId(userCoin.getUserId());
			gameRecord.setUserAccount(userCoin.getUserAccount());
			gameRecord.setCoins(userCoin.getCoins());
			gameRecord.setBaseScore(userCoin.getScore());
			gameRecord.setMultiple(userCoin.getMultiple());
			gameRecord.setRobot(userCoin.getRobot());
			gameRecord.setLandlord(userCoin.getLandlord());
			gameRecord.setCoinsBefore(userCoin.getCoinsBefore());
			gameRecord.setCoinsAfter(userCoin.getCoinsAfter());
			
			gameRecord.setGameId(gameRecordParams.getGameId());
			gameRecord.setGameName(gameName);
			gameRecord.setGradeId(gameRecordParams.getGradeId());
			gameRecord.setGradeName(gradeName);
			gameRecord.setRoomId(gameRecordParams.getRoomId());
			gameRecord.setRoomName(roomName);
			gameRecord.setGameRoundNo(gameRecordParams.getGameRoundNo());
			
			gameRecordList.add(gameRecord);
		}
		
		this.insertBatch(gameRecordList);
		
	}
	
	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		FightLandlordsGameRecordParams fightLandlordsGameRecordParams = JSON.parseObject(gameRecordParam,
				FightLandlordsGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(fightLandlordsGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appFightLandlordsSaveGameRecord(fightLandlordsGameRecordParams);
	}

	/**
	 * 获取同一局数的玩家信息
	 */
	@Override
	public List<GameRecordFightlandlordsEntity> getGameList(String gameRoundNo) {
		return baseMapper.getGameList(gameRoundNo);
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
