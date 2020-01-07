package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.sangong.qiangzhuang.SangongQiangzhuangGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.sangong.qiangzhuang.SangongQiangzhuangUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.dao.GameRecordSangongQiangzhuangDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.entity.GameRecordSangongQiangzhuangEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.service.GameRecordSangongQiangzhuangService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordSangongQiangzhuangService")
public class GameRecordSangongQiangzhuangServiceImpl extends ServiceImpl<GameRecordSangongQiangzhuangDao, GameRecordSangongQiangzhuangEntity> 
implements GameRecordSangongQiangzhuangService , IAppGameRecordService{
	
	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(SangongQiangzhuangGameRecordParams sangongQiangzhuangGameRecordParams) {
		List<GameRecordSangongQiangzhuangEntity> gameRecordList = new ArrayList<>();
		GameRecordSangongQiangzhuangEntity gameRecord = null;
		String gameName = gameInfo.getGameName(sangongQiangzhuangGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(sangongQiangzhuangGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(sangongQiangzhuangGameRecordParams.getRoomId());
		String playerCard = Joiner.on(",").join(sangongQiangzhuangGameRecordParams.getPlayerCard());
		Integer gradeNumber = sangongQiangzhuangGameRecordParams.getGradeNumber();
		StringBuilder stringBuilderSettlementCard = new StringBuilder();
		StringBuilder stringBuilderSettlementCardStr = new StringBuilder();
		StringBuilder stringBuilderPlayerCard = new StringBuilder();
		// 玩家
		for (Integer card : sangongQiangzhuangGameRecordParams.getPlayerCard()) {
			stringBuilderPlayerCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderPlayerCard.append(",");
		}
		stringBuilderPlayerCard.deleteCharAt(stringBuilderPlayerCard.length() - 1);
		// 结算牌型
		Integer[][] settlementCard = sangongQiangzhuangGameRecordParams.getSettlementCard();
		for (int i = 0; i < settlementCard.length; i++) {
			stringBuilderSettlementCard.append(Joiner.on(",").join(settlementCard[i]));
			stringBuilderSettlementCardStr.append(CardsConstants.QINGZHUANG_SANGONG_ARRAY[i]);
			stringBuilderSettlementCardStr.append(":");
			for (Integer card : settlementCard[i]) {
				stringBuilderSettlementCardStr.append(CardsConstants.CARD_ARRAY[card]);
				stringBuilderSettlementCardStr.append(",");
			}
			stringBuilderSettlementCardStr.deleteCharAt(stringBuilderSettlementCardStr.length() - 1);
			stringBuilderSettlementCardStr.append(";");
			stringBuilderSettlementCard.append(";");
		}
		stringBuilderSettlementCardStr.deleteCharAt(stringBuilderSettlementCardStr.length() - 1);
		stringBuilderSettlementCard.deleteCharAt(stringBuilderSettlementCard.length() - 1);
		for (SangongQiangzhuangUserRecordParams userRecord : sangongQiangzhuangGameRecordParams.getUserRecord()) {
			gameRecord = new GameRecordSangongQiangzhuangEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setCoins(userRecord.getCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBaseScore(userRecord.getBaseScore()).setBetMultiple(userRecord.getBetMultiple())
					.setBankerMultiple(userRecord.getBankerMultiple()).setPrizeCoins(userRecord.getPrizeCoins())
					.setRobot(userRecord.getRobot()).setBanker(userRecord.getBanker())
					.setSettlementCardStr(stringBuilderSettlementCardStr.toString())
					.setSettlementCard(stringBuilderSettlementCard.toString())
					.setPlayerCard(playerCard).setPlayerCardStr(stringBuilderPlayerCard.toString());

			gameRecord.setGameId(sangongQiangzhuangGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(sangongQiangzhuangGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(sangongQiangzhuangGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGradeNumber(gradeNumber)
					.setGameRoundNo(sangongQiangzhuangGameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
		}

		this.insertBatch(gameRecordList);
		
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		SangongQiangzhuangGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				SangongQiangzhuangGameRecordParams.class);
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
