package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.service.impl;

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
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.niuniu.tongbi.TongBiNiuNiuGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.niuniu.tongbi.TongBiNiuNiuUserRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.dao.GameRecordCattlTongbiDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.entity.GameRecordCattlTongbiEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.service.GameRecordCattlTongbiService;
import com.xmsy.server.zxyy.manager.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordCattlTongbiService")
public class GameRecordCattlTongbiServiceImpl extends ServiceImpl<GameRecordCattlTongbiDao, GameRecordCattlTongbiEntity> 
implements GameRecordCattlTongbiService , IAppGameRecordService{
	@Autowired
	private GameInfoComponent gameInfo;
	@Override
	public void appSaveTongBiNiuNiuGameRecord(TongBiNiuNiuGameRecordParams gameRecordParams) {
		List<GameRecordCattlTongbiEntity> gameRecordList=new ArrayList<>();
		GameRecordCattlTongbiEntity gameRecord=null;
		String gameName=gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName=gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName=gameInfo.getRoomName(gameRecordParams.getRoomId());
		for(TongBiNiuNiuUserRecordParams userRecord:gameRecordParams.getUserRecord()) {
			gameRecord=new GameRecordCattlTongbiEntity();
			gameRecord.setUserId(userRecord.getUserId())
			.setUserAccount(userRecord.getUserAccount())
			.setCoins(userRecord.getCoins())
			.setBetCoins(userRecord.getBetCoins())
			.setCoinsBefore(userRecord.getCoinsBefore())
			.setCoinsAfter(userRecord.getCoinsAfter())
			.setBaseScore(userRecord.getScore())
			.setBetMultiple(userRecord.getBetMultiple())
			.setBrandMultiple(userRecord.getBrandMultiple())
			.setMultiple(userRecord.getMultiple())
			.setRobot(userRecord.getRobot())
			.setCardsInt(Joiner.on(",").join(userRecord.getCards()));
			
			String cartstr = "";
			for (Integer card : userRecord.getCards()) {
				cartstr += "," + CardsConstants.CARD_ARRAY[card];
			}
			gameRecord.setCardsStr(cartstr.replaceFirst(",", ""));
			
			gameRecord.setGameId(gameRecordParams.getGameId())
			.setGameName(gameName)
			.setGradeId(gameRecordParams.getGradeId())
			.setGradeName(gradeName)
			.setRoomId(gameRecordParams.getRoomId())
			.setRoomName(roomName)
			.setGameRoundNo(gameRecordParams.getGameRoundNo());
			
			gameRecordList.add(gameRecord);
		}
		
		this.insertBatch(gameRecordList);
	}
	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		TongBiNiuNiuGameRecordParams tongBiNiuNiuGameRecordParams = JSON.parseObject(gameRecordParam,
				TongBiNiuNiuGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(tongBiNiuNiuGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveTongBiNiuNiuGameRecord(tongBiNiuNiuGameRecordParams);
		
	}
	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}


}
