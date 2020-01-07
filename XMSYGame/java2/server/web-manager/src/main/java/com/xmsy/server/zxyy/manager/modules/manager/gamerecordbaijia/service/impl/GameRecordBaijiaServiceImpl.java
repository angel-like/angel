package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbaijia.service.impl;

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
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.baijia.BaijiaLeGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.BairenBetUserRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbaijia.dao.GameRecordBaijiaDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbaijia.entity.GameRecordBaijiaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbaijia.service.GameRecordBaijiaService;
import com.xmsy.server.zxyy.manager.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordBaijiaService")
public class GameRecordBaijiaServiceImpl extends ServiceImpl<GameRecordBaijiaDao, GameRecordBaijiaEntity>
		implements GameRecordBaijiaService, IAppGameRecordService {

	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(BaijiaLeGameRecordParams baijiaLeGameRecordParams) {
		List<GameRecordBaijiaEntity> gameRecordList = new ArrayList<>();
		GameRecordBaijiaEntity gameRecord = null;
		String gameName = gameInfo.getGameName(baijiaLeGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(baijiaLeGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(baijiaLeGameRecordParams.getRoomId());
		String bankerCard = Joiner.on(",").join(baijiaLeGameRecordParams.getBankerCard());
		StringBuilder stringBuilderIdleCard = new StringBuilder();
		StringBuilder stringBuilderbankerCard = new StringBuilder();
		StringBuilder stringBuilderbetArea = new StringBuilder();
		// 庄牌
		for (Integer card : baijiaLeGameRecordParams.getBankerCard()) {
			stringBuilderbankerCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderbankerCard.append(",");
		}
		stringBuilderbankerCard.deleteCharAt(stringBuilderbankerCard.length() - 1);
		// 闲牌
		String idleCard = Joiner.on(",").join(baijiaLeGameRecordParams.getIdleCard());
		for (Integer card : baijiaLeGameRecordParams.getIdleCard()) {
			stringBuilderIdleCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderIdleCard.append(",");
		}
		stringBuilderIdleCard.deleteCharAt(stringBuilderIdleCard.length() - 1);
		for (BairenBetUserRecordParams userRecord : baijiaLeGameRecordParams.getUserRecord()) {
			stringBuilderbetArea = new StringBuilder();
			gameRecord = new GameRecordBaijiaEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBankerCard(bankerCard).setBankerCardStr(stringBuilderbankerCard.toString())
					.setIdleCard(idleCard).setIdleCardStr(stringBuilderIdleCard.toString())
					.setBetArea(Joiner.on(",").join(betAreaArray)).setRobot(userRecord.getRobot());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilderbetArea.append(CardsConstants.BACCARAT_ARRAY[i]);
				stringBuilderbetArea.append(":");
				stringBuilderbetArea.append(betAreaArray.get(i));
				stringBuilderbetArea.append(",");
			}
			stringBuilderbetArea.deleteCharAt(stringBuilderbetArea.length() - 1);
			gameRecord.setBetAreaStr(stringBuilderbetArea.toString());
			gameRecord.setGameId(baijiaLeGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(baijiaLeGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(baijiaLeGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(baijiaLeGameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
		}
		this.insertBatch(gameRecordList);
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		BaijiaLeGameRecordParams baijiaLeGameRecordParams = JSON.parseObject(gameRecordParam,
				BaijiaLeGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(baijiaLeGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(baijiaLeGameRecordParams);
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}

}
