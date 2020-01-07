package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.feiqinzoushou.FeiqinzoushouBetUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.feiqinzoushou.FeiqinzoushouGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.dao.GameRecordFeiqinzoushouDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.entity.GameRecordFeiqinzoushouEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.service.GameRecordFeiqinzoushouService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordFeiqinzoushouService")
public class GameRecordFeiqinzoushouServiceImpl extends ServiceImpl<GameRecordFeiqinzoushouDao, GameRecordFeiqinzoushouEntity> 
implements GameRecordFeiqinzoushouService , IAppGameRecordService{
	
	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(FeiqinzoushouGameRecordParams feiqinzoushouGameRecordParams) {
		List<GameRecordFeiqinzoushouEntity> gameRecordList = new ArrayList<>();
		GameRecordFeiqinzoushouEntity gameRecord = null;
		String gameName = gameInfo.getGameName(feiqinzoushouGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(feiqinzoushouGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(feiqinzoushouGameRecordParams.getRoomId());
		StringBuilder stringBuilderbetArea = new StringBuilder();
		// 开奖牌型
		String stringBuilderLotteryCard=CardsConstants.FEIQINZOUSHOU_CARD_ARRAY[feiqinzoushouGameRecordParams.getLotteryCard()];
		
		for (FeiqinzoushouBetUserRecordParams userRecord : feiqinzoushouGameRecordParams.getUserRecord()) {
			stringBuilderbetArea = new StringBuilder();
			gameRecord = new GameRecordFeiqinzoushouEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setLotteryCard(feiqinzoushouGameRecordParams.getLotteryCard()).setLotteryCardStr(stringBuilderLotteryCard.toString())
					.setBetArea(Joiner.on(",").join(betAreaArray)).setRobot(userRecord.getRobot()).setBanker(userRecord.getBanker());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilderbetArea.append(CardsConstants.FEIQINZOUSHOU_CARD_ARRAY[i]);
				stringBuilderbetArea.append(":");
				stringBuilderbetArea
						.append(betAreaArray.get(i) == null ? null : 
							MathUtil.getBigDecimal(betAreaArray.get(i)).
							divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP));
//							betAreaArray.get(i) / Constant.DB_COIN_RATE);
				stringBuilderbetArea.append(",");
			}
			stringBuilderbetArea.deleteCharAt(stringBuilderbetArea.length() - 1);
			if(feiqinzoushouGameRecordParams.getGradeNumber()==null) {
				feiqinzoushouGameRecordParams.setGradeNumber(0);
			}
			gameRecord.setBetAreaStr(stringBuilderbetArea.toString());
			gameRecord.setGameId(feiqinzoushouGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(feiqinzoushouGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(feiqinzoushouGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(feiqinzoushouGameRecordParams.getGameRoundNo())
					.setGradeNumber(feiqinzoushouGameRecordParams.getGradeNumber());
			gameRecordList.add(gameRecord);
			log.info("[GameRecordBairesangongEntity] gameRecord {}", gameRecord);
		}
		this.insertBatch(gameRecordList);
		
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		FeiqinzoushouGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				FeiqinzoushouGameRecordParams.class);
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
