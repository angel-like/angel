package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaodekuai.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.paodekuai.PaodekuaiGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.paodekuai.PaodekuaiUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaodekuai.dao.GameRecordPaodekuaiDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaodekuai.entity.GameRecordPaodekuaiEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaodekuai.service.GameRecordPaodekuaiService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("gameRecordPaodekuaiService")
public class GameRecordPaodekuaiServiceImpl extends ServiceImpl<GameRecordPaodekuaiDao, GameRecordPaodekuaiEntity> 
implements GameRecordPaodekuaiService , IAppGameRecordService{
	
	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSavePaodekuaiGameRecord(PaodekuaiGameRecordParams gameRecordParams) {
		List<GameRecordPaodekuaiEntity> gameRecordList = new ArrayList<>();
		GameRecordPaodekuaiEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
		Integer gradeNumber = gameRecordParams.getGradeNumber();
		for (PaodekuaiUserRecordParams userRecord : gameRecordParams.getUserRecord()) {
			gameRecord = new GameRecordPaodekuaiEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setCoins(userRecord.getCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBaseScore(userRecord.getBaseScore()).setSurplusCardsNum(userRecord.getSurplusCardsNum())
					.setCoverBombNum(userRecord.getCoverBombNum()).setBombNum(userRecord.getBombNum())
					.setTotalBombNum(userRecord.getTotalBombNum())
					.setCompensateNum(userRecord.getCompensateNum()).setPrizeCoins(userRecord.getPrizeCoins())
					.setRobot(userRecord.getRobot());


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
		PaodekuaiGameRecordParams paodekuaiGameRecordParams = JSON.parseObject(gameRecordParam,
				PaodekuaiGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(paodekuaiGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSavePaodekuaiGameRecord(paodekuaiGameRecordParams);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
