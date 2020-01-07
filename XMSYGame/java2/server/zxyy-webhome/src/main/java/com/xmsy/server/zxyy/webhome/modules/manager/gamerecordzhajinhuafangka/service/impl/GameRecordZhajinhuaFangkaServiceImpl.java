package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordzhajinhuafangka.service.impl;

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
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.zhajinhuafangka.ZhajinhuaFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.zhajinhuafangka.ZhajinhuaFangkaUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordzhajinhuafangka.dao.GameRecordZhajinhuaFangkaDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordzhajinhuafangka.entity.GameRecordZhajinhuaFangkaEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordzhajinhuafangka.service.GameRecordZhajinhuaFangkaService;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("gameRecordZhajinhuaFangkaService")
public class GameRecordZhajinhuaFangkaServiceImpl extends ServiceImpl<GameRecordZhajinhuaFangkaDao, GameRecordZhajinhuaFangkaEntity> 
implements GameRecordZhajinhuaFangkaService ,IAppGameRecordService{

	@Autowired
	private GameInfoComponent gameInfo;
	
	@Override
	public void appSaveZhajinhuaFangkaGameRecord(ZhajinhuaFangkaGameRecordParams gameRecordParams) {
		List<GameRecordZhajinhuaFangkaEntity> gameRecordList=new ArrayList<>();
		GameRecordZhajinhuaFangkaEntity gameRecord=null;
		String gameName=gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName=gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName=gameInfo.getRoomName(gameRecordParams.getRoomId());
		for(ZhajinhuaFangkaUserRecordParams userRecord:gameRecordParams.getUserRecord()) {
			gameRecord=new GameRecordZhajinhuaFangkaEntity();
			gameRecord.setUserId(userRecord.getUserId())
			.setUserAccount(userRecord.getUserAccount())
			.setGameModule(gameRecordParams.getGameModule())
			.setPayType(gameRecordParams.getPayType())
			.setGameType(gameRecordParams.getGameType())
			.setCoins(userRecord.getCoins())
			.setBetCoins(userRecord.getBetCoins())
			.setCoinsBefore(userRecord.getCoinsBefore())
			.setCoinsAfter(userRecord.getCoinsAfter())
			.setBaseScore(userRecord.getScore())
			.setMultiple(userRecord.getMultiple())
			.setRobot(userRecord.getRobot())
			.setCardsInt(Joiner.on(",").join(userRecord.getCards()));
			gameRecord.setRoomNo(gameRecordParams.getRoomNo()==null?"":gameRecordParams.getRoomNo().trim());
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
			.setGameRoundNo(gameRecordParams.getGameRoundNo())
			.setRound(gameRecordParams.getRound());
			
			gameRecordList.add(gameRecord);
		}
		
		this.insertBatch(gameRecordList);
		
	}

	@Override
	public void saveRecord(String gameRecordParam) throws Exception {
		ZhajinhuaFangkaGameRecordParams ZhajinhuaFangkaGameRecordParams = JSON.parseObject(gameRecordParam,
				ZhajinhuaFangkaGameRecordParams.class);
		String errorEesult = EntityValidateUtil.validateModel(ZhajinhuaFangkaGameRecordParams);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			throw new ParamInvalidException(errorEesult);
		}
		appSaveZhajinhuaFangkaGameRecord(ZhajinhuaFangkaGameRecordParams);
		
	}

	@Override
	public List<GameRecordFindCardType> findCardType(String gameRoundNo, Integer round) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
