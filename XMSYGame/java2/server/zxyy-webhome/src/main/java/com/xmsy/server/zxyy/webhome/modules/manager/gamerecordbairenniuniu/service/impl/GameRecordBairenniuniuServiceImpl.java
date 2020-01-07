package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairenniuniu.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BairenBetUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.niuniu.bairen.BairenNiuNiuGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairenniuniu.dao.GameRecordBairenniuniuDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairenniuniu.entity.GameRecordBairenniuniuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairenniuniu.service.GameRecordBairenniuniuService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordBairenniuniuService")
public class GameRecordBairenniuniuServiceImpl
		extends ServiceImpl<GameRecordBairenniuniuDao, GameRecordBairenniuniuEntity>
		implements GameRecordBairenniuniuService, IAppGameRecordService {

	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(BairenNiuNiuGameRecordParams bairenNiuNiuGameRecordParams) {
		List<GameRecordBairenniuniuEntity> gameRecordList = new ArrayList<>();
		GameRecordBairenniuniuEntity gameRecord = null;
		String gameName = gameInfo.getGameName(bairenNiuNiuGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(bairenNiuNiuGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(bairenNiuNiuGameRecordParams.getRoomId());
		String bankerCard = Joiner.on(",").join(bairenNiuNiuGameRecordParams.getBankerCard());
		StringBuilder stringBuilderIdleCard = new StringBuilder();
		StringBuilder stringBuilderIdleCardStr = new StringBuilder();
		StringBuilder stringBuilderbankerCard = new StringBuilder();
		StringBuilder stringBuilderbetArea = new StringBuilder();
		// 庄牌
		for (Integer card : bairenNiuNiuGameRecordParams.getBankerCard()) {
			stringBuilderbankerCard.append(CardsConstants.getCardStr(card));
			stringBuilderbankerCard.append(",");
		}
		stringBuilderbankerCard.deleteCharAt(stringBuilderbankerCard.length() - 1);
		// 闲牌
		Integer[][] idleCard = bairenNiuNiuGameRecordParams.getIdleCard();
		for (int i = 0; i < idleCard.length; i++) {
			stringBuilderIdleCard.append(Joiner.on(",").join(idleCard[i]));
			stringBuilderIdleCardStr.append(CardsConstants.BAIREN_NIUNIU_ARRAY[i]);
			stringBuilderIdleCardStr.append(":");
			for (Integer card : idleCard[i]) {
				stringBuilderIdleCardStr.append(CardsConstants.getCardStr(card));
				stringBuilderIdleCardStr.append(",");
			}
			stringBuilderIdleCardStr.deleteCharAt(stringBuilderIdleCardStr.length() - 1);
			stringBuilderIdleCardStr.append(";");
			stringBuilderIdleCard.append(";");
		}
		stringBuilderIdleCardStr.deleteCharAt(stringBuilderIdleCardStr.length() - 1);
		stringBuilderIdleCard.deleteCharAt(stringBuilderIdleCard.length() - 1);
		for (BairenBetUserRecordParams userRecord : bairenNiuNiuGameRecordParams.getUserRecord()) {
			stringBuilderbetArea = new StringBuilder();
			gameRecord = new GameRecordBairenniuniuEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBankerCard(bankerCard).setBankerCardStr(stringBuilderbankerCard.toString())
					.setIdleCard(stringBuilderIdleCard.toString()).setIdleCardStr(stringBuilderIdleCardStr.toString())
					.setBetArea(Joiner.on(",").join(betAreaArray)).setRobot(userRecord.getRobot());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilderbetArea.append(CardsConstants.BAIREN_NIUNIU_ARRAY[i]);
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
			gameRecord.setGameId(bairenNiuNiuGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(bairenNiuNiuGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(bairenNiuNiuGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(bairenNiuNiuGameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
			log.info("[GameRecordBairenniuniuEntity] gameRecord {}", gameRecord);
		}
		this.insertBatch(gameRecordList);
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {

		BairenNiuNiuGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				BairenNiuNiuGameRecordParams.class);
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
