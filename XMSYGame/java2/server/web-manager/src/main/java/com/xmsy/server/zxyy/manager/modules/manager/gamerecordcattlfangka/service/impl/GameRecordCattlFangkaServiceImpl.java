package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.base.Joiner;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.manager.constant.CardsConstants;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.cattlfangka.CattlFangkaGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.cattlfangka.CattlFangkaUserRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.dao.GameRecordCattlFangkaDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.entity.GameRecordCattlFangkaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.service.GameRecordCattlFangkaService;
import com.xmsy.server.zxyy.manager.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordCattlFangkaService")
public class GameRecordCattlFangkaServiceImpl extends ServiceImpl<GameRecordCattlFangkaDao, GameRecordCattlFangkaEntity> 
implements GameRecordCattlFangkaService,IAppGameRecordService{

	
	@Autowired
	private GameInfoComponent gameInfo;
	
	@Override
	public void appSaveCattlFangkaGameRecord(CattlFangkaGameRecordParams gameRecordParams) {
		List<GameRecordCattlFangkaEntity> gameRecordList=new ArrayList<>();
		GameRecordCattlFangkaEntity gameRecord=null;
		String gameName=gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName=gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName=gameInfo.getRoomName(gameRecordParams.getRoomId());
		for(CattlFangkaUserRecordParams userRecord:gameRecordParams.getUserRecord()) {
			gameRecord=new GameRecordCattlFangkaEntity();
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
			.setBetMultiple(userRecord.getBetMultiple())
			.setBrandMultiple(userRecord.getBrandMultiple())
			.setMultiple(userRecord.getMultiple())
			.setRobot(userRecord.getRobot())
			.setCardsInt(Joiner.on(",").join(userRecord.getCards()));
			
			String cartstr = "";
			for (Integer card : userRecord.getCards()) {
				cartstr += "," + CardsConstants.CARD_ARRAY[card];
			}
			gameRecord.setCardsStr(cartstr.replaceFirst(",", ""));
			
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
		CattlFangkaGameRecordParams cattlFangkaGameRecordParams = JSON.parseObject(gameRecordParam,
				CattlFangkaGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(cattlFangkaGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveCattlFangkaGameRecord(cattlFangkaGameRecordParams);
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
