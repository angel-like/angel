package com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.dao.UserGameNumberDao;
import com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.entity.UserGameNumberEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.service.UserGameNumberService;

@Transactional
@Service("userGameNumberService")
public class UserGameNumberServiceImpl extends ServiceImpl<UserGameNumberDao, UserGameNumberEntity> implements UserGameNumberService {
	@Autowired
	private GameRecordDao gameRecordDao;
	@Override
	public void saveUserGameNumber(GameRecordEntity gameRecord) {
		UserGameNumberEntity userGameNumber = new UserGameNumberEntity();
		userGameNumber.setGameId(gameRecord.getGameId());
		userGameNumber.setRoomId(gameRecord.getRoomId());
		userGameNumber.setUserId(gameRecord.getUserId());
		userGameNumber.setCountDate(DateUtils.addDayZeroPoint(new Date(), 0));
		int num = this.selectCount(new EntityWrapper<UserGameNumberEntity>(userGameNumber));	
		if (gameRecord.getPrizeCoins().compareTo(0l) < 0) {
			userGameNumber.setLoseNum(1);
			userGameNumber.setWinNum(0);
		} else if (gameRecord.getPrizeCoins().compareTo(0l) > 0){
			userGameNumber.setLoseNum(0);
			userGameNumber.setWinNum(1);
		}else {
			userGameNumber.setLoseNum(0);
			userGameNumber.setWinNum(0);
		}
		userGameNumber.setGameNum(1);
		if(num>0) {
			this.baseMapper.updateUserGameNumber(userGameNumber);
		}else {
			userGameNumber.setGameName(gameRecord.getGameName());
			userGameNumber.setRoomName(gameRecord.getRoomName());
			userGameNumber.setUserAccount(gameRecord.getUserAccount());
			this.baseMapper.insert(userGameNumber);
		}
		
	}

