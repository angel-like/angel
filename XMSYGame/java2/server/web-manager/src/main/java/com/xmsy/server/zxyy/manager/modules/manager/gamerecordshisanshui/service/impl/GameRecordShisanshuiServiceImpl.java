package com.xmsy.server.zxyy.manager.modules.manager.gamerecordshisanshui.service.impl;

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
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.shisanshui.ShiSanShuiGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.shisanshui.ShiSanShuiUserRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordshisanshui.dao.GameRecordShisanshuiDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordshisanshui.entity.GameRecordShisanshuiEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordshisanshui.service.GameRecordShisanshuiService;
import com.xmsy.server.zxyy.manager.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordShisanshuiService")
public class GameRecordShisanshuiServiceImpl extends ServiceImpl<GameRecordShisanshuiDao, GameRecordShisanshuiEntity> 
implements GameRecordShisanshuiService ,IAppGameRecordService{
	@Autowired
	private GameInfoComponent gameInfo;
	
	@Override
	public void appSaveGameRecord(ShiSanShuiGameRecordParams gameRecordParams) {
		List<GameRecordShisanshuiEntity> gameRecordList=new ArrayList<>();
		GameRecordShisanshuiEntity gameRecord=null;
		String gameName=gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName=gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName=gameInfo.getRoomName(gameRecordParams.getRoomId());
		for(ShiSanShuiUserRecordParams userRecord:gameRecordParams.getUserRecord()) {
			gameRecord=new GameRecordShisanshuiEntity();
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
			.setGameRoundNo(gameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
		}
		
		this.insertBatch(gameRecordList);
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		ShiSanShuiGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				ShiSanShuiGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(gameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(gameRecordParams);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
