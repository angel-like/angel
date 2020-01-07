package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlordsfangka.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.fightlandlordsfangka.FightlandlordsFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.fightlandlordsfangka.FightlandlordsFangkaUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlordsfangka.dao.GameRecordFightlandlordsFangkaDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlordsfangka.entity.GameRecordFightlandlordsFangkaEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlordsfangka.service.GameRecordFightlandlordsFangkaService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("gameRecordFightlandlordsFangkaService")
public class GameRecordFightlandlordsFangkaServiceImpl extends ServiceImpl<GameRecordFightlandlordsFangkaDao, GameRecordFightlandlordsFangkaEntity> 
implements GameRecordFightlandlordsFangkaService ,IAppGameRecordService{
	
	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveFightlandlordsFangkaGameRecord(FightlandlordsFangkaGameRecordParams gameRecordParams) {
		List<GameRecordFightlandlordsFangkaEntity> gameRecordList=new ArrayList<>();
		GameRecordFightlandlordsFangkaEntity gameRecord=null;
		String gameName=gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName=gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName=gameInfo.getRoomName(gameRecordParams.getRoomId());
		for(FightlandlordsFangkaUserRecordParams userRecord:gameRecordParams.getUserRecord()) {
			gameRecord=new GameRecordFightlandlordsFangkaEntity();
			gameRecord.setUserId(userRecord.getUserId())
			.setUserAccount(userRecord.getUserAccount())
			.setGameModule(gameRecordParams.getGameModule())
			.setPayType(gameRecordParams.getPayType())
			.setGameType(gameRecordParams.getGameType())
			.setCoins(userRecord.getCoins())
			.setBetCoins(userRecord.getBetCoins())
			.setCoinsBefore(userRecord.getCoinsBefore())
			.setCoinsAfter(userRecord.getCoinsAfter())
			.setBaseScore(userRecord.getScore())
			.setMultiple(userRecord.getMultiple())
			.setRobot(userRecord.getRobot());
			gameRecord.setRoomNo(gameRecordParams.getRoomNo()==null?"":gameRecordParams.getRoomNo().trim());
			gameRecord.setGameId(gameRecordParams.getGameId())
			.setGameName(gameName)
			.setGradeId(gameRecordParams.getGradeId())
			.setGradeName(gradeName)
			.setRoomId(gameRecordParams.getRoomId())
			.setRoomName(roomName)
			.setGameRoundNo(gameRecordParams.getGameRoundNo())
			.setRound(gameRecordParams.getRound());
			
			gameRecordList.add(gameRecord);
		}
		
		this.insertBatch(gameRecordList);
		
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		FightlandlordsFangkaGameRecordParams fightlandlordsFangkaGameRecordParams = JSON.parseObject(gameRecordParam,
				FightlandlordsFangkaGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(fightlandlordsFangkaGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveFightlandlordsFangkaGameRecord(fightlandlordsFangkaGameRecordParams);
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
