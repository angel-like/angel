package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.twentyonespot.TwentyOneSpotGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.twentyonespot.TwentyOneSpotUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.dao.GameRecordTwentyOneSpotDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.entity.GameRecordTwentyOneSpotEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.service.GameRecordTwentyOneSpotService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordTwentyOneSpotService")
public class GameRecordTwentyOneSpotServiceImpl extends ServiceImpl<GameRecordTwentyOneSpotDao, GameRecordTwentyOneSpotEntity> 
implements GameRecordTwentyOneSpotService ,IAppGameRecordService{
	
	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(TwentyOneSpotGameRecordParams twentyOneSpotGameRecordParams) {
		List<GameRecordTwentyOneSpotEntity> gameRecordList=new ArrayList<>();
		GameRecordTwentyOneSpotEntity gameRecord=null;
		String gameName=gameInfo.getGameName(twentyOneSpotGameRecordParams.getGameId());
		String gradeName=gameInfo.getGradeName(twentyOneSpotGameRecordParams.getGradeId());
		String roomName=gameInfo.getRoomName(twentyOneSpotGameRecordParams.getRoomId());
		Integer gradeNumber = twentyOneSpotGameRecordParams.getGradeNumber();
		String playerCard = Joiner.on(",").join(twentyOneSpotGameRecordParams.getPlayerCards());
		String bankerCard = Joiner.on(",").join(twentyOneSpotGameRecordParams.getBankerCards());
		StringBuilder stringBuilderbankerCard = new StringBuilder();
		StringBuilder stringBuilderPlayerCard = new StringBuilder();
		// 玩家
		for (Integer card : twentyOneSpotGameRecordParams.getPlayerCards()) {
			stringBuilderPlayerCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderPlayerCard.append(",");
		}
		stringBuilderPlayerCard.deleteCharAt(stringBuilderPlayerCard.length() - 1);
		
		// 庄家
		for (Integer card : twentyOneSpotGameRecordParams.getBankerCards()) {
			stringBuilderbankerCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderbankerCard.append(",");
		}
		stringBuilderbankerCard.deleteCharAt(stringBuilderbankerCard.length() - 1);
		for(TwentyOneSpotUserRecordParams userRecord:twentyOneSpotGameRecordParams.getUserRecord()) {
			gameRecord=new GameRecordTwentyOneSpotEntity();
			gameRecord.setUserId(userRecord.getUserId())
			.setUserAccount(userRecord.getUserAccount())
			.setCoins(userRecord.getCoins())
			.setBetCoins(userRecord.getBetCoins())
			.setCoinsBefore(userRecord.getCoinsBefore())
			.setCoinsAfter(userRecord.getCoinsAfter())
			.setBaseScore(userRecord.getBaseScore())
			.setAdditionalScore(userRecord.getAdditionalScore())
			.setCardsPrize(userRecord.getCardsPrize())
			.setInsurancePrize(userRecord.getInsurancePrize())
			.setPrizeCoins(userRecord.getPrizeCoins())
			.setRobot(userRecord.getRobot())
			.setPlayerCard(playerCard).setPlayerCardStr(stringBuilderPlayerCard.toString())
			.setBankerCard(bankerCard).setBankerCardStr(stringBuilderbankerCard.toString());
			
			gameRecord.setGameId(twentyOneSpotGameRecordParams.getGameId())
			.setGameName(gameName)
			.setGradeId(twentyOneSpotGameRecordParams.getGradeId())
			.setGradeName(gradeName)
			.setGradeNumber(gradeNumber)
			.setRoomId(twentyOneSpotGameRecordParams.getRoomId())
			.setRoomName(roomName)
			.setGameRoundNo(twentyOneSpotGameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
		}
		
		this.insertBatch(gameRecordList);
		
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		TwentyOneSpotGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				TwentyOneSpotGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(gameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(gameRecordParams);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
