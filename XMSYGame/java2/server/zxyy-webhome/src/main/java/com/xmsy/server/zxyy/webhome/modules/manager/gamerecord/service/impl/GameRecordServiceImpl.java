package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.bean.message.FortuneMessage;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.AppFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.GameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.UserCoinParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result.AppGameParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result.AppGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.asynchronous.CountGameNumberService;
import com.xmsy.server.zxyy.webhome.modules.manager.asynchronous.EncouragementService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.service.UserBetRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.service.UserPointKillService;
import com.xmsy.server.zxyy.webhome.mq.MqClient;

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
	@Autowired
	private CountGameNumberService countGameNumberService;
	@Autowired
	private UserBetRecordService userBetRecordService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserPointKillService userPointKillService;
	@Autowired
	private MqClient mqClient;
	

	@Override
	public Page<GameRecordEntity> getGameRecords(GameRecordEntity record, PageParam pageParam, String startTime,
			String endTime) {
		Page<GameRecordEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getGameRecords(page, record, startTime, endTime));
	}

	@Override
	public void appSaveGameRecord(GameRecordParams gameRecordParams) {
		GameRecordEntity gameRecord = null;
		String queryDate = DateUtils.format(new Date());
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
		gameRecord.setWaterProfit(MathUtil.getBigDecimal(userCoin.getWaterProfit())
				.compareTo(new BigDecimal(1))<0 ? BigDecimal.ZERO:MathUtil.getBigDecimal(userCoin.getWaterProfit()));
		gameRecord.setWaterRate(MathUtil.getBigDecimal(userCoin.getWaterRate()));
		gameRecord.setGameId(gameRecordParams.getGameId());
		gameRecord.setGameName(gameName);
		gameRecord.setGradeId(gameRecordParams.getGradeId());
		gameRecord.setGradeName(gradeName);
		gameRecord.setRoomId(gameRecordParams.getRoomId());
		gameRecord.setRoomName(roomName);
		gameRecord.setGameRoundNo(gameRecordParams.getGameRoundNo());
		gameRecord.setRound(gameRecordParams.getRound() == null ? 0 : gameRecordParams.getRound());
		gameRecord.setRoomNo(gameRecordParams.getRoomNo()==null?"":gameRecordParams.getRoomNo().trim());
		gameRecord.setMini(gameRecordParams.getMini() != null ? gameRecordParams.getMini() : false);
		gameRecord.setProfitCoins(gameRecord.getPrizeCoins() * -1);
		boolean isGmUser = userService.isGmUser(userCoin.getUserId());
		gameRecord.setGmUser(isGmUser);
		this.baseMapper.insert(gameRecord);
		if(!gameRecord.getRoomId().equals(2l)) {
			userBetRecordService.updateUserEveryDateBet(userCoin.getUserId(), userCoin.getUserAccount(),
					userCoin.getValidBet(), queryDate,isGmUser);
			//点杀名单计算方法
			userPointKillService.countUserPointKill(gameRecord);
			//天降财神打码得红包
			FortuneMessage fortuneMessage=new FortuneMessage();
			fortuneMessage.setUserId(userCoin.getUserId());
			fortuneMessage.setValidBet(userCoin.getValidBet());
			fortuneMessage.setEventCode("coin-0");//0表示打码的事件 
			mqClient.fortunePush(fortuneMessage);
		}
		
		//赠送鼓励金异步方法
		encouragementService.grantEncouragementAsync(gameRecord);
		//计算游戏测试异步方法
		countGameNumberService.countUserGameNumAsync(gameRecord);
		
	}



	@Override
	public List<AppGameParams> userGameForRoomId(Long roomId, Long userId) {
		return baseMapper.userGameForRoomId(roomId, userId);
	}

	@Override
	public List<AppGameRecordParams> userGameRecordForGameId(Long gameId, Long userId) {
		if(gameId==SysConstant.GAME_IDE_10) {
			return baseMapper.userBjlGameRecordForGameId(gameId, userId);
		}
		return baseMapper.userGameRecordForGameId(gameId, userId);
	}

	@Override
	public BigDecimal sumPrizeCoinsByParam(GameRecordEntity record, String startTime, String endTime) {
		return baseMapper.sumPrizeCoinsByParam(record, startTime, endTime);
	}

	@Override
	public Page<GameRecordEntity> queryField(PageParam pageParam, Date countDay, Long userId, Long gradeId, Long gameId,
			String userAccount) {
		Page<GameRecordEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.queryField(page, countDay, userId, gradeId, gameId, userAccount));
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

	@Override
	public List<AppGameRecordParams> userGameRecordForFangka(Long userId,Long gameId) {
		return baseMapper.getGameRecordsByUserIdForFangka(userId, SysConstant.ROOM_ID_2,gameId);
	}

	@Override
	public Page<AppGameRecordParams> getGameRecordsByRoomNoForFangka(PageParam pageParam,
			AppFangkaGameRecordParams param) {
		Page<AppGameRecordParams> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getGameRecordsByRoomNoForFangka(page, param));
	}
	/**
	 * 富豪任务（打码任务）
	 * @param user
	 * @param playerTask
	 * @param cycle
	 * @return
	 */
	@Override
	public PlayerTasksEntity validBetTask(UserEntity user, PlayerTasksEntity playerTask, int cycle) {
		//1 查询在  周期时间内（今天内） +游戏id+房间id 会员打码量
		Long gameId=playerTask.getGameId()==0?null:playerTask.getGameId();
		Long roomId=playerTask.getRoomId()==0?null:playerTask.getRoomId();
		Long userValidBetLong = this.baseMapper.getUserValidBetByGameId(user.getId(),
				DateUtils.formatTime(DateUtils.addDayZeroPoint(new Date(),cycle)), DateUtils.formatTime(new Date()),
				playerTask.getGradeId(),gameId,roomId);
		Integer userValidBet=userValidBetLong.intValue();
		//2  通过  会员打码量-已领取次数*条件    >=条件  判断是否完成任务
		if ((userValidBet - playerTask.getReceiveTaskNum() * playerTask.getCondition()) >= playerTask.getCondition()) {
			playerTask.setWhetherComplete(true);
			if(playerTask.getReceiveTaskNum()<playerTask.getTaskNum()) {//领取次数<任务设置次数 
				Integer unTaskNumTotal = (userValidBet - playerTask.getReceiveTaskNum() * playerTask.getCondition())
						/ playerTask.getCondition();
				Integer unTaskNum=unTaskNumTotal + playerTask.getReceiveTaskNum() > playerTask.getTaskNum()
						? playerTask.getTaskNum() - playerTask.getReceiveTaskNum()
						: unTaskNumTotal;
				user.setUnTaskNum(user.getUnTaskNum()+unTaskNum);//已完成任务数+1  红点
			}
		}else {
			playerTask.setWhetherComplete(false);
		}
		//3  把当前会员打码量设置进去
		playerTask.setCompleteNum(userValidBet);
		return playerTask;
	}

}
