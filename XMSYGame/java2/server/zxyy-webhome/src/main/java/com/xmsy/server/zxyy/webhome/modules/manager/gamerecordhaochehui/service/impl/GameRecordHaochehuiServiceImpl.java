package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.HaochehuiBetUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.haochehui.HaochehuiGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.dao.GameRecordHaochehuiDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.entity.GameRecordHaochehuiEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.service.GameRecordHaochehuiService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameRecordHaochehuiService")
public class GameRecordHaochehuiServiceImpl extends ServiceImpl<GameRecordHaochehuiDao, GameRecordHaochehuiEntity> 
implements GameRecordHaochehuiService , IAppGameRecordService{
	
	@Autowired
	private GameInfoComponent gameInfo;

	@Override
	public void appSaveGameRecord(HaochehuiGameRecordParams haochehuiGameRecordParams) {
		List<GameRecordHaochehuiEntity> gameRecordList = new ArrayList<>();
		GameRecordHaochehuiEntity gameRecord = null;
		String gameName = gameInfo.getGameName(haochehuiGameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(haochehuiGameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(haochehuiGameRecordParams.getRoomId());
		StringBuilder stringBuilderbetArea = new StringBuilder();
		// 开奖牌型
		String stringBuilderLotteryCard=CardsConstants.HAOCHEHUI_CARD_ARRAY[haochehuiGameRecordParams.getLotteryCard()];
		
		for (HaochehuiBetUserRecordParams userRecord : haochehuiGameRecordParams.getUserRecord()) {
			stringBuilderbetArea = new StringBuilder();
			gameRecord = new GameRecordHaochehuiEntity();
			List<Integer> betAreaArray = userRecord.getBetArea();
			gameRecord.setUserId(userRecord.getUserId()).setUserAccount(userRecord.getUserAccount())
					.setPrizeCoins(userRecord.getPrizeCoins()).setBetCoins(userRecord.getBetCoins())
					.setCoinsBefore(userRecord.getCoinsBefore()).setCoinsAfter(userRecord.getCoinsAfter())
					.setLotteryCard(haochehuiGameRecordParams.getLotteryCard()).setLotteryCardStr(stringBuilderLotteryCard.toString())
					.setBetArea(Joiner.on(",").join(betAreaArray)).setRobot(userRecord.getRobot());
			// 下注区域
			for (int i = 0; i < betAreaArray.size(); i++) {
				stringBuilderbetArea.append(CardsConstants.HAOCHEHUI_CARD_ARRAY[i]);
				stringBuilderbetArea.append(":");
				stringBuilderbetArea
						.append(betAreaArray.get(i) == null ? null :
							MathUtil.getBigDecimal(betAreaArray.get(i)).
							divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP));
//							betAreaArray.get(i) / Constant.DB_COIN_RATE);
				stringBuilderbetArea.append(",");
			}
			stringBuilderbetArea.deleteCharAt(stringBuilderbetArea.length() - 1);
			if(haochehuiGameRecordParams.getGradeNumber()==null) {
				haochehuiGameRecordParams.setGradeNumber(0);
			}
			gameRecord.setBetAreaStr(stringBuilderbetArea.toString());
			gameRecord.setGameId(haochehuiGameRecordParams.getGameId()).setGameName(gameName)
					.setGradeId(haochehuiGameRecordParams.getGradeId()).setGradeName(gradeName)
					.setRoomId(haochehuiGameRecordParams.getRoomId()).setRoomName(roomName)
					.setGameRoundNo(haochehuiGameRecordParams.getGameRoundNo())
					.setGradeNumber(haochehuiGameRecordParams.getGradeNumber());
			gameRecordList.add(gameRecord);
			log.info("[GameRecordBairesangongEntity] gameRecord {}", gameRecord);
		}
		this.insertBatch(gameRecordList);
		
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		HaochehuiGameRecordParams gameRecordParams = JSON.parseObject(gameRecordParam,
				HaochehuiGameRecordParams.class);
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
