package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.HongheidazhanBetUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.hongheidazhan.HongheidazhanGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.dao.GameRecordHongheidazhanDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.entity.GameRecordHongheidazhanEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.service.GameRecordHongheidazhanService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordHongheidazhanService")
public class GameRecordHongheidazhanServiceImpl extends ServiceImpl<GameRecordHongheidazhanDao, GameRecordHongheidazhanEntity> 
implements GameRecordHongheidazhanService , IAppGameRecordService{
	
	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(HongheidazhanGameRecordParams hongheidazhanGameRecordParams) {
		List<GameRecordHongheidazhanEntity> gameRecordList = new ArrayList<>();
		GameRecordHongheidazhanEntity gameRecord = null;
		String gameName = gameInfo.getGameName(hongheidazhanGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(hongheidazhanGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(hongheidazhanGameRecordParams.getRoomId());
		String redCard = Joiner.on(",").join(hongheidazhanGameRecordParams.getRedCard());
		String blackCard = Joiner.on(",").join(hongheidazhanGameRecordParams.getBlackCard());
		StringBuilder stringBuilderBlackCard = new StringBuilder();
		StringBuilder stringBuilderRedCard = new StringBuilder();
		StringBuilder stringBuilderbetArea = new StringBuilder();
		// 红方牌型
		for (Integer card : hongheidazhanGameRecordParams.getRedCard()) {
			stringBuilderRedCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderRedCard.append(",");
		}
		stringBuilderRedCard.deleteCharAt(stringBuilderRedCard.length() - 1);
		// 黑方牌型
		for (Integer card : hongheidazhanGameRecordParams.getBlackCard()) {
			stringBuilderBlackCard.append(CardsConstants.CARD_ARRAY[card]);
			stringBuilderBlackCard.append(",");
		}
		stringBuilderBlackCard.deleteCharAt(stringBuilderBlackCard.length() - 1);
		for (HongheidazhanBetUserRecordParams userRecord : hongheidazhanGameRecordParams.getUserRecord()) {
			stringBuilderbetArea = new StringBuilder();
			gameRecord = new GameRecordHongheidazhanEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setRedCard(redCard).setRedCardStr(stringBuilderRedCard.toString())
					.setBlackCard(blackCard).setBlackCardStr(stringBuilderBlackCard.toString())
					.setBetArea(Joiner.on(",").join(betAreaArray)).setRobot(userRecord.getRobot());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilderbetArea.append(CardsConstants.HONGHEIDAZHAN_BET_ARRAY[i]);
				stringBuilderbetArea.append(":");
				stringBuilderbetArea
						.append(betAreaArray.get(i) == null ? null : 
							MathUtil.getBigDecimal(betAreaArray.get(i)).
							divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP));
//							betAreaArray.get(i) / Constant.DB_COIN_RATE);
				stringBuilderbetArea.append(",");
			}
			stringBuilderbetArea.deleteCharAt(stringBuilderbetArea.length() - 1);
			if(hongheidazhanGameRecordParams.getGradeNumber()==null) {
				hongheidazhanGameRecordParams.setGradeNumber(0);
			}
			gameRecord.setBetAreaStr(stringBuilderbetArea.toString());
			gameRecord.setGameId(hongheidazhanGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(hongheidazhanGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(hongheidazhanGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(hongheidazhanGameRecordParams.getGameRoundNo())
					.setGradeNumber(hongheidazhanGameRecordParams.getGradeNumber());
			gameRecordList.add(gameRecord);
			log.info("[GameRecordHongheidazhanEntity] gameRecord {}", gameRecord);
		}
		this.insertBatch(gameRecordList);
		
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		HongheidazhanGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				HongheidazhanGameRecordParams.class);
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
