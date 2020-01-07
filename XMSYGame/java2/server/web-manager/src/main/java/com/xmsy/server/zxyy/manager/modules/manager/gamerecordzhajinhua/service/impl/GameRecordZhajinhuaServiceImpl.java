package com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.service.impl;

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
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.BairenBetUserRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.zhajinhua.BairenZhajinhuaGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.dao.GameRecordZhajinhuaDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.entity.GameRecordZhajinhuaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.service.GameRecordZhajinhuaService;
import com.xmsy.server.zxyy.manager.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordZhajinhuaService")
public class GameRecordZhajinhuaServiceImpl extends ServiceImpl<GameRecordZhajinhuaDao, GameRecordZhajinhuaEntity>
		implements GameRecordZhajinhuaService, IAppGameRecordService {

	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		BairenZhajinhuaGameRecordParams bairenZhajinhuaGameRecordParams = JSON.parseObject(gameRecordParam,
				BairenZhajinhuaGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(bairenZhajinhuaGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(bairenZhajinhuaGameRecordParams);
	}

	@Override
	public void appSaveGameRecord(BairenZhajinhuaGameRecordParams bairenZhajinhuaGameRecordParams) {
		List<GameRecordZhajinhuaEntity> gameRecordList = new ArrayList<>();
		GameRecordZhajinhuaEntity gameRecord = null;
		String gameName = gameInfo.getGameName(bairenZhajinhuaGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(bairenZhajinhuaGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(bairenZhajinhuaGameRecordParams.getRoomId());
		String bankerCard = Joiner.on(",").join(bairenZhajinhuaGameRecordParams.getBankerCard());
		StringBuilder stringBuilderIdleCard = new StringBuilder();
		StringBuilder stringBuilderIdleCardStr = new StringBuilder();
		StringBuilder stringBuilderbankerCard = new StringBuilder();
		StringBuilder stringBuilderbetArea = new StringBuilder();
		// 庄牌
		for (Integer card : bairenZhajinhuaGameRecordParams.getBankerCard()) {
			stringBuilderbankerCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderbankerCard.append(",");
		}
		stringBuilderbankerCard.deleteCharAt(stringBuilderbankerCard.length() - 1);
		// 闲牌
		Integer[][] idleCard = bairenZhajinhuaGameRecordParams.getIdleCard();
		for (int i = 0; i < idleCard.length; i++) {
			stringBuilderIdleCard.append(CardsConstants.FRAUDULENT_FLOWER_ARRAY[i]);
			stringBuilderIdleCard.append(":");
			stringBuilderIdleCardStr.append(CardsConstants.FRAUDULENT_FLOWER_ARRAY[i]);
			stringBuilderIdleCardStr.append(":");
			for (Integer card : idleCard[i]) {
				stringBuilderIdleCard.append(card);
				stringBuilderIdleCard.append(",");
				stringBuilderIdleCardStr.append(CardsConstants.CARD_ARRAY[card]);
				stringBuilderIdleCardStr.append(",");
			}
			stringBuilderIdleCard.append(";");
			stringBuilderIdleCardStr.append(";");
		}
		stringBuilderIdleCard.deleteCharAt(stringBuilderIdleCard.length() - 1);
		stringBuilderIdleCardStr.deleteCharAt(stringBuilderIdleCardStr.length() - 1);
		for (BairenBetUserRecordParams userRecord : bairenZhajinhuaGameRecordParams.getUserRecord()) {
			stringBuilderbetArea = new StringBuilder();
			gameRecord = new GameRecordZhajinhuaEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBankerCard(bankerCard).setBankerCardStr(stringBuilderbankerCard.toString())
					.setIdleCard(stringBuilderIdleCard.toString()).setIdleCardStr(stringBuilderIdleCardStr.toString())
					.setBetArea(Joiner.on(",").join(betAreaArray)).setRobot(userRecord.getRobot());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilderbetArea.append(CardsConstants.FRAUDULENT_FLOWER_ARRAY[i]);
				stringBuilderbetArea.append(":");
				stringBuilderbetArea.append(betAreaArray.get(i));
				stringBuilderbetArea.append(",");
			}
			stringBuilderbetArea.deleteCharAt(stringBuilderbetArea.length() - 1);
			gameRecord.setBetAreaStr(stringBuilderbetArea.toString());
			gameRecord.setGameId(bairenZhajinhuaGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(bairenZhajinhuaGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(bairenZhajinhuaGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(bairenZhajinhuaGameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
			log.info("[GameRecordZhajinhuaEntity] gameRecord {}",gameRecord);
		}
		this.insertBatch(gameRecordList);
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}

}
