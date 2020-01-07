package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaijiu.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.paijiu.BairenpaijiuGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaijiu.dao.GameRecordPaijiuDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaijiu.entity.GameRecordPaijiuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaijiu.service.GameRecordPaijiuService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordPaijiuService")
public class GameRecordPaijiuServiceImpl extends ServiceImpl<GameRecordPaijiuDao, GameRecordPaijiuEntity>
		implements GameRecordPaijiuService, IAppGameRecordService {

	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		BairenpaijiuGameRecordParams bairenpaijiuGameRecordParams = JSON.parseObject(gameRecordParam,
				BairenpaijiuGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(bairenpaijiuGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(bairenpaijiuGameRecordParams);

	}

	@Override
	public void appSaveGameRecord(BairenpaijiuGameRecordParams bairenpaijiuGameRecordParams) {
		List<GameRecordPaijiuEntity> gameRecordList = new ArrayList<>();
		GameRecordPaijiuEntity gameRecord = null;
		String gameName = gameInfo.getGameName(bairenpaijiuGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(bairenpaijiuGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(bairenpaijiuGameRecordParams.getRoomId());
		String bankerCard = Joiner.on(",").join(bairenpaijiuGameRecordParams.getBankerCard());
		StringBuilder stringBuilderIdleCard = new StringBuilder();
		StringBuilder stringBuilderbankerCard = new StringBuilder();
		StringBuilder stringBuilderIdleCardStrStr = new StringBuilder();
		StringBuilder stringBuilderIdleCardStr = new StringBuilder();
		StringBuilder stringBuilderbetArea = new StringBuilder();
		// 庄牌
		for (Integer card : bairenpaijiuGameRecordParams.getBankerCard()) {
			stringBuilderbankerCard.append(card);
			stringBuilderbankerCard.append("-");
		}
		stringBuilderbankerCard.deleteCharAt(stringBuilderbankerCard.length() - 1);
		// 闲牌
		Integer[][] idleCard = bairenpaijiuGameRecordParams.getIdleCard();
		for (int i = 0; i < idleCard.length; i++) {
			stringBuilderIdleCardStrStr.append(CardsConstants.PAIJIU_IDLE[i]);
			stringBuilderIdleCardStrStr.append(":");
			stringBuilderIdleCardStr = new StringBuilder();
			for (Integer card : idleCard[i]) {
				stringBuilderIdleCard.append(card);
				stringBuilderIdleCard.append(",");
				stringBuilderIdleCardStr.append(card);
				stringBuilderIdleCardStr.append("-");
			}
			stringBuilderIdleCardStr.deleteCharAt(stringBuilderIdleCardStr.length() - 1);
			stringBuilderIdleCardStrStr.append(CardsConstants.PAIJIU_MAP.get(stringBuilderIdleCardStr.toString()));
			stringBuilderIdleCardStrStr.append(";");
		}
		stringBuilderIdleCard.deleteCharAt(stringBuilderIdleCard.length() - 1);
		for (BairenBetUserRecordParams userRecord : bairenpaijiuGameRecordParams.getUserRecord()) {
			stringBuilderbetArea = new StringBuilder();
			gameRecord = new GameRecordPaijiuEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBankerCard(bankerCard)
					.setBankerCardStr(CardsConstants.PAIJIU_MAP.get(stringBuilderbankerCard.toString()))
					.setIdleCard(stringBuilderIdleCard.toString())
					.setIdleCardStr(stringBuilderIdleCardStrStr.toString())
					.setBetArea(Joiner.on(",").join(betAreaArray)).setRobot(userRecord.getRobot());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilderbetArea.append(CardsConstants.PAIJIU_AREA[i]);
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
			gameRecord.setGameId(bairenpaijiuGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(bairenpaijiuGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(bairenpaijiuGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(bairenpaijiuGameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
		}
		this.insertBatch(gameRecordList);

	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}

}
