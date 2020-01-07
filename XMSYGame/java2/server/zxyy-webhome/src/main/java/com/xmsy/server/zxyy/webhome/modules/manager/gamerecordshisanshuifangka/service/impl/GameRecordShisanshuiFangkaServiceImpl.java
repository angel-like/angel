package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.base.Joiner;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.constant.CardsConstants;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.shisanshuifangka.ShiSanShuiFangKaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.shisanshuifangka.ShiSanShuiFangKaUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.dao.GameRecordShisanshuiFangkaDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.entity.GameRecordShisanshuiFangkaEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.service.GameRecordShisanshuiFangkaService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordShisanshuiFangkaService")
public class GameRecordShisanshuiFangkaServiceImpl extends ServiceImpl<GameRecordShisanshuiFangkaDao, GameRecordShisanshuiFangkaEntity> 
implements GameRecordShisanshuiFangkaService,IAppGameRecordService{
	@Autowired
	private GameInfoComponent gameInfo;
	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		ShiSanShuiFangKaGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				ShiSanShuiFangKaGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(gameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(gameRecordParams);
	}

	@Override
	public void appSaveGameRecord(ShiSanShuiFangKaGameRecordParams gameRecordParams) {
		List<GameRecordShisanshuiFangkaEntity> gameRecordList=new ArrayList<>();
		GameRecordShisanshuiFangkaEntity gameRecord=null;
		String gameName=gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName=gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName=gameInfo.getRoomName(gameRecordParams.getRoomId());
		for(ShiSanShuiFangKaUserRecordParams userRecord:gameRecordParams.getUserRecord()) {
			gameRecord=new GameRecordShisanshuiFangkaEntity();
			gameRecord.setUserId(userRecord.getUserId())
			.setUserAccount(userRecord.getUserAccount())
			.setCoins(userRecord.getCoins())
			.setBetCoins(userRecord.getBetCoins())
			.setCoinsBefore(userRecord.getCoinsBefore())
			.setCoinsAfter(userRecord.getCoinsAfter())
			.setBaseScore(userRecord.getScore())
			.setMultiple(userRecord.getMultiple())
			.setRobot(userRecord.getRobot())
			.setHeadCardsInt(Joiner.on(",").join(userRecord.getHeadCards()))
			.setMiddleCardsInt(Joiner.on(",").join(userRecord.getMiddleCards()))
			.setBottomCardsInt(Joiner.on(",").join(userRecord.getBottomCards()));
			gameRecord.setRoomNo(gameRecordParams.getRoomNo()==null?"":gameRecordParams.getRoomNo().trim());
			StringBuilder headCarts = new StringBuilder();
			for (Integer card : userRecord.getHeadCards()) {
				headCarts.append(CardsConstants.CARD_ARRAY[card]);
				headCarts.append(",");
			}
			headCarts.deleteCharAt(headCarts.length() - 1);
			
			StringBuilder middleCarts = new StringBuilder();
			for (Integer card : userRecord.getMiddleCards()) {
				middleCarts.append(CardsConstants.CARD_ARRAY[card]);
				middleCarts.append(",");
			}
			middleCarts.deleteCharAt(middleCarts.length() - 1);
			
			StringBuilder bottomCarts = new StringBuilder();
			for (Integer card : userRecord.getBottomCards()) {
				bottomCarts.append(CardsConstants.CARD_ARRAY[card]);
				bottomCarts.append(",");
			}
			bottomCarts.deleteCharAt(bottomCarts.length() - 1);
			
			gameRecord.setHeadCardsStr(headCarts.toString())
			.setMiddleCardsStr(middleCarts.toString())
			.setBottomCardsStr(bottomCarts.toString());
			
			gameRecord.setGameId(gameRecordParams.getGameId())
			.setGameName(gameName)
			.setGradeId(gameRecordParams.getGradeId())
			.setGradeName(gradeName)
			.setRoomId(gameRecordParams.getRoomId())
			.setRoomName(roomName)
			.setRound(gameRecordParams.getRound())
			.setGameModule(gameRecordParams.getGameModule())
			.setGameType(gameRecordParams.getGameType())
			.setPayType(gameRecordParams.getPayType())
			.setGameRoundNo(gameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
		}
		
		this.insertBatch(gameRecordList);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
