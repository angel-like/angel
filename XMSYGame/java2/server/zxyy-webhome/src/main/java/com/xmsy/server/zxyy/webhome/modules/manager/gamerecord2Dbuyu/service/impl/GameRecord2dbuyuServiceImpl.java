package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.base.Joiner;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.constant.CardsConstants;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.buyu.Buyu2DGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.buyu.Buyu2DUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.dao.GameRecord2dbuyuDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.entity.GameRecord2dbuyuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.service.GameRecord2dbuyuService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecord2dbuyuService")
public class GameRecord2dbuyuServiceImpl extends ServiceImpl<GameRecord2dbuyuDao, GameRecord2dbuyuEntity> 
	implements GameRecord2dbuyuService , IAppGameRecordService{

	@Autowired
	private GameInfoComponent gameInfo;
	
	@Override
	public void appSave2dbuyuGameRecord(Buyu2DGameRecordParams gameRecordParams) {
		List<GameRecord2dbuyuEntity> gameRecordList = new ArrayList<>();
		GameRecord2dbuyuEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
		StringBuilder stringBuilderKillNumber = new StringBuilder();
		for (Buyu2DUserRecordParams userRecord : gameRecordParams.getUserRecord()) {
			List<Integer> killNumberStr = userRecord.getKillNumberStr();
			stringBuilderKillNumber = new StringBuilder();
			gameRecord = new GameRecord2dbuyuEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setCoins(userRecord.getCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBaseScore(userRecord.getBaseScore()).setPrizeCoins(userRecord.getPrizeCoins())
					.setRobot(userRecord.getRobot()).setKillNumberInt(Joiner.on(",").join(killNumberStr));

			for (int i = 0; i < killNumberStr.size(); i++) {
				stringBuilderKillNumber.append(CardsConstants.BUYU2D_KILLNUMBER[i]);
				stringBuilderKillNumber.append(":");
				stringBuilderKillNumber
						.append(killNumberStr.get(i) == null ? null : killNumberStr.get(i));
				stringBuilderKillNumber.append(",");
			}
			stringBuilderKillNumber.deleteCharAt(stringBuilderKillNumber.length() - 1);
			gameRecord.setKillNumberStr(stringBuilderKillNumber.toString());
			gameRecord.setGameId(gameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(gameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(gameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(gameRecordParams.getGameRoundNo());

			gameRecordList.add(gameRecord);
		}

		this.insertBatch(gameRecordList);
		
	}
	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		Buyu2DGameRecordParams Buyu2DGameRecordParams = JSON.parseObject(gameRecordParam,
				Buyu2DGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(Buyu2DGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSave2dbuyuGameRecord(Buyu2DGameRecordParams);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	


}
