package com.xmsy.server.zxyy.manager.modules.manager.gamerecord.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.GameRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.UserCoinParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.result.AppGameParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.result.AppGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.asynchronous.EncouragementService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserCoinSumParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;

/**
 * .游戏记录实现类
 *
 * @author Administrator
 *
 */
@Service("gameRecordService")
public class GameRecordServiceImpl extends ServiceImpl<GameRecordDao, GameRecordEntity> implements GameRecordService {

	@Autowired
	private GameInfoComponent gameInfo;
	@Autowired
	private EncouragementService encouragementService;
	@Override
	public BigDecimal sumValidBet(GameRecordEntity record,String startTime,
								  String endTime) {
		return baseMapper.sumValidBet(record,startTime, endTime);
	}
	@Override
	public Page<GameRecordEntity> getGameRecords(GameRecordEntity record, PageParam pageParam, String startTime,
			String endTime) {
		Page<GameRecordEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getGameRecords(page, record, startTime, endTime));
	}


	@Override
	public List  <GameRecordEntity>  getRecords(GameRecordEntity record) {

		return this.baseMapper.getRecords(record);
	}

	@Override
	public void appSaveGameRecord(GameRecordParams gameRecordParams) {
		GameRecordEntity gameRecord = null;
		String gameName = gameInfo.getGameName(gameRecordParams.getGameId());
		String gradeName = gameInfo.getGradeName(gameRecordParams.getGradeId());
		String roomName = gameInfo.getRoomName(gameRecordParams.getRoomId());
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
		gameRecord.setGameName(gameName);
		gameRecord.setGradeId(gameRecordParams.getGradeId());
		gameRecord.setGradeName(gradeName);
		gameRecord.setRoomId(gameRecordParams.getRoomId());
		gameRecord.setRoomName(roomName);
		gameRecord.setGameRoundNo(gameRecordParams.getGameRoundNo());
		gameRecord.setRound(gameRecordParams.getRound() == null ? 0 : gameRecordParams.getRound());
		gameRecord.setMini(gameRecordParams.getMini() != null ? gameRecordParams.getMini() : false);
		gameRecord.setProfitCoins(gameRecord.getPrizeCoins() * -1);
		this.baseMapper.insert(gameRecord);
		encouragementService.grantEncouragementAsync(gameRecord);
	}

	@Override
	public List<AppGameParams> userGameForRoomId(Long roomId, Long userId) {
		return baseMapper.userGameForRoomId(roomId, userId);
	}

	@Override
	public List<AppGameRecordParams> userGameRecordForGameId(Long gameId, Long userId) {
		// TODO Auto-generated method stub
		return baseMapper.userGameRecordForGameId(gameId, userId);
	}

	@Override
	public BigDecimal sumPrizeCoinsByParam(GameRecordEntity record, String startTime, String endTime) {
		return baseMapper.sumPrizeCoinsByParam(record, startTime, endTime);
	}

	/**
	 * sdfsd
	 */
	@Override
	public Page<GameRecordEntity> queryField(PageParam pageParam, Date countDay, String startTime, String endTime, Long userId, Long gradeId, Long gameId,
			String userAccount) {
		Page<GameRecordEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.queryField(page, countDay,startTime,endTime, userId, gradeId, gameId, userAccount));
	}

	/**
	 * 查询同个局号的用户id和输赢金币
	 *
	 * @return
	 */
	@Override
	public List<GameRecordEntity> queryByGameRoundNo(String gameRoundNo, Integer round) {
		return baseMapper.queryByGameRoundNo(gameRoundNo, round);
	}

	/**
	 * 查询单个用户下注金额和输赢金额
	 * @param user
	 * @return
	 */
	@Override
	public List<UserCoinSumParam> selectCoinSum(UserParamFour user) {
		return baseMapper.selectCoinSum(user);
	}
	/**
	 * 根据会员id统计当前会员   总投入统计 --下注金币 （房卡房间过滤掉）
	 * @param userId
	 * @return
	 */
	@Override
	public BigDecimal totalPutInto(Long userId,String startTime,String endTime) {
		return baseMapper.totalPutInto(userId,startTime,endTime);
	}
	/**
	 * 根据会员id统计当前会员   总产出统计 --中奖金币 （房卡房间过滤掉）
	 * @param userId
	 * @return
	 */
	@Override
	public BigDecimal totalPutOut(Long userId,String startTime,String endTime) {
		return baseMapper.totalPutOut(userId,startTime,endTime);
	}

}
