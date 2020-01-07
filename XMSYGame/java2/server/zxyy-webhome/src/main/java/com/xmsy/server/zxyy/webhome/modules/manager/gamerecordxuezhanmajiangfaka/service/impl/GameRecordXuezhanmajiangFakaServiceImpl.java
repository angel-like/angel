package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.xuezhanmajiang.fangka.XuezhanmajiangFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.xuezhanmajiang.fangka.XuezhanmajiangFangkaUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.dao.GameRecordXuezhanmajiangFakaDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.entity.GameRecordXuezhanmajiangFakaEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.service.GameRecordXuezhanmajiangFakaService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("gameRecordXuezhanmajiangFakaService")
public class GameRecordXuezhanmajiangFakaServiceImpl extends ServiceImpl<GameRecordXuezhanmajiangFakaDao, GameRecordXuezhanmajiangFakaEntity> 
implements GameRecordXuezhanmajiangFakaService , IAppGameRecordService{

	@Autowired
	private GameInfoComponent gameInfo;
	
	@Override
	public void appSaveXuezhanmajianFangkaGameRecord(XuezhanmajiangFangkaGameRecordParams gameRecordParams) {
		List<GameRecordXuezhanmajiangFakaEntity> gameRecordList = new ArrayList<>();
		GameRecordXuezhanmajiangFakaEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
		for (XuezhanmajiangFangkaUserRecordParams userRecord : gameRecordParams.getUserRecord()) {
			gameRecord = new GameRecordXuezhanmajiangFakaEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setCoins(userRecord.getCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBaseScore(userRecord.getBaseScore()).setCardMultiple(userRecord.getCardMultiple())
					.setSettlementCard(userRecord.getSettlementCard())
					.setGameModule(gameRecordParams.getGameModule())
					.setPayType(gameRecordParams.getPayType())
					.setGameType(gameRecordParams.getGameType())
					.setRoomNo(gameRecordParams.getRoomNo()==null?"":gameRecordParams.getRoomNo().trim())
					.setBanker(userRecord.getBanker());

			gameRecord.setGameId(gameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(gameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(gameRecordParams.getRoomId()).setRoomName(roomName)
					.setRound(gameRecordParams.getRound())
					.setGameRoundNo(gameRecordParams.getGameRoundNo());

			gameRecordList.add(gameRecord);
		}

		this.insertBatch(gameRecordList);
		
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		log.info("gameRecordParam：{}", gameRecordParam);
		XuezhanmajiangFangkaGameRecordParams xuezhanmajiangFangkaGameRecordParams = JSON.parseObject(gameRecordParam,
				XuezhanmajiangFangkaGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(xuezhanmajiangFangkaGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveXuezhanmajianFangkaGameRecord(xuezhanmajiangFangkaGameRecordParams);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	


}
