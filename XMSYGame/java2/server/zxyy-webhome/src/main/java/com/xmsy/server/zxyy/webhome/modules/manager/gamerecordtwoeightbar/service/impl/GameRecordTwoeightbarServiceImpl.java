package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwoeightbar.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.twoeightbar.TwoEightBarGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.twoeightbar.TwoEightBarUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwoeightbar.dao.GameRecordTwoeightbarDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwoeightbar.entity.GameRecordTwoeightbarEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwoeightbar.service.GameRecordTwoeightbarService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordTwoeightbarService")
public class GameRecordTwoeightbarServiceImpl extends ServiceImpl<GameRecordTwoeightbarDao, GameRecordTwoeightbarEntity>
		implements GameRecordTwoeightbarService, IAppGameRecordService {
	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(TwoEightBarGameRecordParams gameRecordParams) {
		List<GameRecordTwoeightbarEntity> gameRecordList = new ArrayList<>();
		GameRecordTwoeightbarEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
		for (TwoEightBarUserRecordParams userRecord : gameRecordParams.getUserRecord()) {
			gameRecord = new GameRecordTwoeightbarEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setCoins(userRecord.getCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBaseScore(userRecord.getScore()).setBetMultiple(userRecord.getBetMultiple())
					.setBrandMultiple(userRecord.getBrandMultiple()).setMultiple(userRecord.getMultiple())
					.setRobot(userRecord.getRobot()).setBanker(userRecord.getBanker())
					.setCardsInt(Joiner.on(",").join(userRecord.getCards()));

			String cartstr = "";
			for (Integer card : userRecord.getCards()) {
				cartstr += "," + CardsConstants.TWOEIGHTBAR_CARD_ARRAY[card];
			}
			gameRecord.setCardsStr(cartstr.replaceFirst(",", ""));

			gameRecord.setGameId(gameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(gameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(gameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(gameRecordParams.getGameRoundNo()).setRound(gameRecordParams.getRound());

			gameRecordList.add(gameRecord);
		}

		this.insertBatch(gameRecordList);

	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		TwoEightBarGameRecordParams twoEightBarGameRecordParams = JSON.parseObject(gameRecordParam,
				TwoEightBarGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(twoEightBarGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(twoEightBarGameRecordParams);

	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo, round);
	}

}
