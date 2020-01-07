package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairenjingdianniuniu.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.base.Joiner;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.constant.CardsConstants;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BairenjingdianBetUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.niuniu.bairenjingdain.BairenjingdianNiuNiuGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairenjingdianniuniu.dao.GameRecordBairenjingdianniuniuDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairenjingdianniuniu.entity.GameRecordBairenjingdianniuniuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairenjingdianniuniu.service.GameRecordBairenjingdianniuniuService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordBairenjingdianniuniuService")
public class GameRecordBairenjingdianniuniuServiceImpl
		extends ServiceImpl<GameRecordBairenjingdianniuniuDao, GameRecordBairenjingdianniuniuEntity>
		implements GameRecordBairenjingdianniuniuService, IAppGameRecordService {

	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(BairenjingdianNiuNiuGameRecordParams bairenjingdianNiuNiuGameRecordParams) {
		List<GameRecordBairenjingdianniuniuEntity> gameRecordList = new ArrayList<>();
		GameRecordBairenjingdianniuniuEntity gameRecord = null;
		String gameName = gameInfo.getGameName(bairenjingdianNiuNiuGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(bairenjingdianNiuNiuGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(bairenjingdianNiuNiuGameRecordParams.getRoomId());
		String bankerCard = Joiner.on(",").join(bairenjingdianNiuNiuGameRecordParams.getBankerCard());
		StringBuilder stringBuilderIdleCard = new StringBuilder();
		StringBuilder stringBuilderIdleCardStr = new StringBuilder();
		StringBuilder stringBuilderbankerCard = new StringBuilder();
		StringBuilder stringBuilderbetArea = new StringBuilder();
		// 庄牌
		for (Integer card : bairenjingdianNiuNiuGameRecordParams.getBankerCard()) {
			stringBuilderbankerCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderbankerCard.append(",");
		}
		stringBuilderbankerCard.deleteCharAt(stringBuilderbankerCard.length() - 1);
		// 闲牌
		Integer[][] idleCard = bairenjingdianNiuNiuGameRecordParams.getIdleCard();
		for (int i = 0; i < idleCard.length; i++) {
			stringBuilderIdleCard.append(Joiner.on(",").join(idleCard[i]));
			stringBuilderIdleCardStr.append(CardsConstants.BAIREN_JINGDIAN_NIUNIU_ARRAY[i]);
			stringBuilderIdleCardStr.append(":");
			for (Integer card : idleCard[i]) {
				stringBuilderIdleCardStr.append(CardsConstants.CARD_ARRAY[card]);
				stringBuilderIdleCardStr.append(",");
			}
			stringBuilderIdleCardStr.deleteCharAt(stringBuilderIdleCardStr.length() - 1);
			stringBuilderIdleCardStr.append(";");
			stringBuilderIdleCard.append(";");
		}
		stringBuilderIdleCardStr.deleteCharAt(stringBuilderIdleCardStr.length() - 1);
		stringBuilderIdleCard.deleteCharAt(stringBuilderIdleCard.length() - 1);
		for (BairenjingdianBetUserRecordParams userRecord : bairenjingdianNiuNiuGameRecordParams.getUserRecord()) {
			stringBuilderbetArea = new StringBuilder();
			gameRecord = new GameRecordBairenjingdianniuniuEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBankerCard(bankerCard).setBankerCardStr(stringBuilderbankerCard.toString())
					.setIdleCard(stringBuilderIdleCard.toString()).setIdleCardStr(stringBuilderIdleCardStr.toString())
					.setBetArea(Joiner.on(",").join(betAreaArray)).setRobot(userRecord.getRobot());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilderbetArea.append(CardsConstants.BAIREN_JINGDIAN_NIUNIU_ARRAY[i]);
				stringBuilderbetArea.append(":");
				stringBuilderbetArea
						.append(betAreaArray.get(i) == null ? null : 
							MathUtil.getBigDecimal(betAreaArray.get(i)).
							divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP));
//							betAreaArray.get(i) / Constant.DB_COIN_RATE);
				stringBuilderbetArea.append(",");
			}
			stringBuilderbetArea.deleteCharAt(stringBuilderbetArea.length() - 1);
			gameRecord.setBetAreaStr(stringBuilderbetArea.toString());
			gameRecord.setGameId(bairenjingdianNiuNiuGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(bairenjingdianNiuNiuGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(bairenjingdianNiuNiuGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(bairenjingdianNiuNiuGameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
			log.info("[BairenJingdianNiuNiuGameRecordEntity] gameRecord {}", gameRecord);
		}
		this.insertBatch(gameRecordList);

	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {

		BairenjingdianNiuNiuGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				BairenjingdianNiuNiuGameRecordParams.class);
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
