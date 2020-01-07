package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordlonghu.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.longhu.LongHuGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordlonghu.dao.GameRecordLonghuDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordlonghu.entity.GameRecordLonghuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordlonghu.service.GameRecordLonghuService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordLonghuService")
public class GameRecordLonghuServiceImpl extends ServiceImpl<GameRecordLonghuDao, GameRecordLonghuEntity>
		implements GameRecordLonghuService, IAppGameRecordService {

	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(LongHuGameRecordParams longHuGameRecordParams) {
		List<GameRecordLonghuEntity> gameRecordList = new ArrayList<>();
		GameRecordLonghuEntity gameRecord = null;
		StringBuilder stringBuilder = new StringBuilder();
		String gameName = gameInfo.getGameName(longHuGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(longHuGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(longHuGameRecordParams.getRoomId());
		for (BairenBetUserRecordParams userRecord : longHuGameRecordParams.getUserRecord()) {
			stringBuilder = new StringBuilder();
			gameRecord = new GameRecordLonghuEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setDragonCard(longHuGameRecordParams.getDragonCard())
					.setDragonCardStr(CardsConstants.CARD_ARRAY[longHuGameRecordParams.getDragonCard()])
					.setOpenCard(longHuGameRecordParams.getOpenCard())
					.setOpenCardStr(CardsConstants.CARD_ARRAY[longHuGameRecordParams.getOpenCard()])
					.setTigerCard(longHuGameRecordParams.getTigerCard())
					.setTigerCardStr(CardsConstants.CARD_ARRAY[longHuGameRecordParams.getTigerCard()])
					.setRobot(userRecord.getRobot());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilder.append(CardsConstants.DRAGON_AND_TIGER_ARRAY[i]);
				stringBuilder.append(":");
				stringBuilder.append(betAreaArray.get(i) == null ? null : 
					MathUtil.getBigDecimal(betAreaArray.get(i)).
					divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP));
//					betAreaArray.get(i) / Constant.DB_COIN_RATE);
				stringBuilder.append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			gameRecord.setBetArea(Joiner.on(",").join(betAreaArray));
			gameRecord.setBetAreaStr(stringBuilder.toString());
			gameRecord.setGameId(longHuGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(longHuGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(longHuGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(longHuGameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
		}
		this.insertBatch(gameRecordList);
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		LongHuGameRecordParams longHuGameRecordParams = JSON.parseObject(gameRecordParam, LongHuGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(longHuGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(longHuGameRecordParams);

	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}

}