	@Override
	public UserGameNumberEntity queryNum(Long userId,Long roomId,Long gameId,Date countDate) {
		return baseMapper.queryNum(userId,roomId,gameId,countDate);
	}
	/**
	 * 财神任务计算  获取 要求对局的  完成次数 ， 以及是否完成（只包含一次）
	 * @param user  会员实体
	 * @param playerTask 任务实体
	 * @param cycle  周期
	 * @return
	 */
	@Override
	public PlayerTasksEntity mammonTask(UserEntity user,PlayerTasksEntity playerTask, Integer cycle) {
		//1.查询在周期时间内 通过(会员id+游戏id+房间)  输，赢，对局次数
		UserGameNumberEntity whetherCompleteList = this.baseMapper.queryNum(user.getId(),
				playerTask.getRoomId(),playerTask.getGameId(),DateUtils.addDayZeroPoint(new Date(),cycle));
		//2.对局记录为空，一个对局任务都还没做 
		//whetherCompleteList=new UserGameNumberEntity();//假数据
		//whetherCompleteList.setWinNum(78);
		if(whetherCompleteList==null 
				||( whetherCompleteList.getGameNum()==null
				&& whetherCompleteList.getWinNum()==null
				&& whetherCompleteList.getLoseNum()==null)) {
			playerTask.setWhetherComplete(false);//未完成
			playerTask.setCompleteNum(0);//完成数量为0
			return playerTask;//跳出方法
		}
		//2.5新增游戏场次  如果有指定场次  就要去统计指定场次的输赢
		if(playerTask.getGradeId()!=null&&playerTask.getGradeId()!=0) {
			Map<String, Object> map = gameRecordDao.queryGameNumByGameIdGradeId(user.getId(),
					DateUtils.formatTime(DateUtils.addDayZeroPoint(new Date(), cycle)),
					DateUtils.formatTime(new Date()), playerTask.getGameId(), playerTask.getGradeId(),
					playerTask.getRoomId());
			int winNum = MathUtil.getBigDecimal(map.get("winNum")).intValue();
			int loseNum = MathUtil.getBigDecimal(map.get("loseNum")).intValue();
			int drawNum = MathUtil.getBigDecimal(map.get("drawNum")).intValue();
			whetherCompleteList.setWinNum(winNum);
			whetherCompleteList.setLoseNum(loseNum);
			whetherCompleteList.setGameNum(winNum+loseNum+drawNum);
		}
		//3.1 如果对局要求为胜局的话
		if(playerTask.getConfrontationRequirement()==Constant.CONFRONTATION_1) {
			if (whetherCompleteList.getWinNum() != null && (whetherCompleteList.getWinNum()
					- playerTask.getReceiveTaskNum() * playerTask.getCondition()) >= playerTask.getCondition()) {
				//是否完成要通过(已领取次数*条件condition) 跟现有的对局次数做对比          现有对局10次  -（领取2次）*（条件为3）    
				playerTask.setWhetherComplete(true);
				if(playerTask.getReceiveTaskNum()<playerTask.getTaskNum()) {//领取次数<任务设置次数 
					 //现有对局13次  -（领取1次）*（条件为3）    / （条件为3）
					Integer unTaskNumTotal=(whetherCompleteList.getWinNum()
							- playerTask.getReceiveTaskNum() * playerTask.getCondition()) / playerTask.getCondition();
					Integer unTaskNum = unTaskNumTotal + playerTask.getReceiveTaskNum() > playerTask.getTaskNum()
							? playerTask.getTaskNum() - playerTask.getReceiveTaskNum()
							: unTaskNumTotal;
					user.setUnTaskNum(user.getUnTaskNum()+unTaskNum);//已完成任务数+unTaskNum  红点
				}
			}else {
				playerTask.setWhetherComplete(false);
			}
			//完成次数
			playerTask.setCompleteNum(whetherCompleteList.getWinNum()!=null?whetherCompleteList.getWinNum():0);
		}
		//如果对局要求为输胜局的话
		else if(playerTask.getConfrontationRequirement()==Constant.CONFRONTATION_2) {
			if (whetherCompleteList.getLoseNum() != null && (whetherCompleteList.getLoseNum()
					- playerTask.getReceiveTaskNum() * playerTask.getCondition()) >= playerTask.getCondition()) {
				playerTask.setWhetherComplete(true);
				if(playerTask.getReceiveTaskNum()<playerTask.getTaskNum()) {//领取次数<任务设置次数 
					Integer unTaskNumTotal=(whetherCompleteList.getLoseNum()
							- playerTask.getReceiveTaskNum() * playerTask.getCondition()) / playerTask.getCondition();
					Integer unTaskNum = unTaskNumTotal + playerTask.getReceiveTaskNum() > playerTask.getTaskNum()
							? playerTask.getTaskNum() - playerTask.getReceiveTaskNum()
							: unTaskNumTotal;
					user.setUnTaskNum(user.getUnTaskNum()+unTaskNum);//已完成任务数+unTaskNum  红点
				}
			}else {
				playerTask.setWhetherComplete(false);
			}
			playerTask.setCompleteNum(whetherCompleteList.getLoseNum()!=null?whetherCompleteList.getLoseNum():0);
		}
		//如果对局要求为输对局的话
		else if(playerTask.getConfrontationRequirement()==Constant.CONFRONTATION_3) {
			if (whetherCompleteList.getGameNum() != null && (whetherCompleteList.getGameNum()
					- playerTask.getReceiveTaskNum() * playerTask.getCondition()) >= playerTask.getCondition()) {
				playerTask.setWhetherComplete(true);
				if(playerTask.getReceiveTaskNum()<playerTask.getTaskNum()) {//领取次数<任务设置次数 
					Integer unTaskNumTotal=(whetherCompleteList.getGameNum()
							- playerTask.getReceiveTaskNum() * playerTask.getCondition()) / playerTask.getCondition();
					Integer unTaskNum = unTaskNumTotal + playerTask.getReceiveTaskNum() > playerTask.getTaskNum()
							? playerTask.getTaskNum() - playerTask.getReceiveTaskNum()
							: unTaskNumTotal;
					user.setUnTaskNum(user.getUnTaskNum()+unTaskNum);//已完成任务数+unTaskNum  红点
				}
			}else {
				playerTask.setWhetherComplete(false);
			}
			playerTask.setCompleteNum(whetherCompleteList.getGameNum()!=null?whetherCompleteList.getGameNum():0);
		}
		return playerTask;
	}


}
