package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.xuezhanmajiang.XuezhanmajiangGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.xuezhanmajiang.XuezhanmajiangUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.dao.GameRecordXuezhanmajiangDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.entity.GameRecordXuezhanmajiangEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.service.GameRecordXuezhanmajiangService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("gameRecordXuezhanmajiangService")
public class GameRecordXuezhanmajiangServiceImpl extends ServiceImpl<GameRecordXuezhanmajiangDao, GameRecordXuezhanmajiangEntity> 
implements GameRecordXuezhanmajiangService , IAppGameRecordService{
	@Autowired
	private GameInfoComponent gameInfo;
	
	@Override
	public void appSaveXuezhanmajianGameRecord(XuezhanmajiangGameRecordParams gameRecordParams) {
		List<GameRecordXuezhanmajiangEntity> gameRecordList = new ArrayList<>();
		GameRecordXuezhanmajiangEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
		Integer gradeNumber = gameRecordParams.getGradeNumber();
		for (XuezhanmajiangUserRecordParams userRecord : gameRecordParams.getUserRecord()) {
			gameRecord = new GameRecordXuezhanmajiangEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setCoins(userRecord.getCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBaseScore(userRecord.getBaseScore()).setCardMultiple(userRecord.getCardMultiple())
					.setSettlementCard(userRecord.getSettlementCard())
					.setRobot(userRecord.getRobot()).setBanker(userRecord.getBanker())
					.setDefeat(userRecord.getDefeat());

			gameRecord.setGameId(gameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(gameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(gameRecordParams.getRoomId()).setRoomName(roomName)
					.setGradeNumber(gradeNumber)
					.setGameRoundNo(gameRecordParams.getGameRoundNo());

			gameRecordList.add(gameRecord);
		}

		this.insertBatch(gameRecordList);
		
	}
	

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		log.info("gameRecordParam：{}", gameRecordParam);
		XuezhanmajiangGameRecordParams xuezhanmajiangGameRecordParams = JSON.parseObject(gameRecordParam,
				XuezhanmajiangGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(xuezhanmajiangGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveXuezhanmajianGameRecord(xuezhanmajiangGameRecordParams);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
