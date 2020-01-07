package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.service.impl;

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
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.pipeizhajinhua.PipeizhajinhuaGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.pipeizhajinhua.PipeizhajinhuaUserRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.dao.GameRecordPipeizhajinhuaDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.entity.GameRecordPipeizhajinhuaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.service.GameRecordPipeizhajinhuaService;
import com.xmsy.server.zxyy.manager.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordPipeizhajinhuaService")
public class GameRecordPipeizhajinhuaServiceImpl
		extends ServiceImpl<GameRecordPipeizhajinhuaDao, GameRecordPipeizhajinhuaEntity>
		implements GameRecordPipeizhajinhuaService, IAppGameRecordService {

	@Autowired
	private GameInfoComponent gameInfo;

	/**
	 * 保存匹配炸金花游戏记录
	 * 
	 * @param gameRecordParams
	 */
	@Override
	public void appSavePipeizhajinhuaGameRecord(PipeizhajinhuaGameRecordParams gameRecordParams) {
		List<GameRecordPipeizhajinhuaEntity> gameRecordList = new ArrayList<>();
		GameRecordPipeizhajinhuaEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
		for (PipeizhajinhuaUserRecordParams userRecord : gameRecordParams.getUserRecord()) {
			gameRecord = new GameRecordPipeizhajinhuaEntity();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setCoins(userRecord.getCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setBaseScore(userRecord.getScore()).setMultiple(userRecord.getMultiple())
					.setRobot(userRecord.getRobot()).setCardsInt(Joiner.on(",").join(userRecord.getCards()));

			String cartstr = "";
			for (Integer card : userRecord.getCards()) {
				cartstr += "," + CardsConstants.CARD_ARRAY[card];
			}
			gameRecord.setCardsStr(cartstr.replaceFirst(",", ""));

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
		PipeizhajinhuaGameRecordParams pipeizhajinhuaGameRecordParams = JSON.parseObject(gameRecordParam,
				PipeizhajinhuaGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(pipeizhajinhuaGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSavePipeizhajinhuaGameRecord(pipeizhajinhuaGameRecordParams);

	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		return baseMapper.findCardType(gameRoundNo);
	}

}
