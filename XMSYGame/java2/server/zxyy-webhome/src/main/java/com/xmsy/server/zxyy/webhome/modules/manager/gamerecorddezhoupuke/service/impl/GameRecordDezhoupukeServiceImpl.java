package com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddezhoupuke.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.constant.CardsConstants;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.dezhoupuke.DezhoupukeGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.dezhoupuke.DezhoupukeUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddezhoupuke.dao.GameRecordDezhoupukeDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddezhoupuke.entity.GameRecordDezhoupukeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddezhoupuke.service.GameRecordDezhoupukeService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.base.Joiner;
import com.xmsy.common.define.exception.ParamInvalidException;



@Slf4j
@Service("gameRecordDezhoupukeService")
public class GameRecordDezhoupukeServiceImpl extends ServiceImpl<GameRecordDezhoupukeDao, GameRecordDezhoupukeEntity> 
implements GameRecordDezhoupukeService , IAppGameRecordService{
	
	@Autowired
	private GameInfoComponent gameInfo;
	
	@Override
	public void appSaveDezhoupukeGameRecord(DezhoupukeGameRecordParams gameRecordParams) {
		List<GameRecordDezhoupukeEntity> gameRecordList = new ArrayList<>();
		GameRecordDezhoupukeEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
		Integer gradeNumber = gameRecordParams.getGradeNumber();
		String publicCard = Joiner.on(",").join(gameRecordParams.getPublicCard());
		StringBuilder stringBuilderPublicCard = new StringBuilder();
		StringBuilder stringBuilderPlayCard = new StringBuilder();
		StringBuilder stringBuilderPlayCardStr = new StringBuilder();
		if(gameRecordParams.getPublicCard()!=null&&gameRecordParams.getPublicCard().size()!=0) {
			// 公共牌型
			for (Integer card : gameRecordParams.getPublicCard()) {
				stringBuilderPublicCard.append(CardsConstants.CARD_ARRAY[card]);
				stringBuilderPublicCard.append(",");
			}
			stringBuilderPublicCard.deleteCharAt(stringBuilderPublicCard.length() - 1);
		}
		// 玩家牌型
		Object[][] settlementCard = gameRecordParams.getPlayCard();
		for (int i = 0; i < settlementCard.length; i++) {
				stringBuilderPlayCard.append(Joiner.on(",").join(settlementCard[i]));
				stringBuilderPlayCardStr.append(settlementCard[i][0]);
				stringBuilderPlayCardStr.append(":");
				stringBuilderPlayCardStr.append(CardsConstants.CARD_ARRAY[getIndex(settlementCard[i][1])]);
				stringBuilderPlayCardStr.append(",");
				stringBuilderPlayCardStr.append(CardsConstants.CARD_ARRAY[getIndex(settlementCard[i][2])]);
				if(getIndex(settlementCard[i][3])==0) {
					stringBuilderPlayCardStr.append("(结算)");
				}else if(getIndex(settlementCard[i][3])==1){
					stringBuilderPlayCardStr.append("(弃牌)");
				}
			stringBuilderPlayCardStr.append(";");
			stringBuilderPlayCard.append(";");
		}
		stringBuilderPlayCardStr.deleteCharAt(stringBuilderPlayCardStr.length() - 1);
		stringBuilderPlayCard.deleteCharAt(stringBuilderPlayCard.length() - 1);
		for (DezhoupukeUserRecordParams userRecord : gameRecordParams.getUserRecord()) {
			gameRecord = new GameRecordDezhoupukeEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setCoins(userRecord.getCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBigBaseScore(userRecord.getBigBaseScore()).setSmallBaseScore(userRecord.getSmallBaseScore())
					.setPublicCardsInt(publicCard).setPublicCardsStr(stringBuilderPublicCard.toString())
					.setPlayCardsInt(stringBuilderPlayCard.toString()).setPlayCardsStr(stringBuilderPlayCardStr.toString())
					.setRobot(userRecord.getRobot()).setBanker(userRecord.getBanker());

			gameRecord.setGameId(gameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(gameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(gameRecordParams.getRoomId()).setRoomName(roomName)
					.setGradeNumber(gradeNumber)
					.setGameRoundNo(gameRecordParams.getGameRoundNo());

			gameRecordList.add(gameRecord);
		}

		this.insertBatch(gameRecordList);
		
	}
	
	private int getIndex(Object obj) {
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		DezhoupukeGameRecordParams dezhoupukeGameRecordParams = JSON.parseObject(gameRecordParam,
				DezhoupukeGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(dezhoupukeGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveDezhoupukeGameRecord(dezhoupukeGameRecordParams);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	


}
