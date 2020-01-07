package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.robot.common.utils.MathUtil;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordParams;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.UserCoinParams;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.service.GameRecordService;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("gameRecordService")
public class GameRecordServiceImpl<E> extends ServiceImpl<GameRecordDao, GameRecordEntity> implements GameRecordService {
	
	@Override
	public void appSaveGameRecord(GameRecordParams gameRecordParams) {
		GameRecordEntity gameRecord = null;
		if(gameRecordParams.getRoomId()==3) {
			return;
		}
		UserCoinParams userCoin = gameRecordParams.getUserCoin().get(0);
		gameRecord = new GameRecordEntity();
		gameRecord.setUserId(userCoin.getUserId());
		gameRecord.setUserAccount(userCoin.getUserAccount());
		gameRecord.setBetCoins(userCoin.getBetCoins());
		gameRecord.setValidBet(userCoin.getValidBet());
		gameRecord.setPrizeCoins(userCoin.getPrizeCoins());
		gameRecord.setRobot(userCoin.getRobot());
		gameRecord.setWaterProfit(MathUtil.getBigDecimal(userCoin.getWaterProfit()));
		gameRecord.setWaterRate(MathUtil.getBigDecimal(userCoin.getWaterRate()));
		gameRecord.setGameId(gameRecordParams.getGameId());
		gameRecord.setGradeId(gameRecordParams.getGradeId());
		gameRecord.setRoomId(gameRecordParams.getRoomId());
		gameRecord.setGameRoundNo(gameRecordParams.getGameRoundNo());
		gameRecord.setRound(gameRecordParams.getRound()==null?0:gameRecordParams.getRound());
		gameRecord.setMini(gameRecordParams.getMini() != null ? gameRecordParams.getMini() : false);
		gameRecord.setProfitCoins(gameRecord.getPrizeCoins() * -1);
		this.baseMapper.insert(gameRecord);

	}

	@Override
	public Long sumCountProfit(GameRecordEntity gamerecord) {
		// TODO Auto-generated method stub
		log.info("[sumCountProfit] gamerecord {}", gamerecord);
		return baseMapper.sumCountProfit(gamerecord);
	}

	@Override
	public void deleteGameRecord(String date) {
		log.info("[deleteGameRecord] 开始删除昨日以前游戏记录 {}", DateUtil.now());
		//删除两天前游戏记录
		baseMapper.deleteGameRecord(date);
		
	}
	
	//获取指定房间的每款游戏每个场次盈利总额
	@Override
	public Long sumProfitForGame(List<Long> gameIds, String date) {
		return baseMapper.sumProfitForGame(gameIds, date);
	}

	@Override
	public void deleteGameRecord(int days) {
		
		baseMapper.deleteGameRecordByDays(days);
	}

}
