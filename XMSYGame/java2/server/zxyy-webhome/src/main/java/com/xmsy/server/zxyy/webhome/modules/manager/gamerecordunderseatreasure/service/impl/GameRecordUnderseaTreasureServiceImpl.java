package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordunderseatreasure.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.LabaBetUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.laba.UnderseaTreasureGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordunderseatreasure.dao.GameRecordUnderseaTreasureDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordunderseatreasure.entity.GameRecordUnderseaTreasureEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordunderseatreasure.service.GameRecordUnderseaTreasureService;
import com.xmsy.server.zxyy.webhome.utils.ByteUtil;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordUnderseaTreasureService")
public class GameRecordUnderseaTreasureServiceImpl
		extends ServiceImpl<GameRecordUnderseaTreasureDao, GameRecordUnderseaTreasureEntity>
		implements GameRecordUnderseaTreasureService, IAppGameRecordService {

	public static final String CONTENT = "中奖线:%s, 中奖线图标: %s, 中奖图标数量: %s";

	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		UnderseaTreasureGameRecordParams underseaTreasureGameRecordParams = JSON.parseObject(gameRecordParam,
				UnderseaTreasureGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(underseaTreasureGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveGameRecord(underseaTreasureGameRecordParams);

	}

	@Override
	public void appSaveGameRecord(UnderseaTreasureGameRecordParams gameRecordParams) {
		List<GameRecordUnderseaTreasureEntity> gameRecordList = new ArrayList<>();
		GameRecordUnderseaTreasureEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
		String[] lines = null;
		String errorEesult = "";
		for (LabaBetUserRecordParams userRecord : gameRecordParams.getUserRecord()) {
			errorEesult = EntityValidateUtil.validateModel(userRecord);
			if (StringUtils.isNotEmpty(errorEesult)) {
				log.error("参数校验失败：{}", errorEesult);
				throw new ParamInvalidException(errorEesult);
			}
			gameRecord = new GameRecordUnderseaTreasureEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setBetCoins(userRecord.getBetCoins()).setPrizeCoins(userRecord.getPrizeCoins())
					.setBetLines(userRecord.getBetLines()).setPow(userRecord.getPow())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setMiniGameMultiple(userRecord.getMiniGameMultiple()==null?0:userRecord.getMiniGameMultiple())
					.setMiniGameRewards(userRecord.getMiniGameRewards()==null?0:userRecord.getMiniGameRewards())
					.setBcoins(userRecord.getBcoins()).setRobot(userRecord.getRobot())
					.setMiniGame(userRecord.getMiniGame()==null?false:userRecord.getMiniGame());
			gameRecord.setScenes(ByteUtil.byteArrayToString(Base64.decode(userRecord.getScenes())));
			lines = userRecord.getLines();
			String line = "";
			StringBuilder linesBuilder = new StringBuilder();
			for (int i = 0; i < lines.length; i++) {
				if (null == lines[i]) {
					continue;
				}
				line = ByteUtil.byteArrayToString(Base64.decode(lines[i]));
				line = String.format(CONTENT, i + 1, line, line.charAt(line.length() - 1));
				linesBuilder.append(line);
				linesBuilder.append(";");
			}
			gameRecord.setLines(linesBuilder.toString());
			gameRecord.setGameId(gameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(gameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(gameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(gameRecordParams.getGameRoundNo());
			gameRecordList.add(gameRecord);
		}
		this.insertBatch(gameRecordList);

	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
