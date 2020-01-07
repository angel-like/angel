package com.xmsy.server.zxyy.webhome.modules.app.playertasks;

 import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.playertasks.param.ReceiveParam;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.service.PlayerTasksService;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.service.PlayerTasksReceiveRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.entity.ShareRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.service.ShareRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.entity.UserGameNumberEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.service.UserGameNumberService;

import cn.hutool.json.JSONArray;
//import lombok.extern.slf4j.Slf4j;


//@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class PlayerTasksListController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private PlayerTasksService playerTasksService;
	
	@Autowired
	private PlayerTasksReceiveRecordService playerTasksReceiveRecordService;
	
	@Autowired
	private UserGameNumberService userGameNumberService;
	
	@Autowired
	private ShareRecordService shareRecordService;
	@Autowired
	private GameRecordDao gameRecordDao;
	@Resource
	private PushService pushService;
	
	/**
	 * 会员获取今日可完成列表接口
	 * @throws Exception
	 */
	@GetMapping("/getPlayerTasks")
	public R getPlayerTasks(@RequestHeader("token") String token)
			throws Exception {
		UserEntity entity = userService.selectUserForToken(token);
		//判断用户是否登录
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		//查询数据字典，查询出任务类型（财神任务，财神降临，分享任务）
		List<SysDictionaryEntity> dictionaryList=sysDictionaryService.selectList(new EntityWrapper<SysDictionaryEntity>().
				eq("parent_code", "TaskType"));
		List<PlayerTasksEntity> playerTasksEntities = null;
		List<PlayerTasksEntity> receivePlayerTasksList = null;
		List<PlayerTasksEntity> unreceivePlayerTasksList = null;
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		for(SysDictionaryEntity dic:dictionaryList) {
			//根据任务类型查询玩家任务信息
			playerTasksEntities = playerTasksService.queryByType(dic.getCode());
			receivePlayerTasksList=new ArrayList<>();
			unreceivePlayerTasksList=new ArrayList<>();
			obj = new JSONObject();
			obj.put("type",dic.getCode());
			obj.put("typeName", dic.getName());
			for(PlayerTasksEntity datra: playerTasksEntities) {
				//周期(天)
				int cycle=datra.getCycle()==null||datra.getCycle()<1?0:datra.getCycle()-1;
				cycle=cycle*-1;
				//查询会员领取记录次数
				Integer whetherReceive =  playerTasksReceiveRecordService.queryCount(datra.getId(), entity.getId(),DateUtils.addDayZeroPoint(new Date(),cycle));
				if(whetherReceive>0) {
					//领取次数大于0将是否领取设为true，是否完成设为true，完成次数设为满足条件的次数，增加玩家任务数据
					datra.setWhetherReceive(true);
					datra.setWhetherComplete(true);
					datra.setCompleteNum(datra.getCondition());
					receivePlayerTasksList.add(datra);
					continue;
				}
				//没领取的话将是否领取设为false
				unreceivePlayerTasksList.add(datra);
				datra.setWhetherReceive(false);
				//任务类型为3（分享任务）
				if("3".equals(dic.getCode())) {
					ShareRecordEntity entity1 = new ShareRecordEntity();
					entity1.setUserId(entity.getId());
					//查询分享的次数
					Integer shareRecordEntity = shareRecordService.selectCount(new EntityWrapper<ShareRecordEntity>(entity1));
					//次数大于等于满足的条件，将是否完成改成true，否的话将是否完成改成false
					if(shareRecordEntity >= datra.getCondition()) {
						datra.setWhetherComplete(true);
					}else {
						datra.setWhetherComplete(false);
					}
					//完成的次数为查询出来的次数
					datra.setCompleteNum(shareRecordEntity);
					continue;
				}
				//如果任务类型为财神任务（1）且gameId=0（全部游戏）
				if(datra.getType()==1&&datra.getGameId()==0) {
					datra.setType(2l);
				//如果任务类型为财神任务（1）且gameId>0（单个游戏）
				}else if(datra.getType()==1&&datra.getGameId()>0) {
					datra.setType(1l);
				}
				//查询输，赢，对局次数
				UserGameNumberEntity whetherCompleteList = userGameNumberService.queryNum(entity.getId(),
						datra.getRoomId(),datra.getGameId(),DateUtils.addDayZeroPoint(new Date(),cycle));
				if(whetherCompleteList==null 
						||( whetherCompleteList.getGameNum()==null
						&& whetherCompleteList.getWinNum()==null
						&& whetherCompleteList.getLoseNum()==null)) {
					datra.setWhetherComplete(false);
					datra.setCompleteNum(0);
					continue;
				}
				//如果对局要求为胜局的话
				if(datra.getConfrontationRequirement()==Constant.CONFRONTATION_1) {
					if( whetherCompleteList.getWinNum()!=null
							&& whetherCompleteList.getWinNum() >=datra.getCondition()) {
						datra.setWhetherComplete(true);
					}else {
						datra.setWhetherComplete(false);
					}
					datra.setCompleteNum(whetherCompleteList.getWinNum()!=null?whetherCompleteList.getWinNum():0);
				}
				//如果对局要求为输胜局的话
				else if(datra.getConfrontationRequirement()==Constant.CONFRONTATION_2) {
					if( whetherCompleteList.getLoseNum()!=null
							&& whetherCompleteList.getLoseNum() >=datra.getCondition()) {
						datra.setWhetherComplete(true);
					}else {
						datra.setWhetherComplete(false);
					}
					datra.setCompleteNum(whetherCompleteList.getLoseNum()!=null?whetherCompleteList.getLoseNum():0);
				}
				//如果对局要求为输对局的话
				else if(datra.getConfrontationRequirement()==Constant.CONFRONTATION_3) {
					if( whetherCompleteList.getGameNum()!=null
							&& whetherCompleteList.getGameNum() >=datra.getCondition()) {
						datra.setWhetherComplete(true);
					}else {
						datra.setWhetherComplete(false);
					}
					datra.setCompleteNum(whetherCompleteList.getGameNum()!=null?whetherCompleteList.getGameNum():0);
				}
			}
			unreceivePlayerTasksList.addAll(receivePlayerTasksList);
			obj.put("playerTasksList", unreceivePlayerTasksList);
			array.add(obj);
		}
		
		//log.info("[getPlayerTasks===》获取任务列表] obj {} ", array);
		return R.ok().put("data", array);
		
	}
	
	/**
	 * 会员任务领取奖励接口
	 * 
	 * @throws Exception
	 */
	@PostMapping("/userReward")
	public R userReward(@RequestHeader("token") String token,@RequestBody ReceiveParam receiveParam)
			throws Exception {
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		playerTasksReceiveRecordService.userIsNotReward(entity,receiveParam);
		return R.ok();
	}
	//===========================================新版本任务==============================
	/**
	 * 会员获取今日可完成列表接口
	 * @throws Exception
	 */
	@GetMapping("/getPlayerTasksNew")
	public R getPlayerTasksNew(@RequestHeader("token") String token)
			throws Exception {
		UserEntity user = userService.getUser(token);
		user.setUnTaskNum(0);//设置红点个数为0
		//1.查询数据字典，查询出任务类型（财神任务，富豪任务，福缘任务）
		List<SysDictionaryEntity> dictionaryList=sysDictionaryService.selectList(new EntityWrapper<SysDictionaryEntity>().
				eq("parent_code", "TaskType"));
		List<PlayerTasksEntity> playerTasksEntities = null;
		List<PlayerTasksEntity> receivePlayerTasksList = null;
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		for(SysDictionaryEntity dic:dictionaryList) {
			//2.根据任务类型查询玩家任务信息  并遍历
			playerTasksEntities = playerTasksService.queryByType(dic.getCode());
			receivePlayerTasksList=new ArrayList<>();
			obj = new JSONObject();
			obj.put("type",dic.getCode());
			obj.put("typeName", dic.getName());
			for(PlayerTasksEntity playerTask: playerTasksEntities) {
				int cycle=playerTask.getCycle()==null||playerTask.getCycle()<1?0:playerTask.getCycle()-1;
				cycle=cycle*-1;//周期(天)
				//3.查询在  任务id+会员id+周期内   会员已领取记录次数
				Integer whetherReceive =  playerTasksReceiveRecordService.queryCount(playerTask.getId(), user.getId(),DateUtils.addDayZeroPoint(new Date(),cycle));
				playerTask.setReceiveTaskNum(whetherReceive);//已领取记录次数
				//4.没领取或者领取次数没满       并通过任务类型，分类去判断会员是否达到领取要求，否的话还差多少达到要求
				playerTask.setWhetherReceive(false);//是否领取设为false
				//4.1任务类型为1（财神任务）对局任务
				if("1".equals(dic.getCode())) {
					//4.1.1 通过会员对局数 +已领取次数  =》 获取是否完成（通过已领取次数*条件判断），完成次数
					playerTask=userGameNumberService.mammonTask(user, playerTask, cycle);
					//4.1.2 全部领取完
					if(whetherReceive>=playerTask.getTaskNum()) {//领取次数>=任务设置次数 
						playerTask.setWhetherReceive(true);//是否全部领取完
						playerTask.setWhetherComplete(true);// 是否完成
					}
					receivePlayerTasksList.add(playerTask);//增加玩家任务数据
					continue;
				}
				//4.2 任务类型为2（富豪任务）打码任务
				if("2".equals(dic.getCode())) {
					//4.2.1 通过会员打码量 +已领取次数   =》 获取是否完成（通过已领取次数*条件判断），完成次数
					playerTask = validBetTask(user, playerTask, cycle);
					//4.2.2 全部领取完
					if(whetherReceive>=playerTask.getTaskNum()) {//领取次数>=任务设置次数 
						playerTask.setWhetherReceive(true);//是否全部领取完
						playerTask.setWhetherComplete(true);// 是否完成
					}
					receivePlayerTasksList.add(playerTask);//增加玩家任务数据
					continue;
				}
				//4.3 任务类型为3（福缘任务）分享任务
				if("3".equals(dic.getCode())) {
					//4.3.1  通过会员分享的次数 +已领取次数   =》 获取是否完成（通过已领取次数*条件判断），完成次数
					playerTask = shareRecordService.otherTask(user, playerTask, cycle);
					//4.3.2 全部领取完
					if(whetherReceive>=playerTask.getTaskNum()) {//领取次数>=任务设置次数 
						playerTask.setWhetherReceive(true);//是否全部领取完
						playerTask.setWhetherComplete(true);// 是否完成
					}
					receivePlayerTasksList.add(playerTask);//增加玩家任务数据
					continue;
				}
				
				
			}
			obj.put("playerTasksList", receivePlayerTasksList);
			array.add(obj);
		}
		//log.info("[getPlayerTasks===》获取任务列表] obj {} ", array);
		return R.ok().put("data", array);
		
	}


	/** * 富豪任务（打码任务）*/
	private PlayerTasksEntity validBetTask(UserEntity user, PlayerTasksEntity playerTask, int cycle) {
		//1 查询在  周期时间内（今天内） +游戏id+房间id 会员打码量
		Long gameId=playerTask.getGameId()==0?null:playerTask.getGameId();
		Long gradeId=playerTask.getGradeId()==0?null:playerTask.getGradeId();
		Long roomId=playerTask.getRoomId()==0?null:playerTask.getRoomId();
		Long userValidBetLong = gameRecordDao.getUserValidBetByGameId(user.getId(),
				DateUtils.formatTime(DateUtils.addDayZeroPoint(new Date(),cycle)), DateUtils.formatTime(new Date()),
				gameId,gradeId,roomId);//这里加个大厅
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
				user.setUnTaskNum(user.getUnTaskNum()+unTaskNum);//已完成任务数+unTaskNum  红点
			}
		}else {
			playerTask.setWhetherComplete(false);
		}
		//3  把当前会员打码量设置进去
		playerTask.setCompleteNum(userValidBet);
		return playerTask;
	}
	
	
	/**
	 * 会员任务领取奖励接口
	 * 
	 * @throws Exception
	 */
	@PostMapping("/userRewardNew")
	public R userRewardNew(@RequestHeader("token") String token,@RequestBody @Valid ReceiveParam receiveParam)
			throws Exception {
		UserEntity user = userService.getUser(token);
		playerTasksReceiveRecordService.userIsNotRewardNew(user,receiveParam);
		return R.ok();
	}
	
	
	/**
	 * 会员任务列表获取红点
	 * @throws Exception
	 */
	@GetMapping("/redPointTask")
	public R getRedPoint(@RequestHeader("token") String token)
			throws Exception {
		UserEntity user = userService.getUser(token);
		user.setUnTaskNum(0);//设置红点个数为0
		//1.查询数据字典，查询出任务类型（财神任务，富豪任务，福缘任务）
		List<SysDictionaryEntity> dictionaryList=sysDictionaryService.selectList(new EntityWrapper<SysDictionaryEntity>().
				eq("parent_code", "TaskType"));
		List<PlayerTasksEntity> playerTasksEntities = null;
		for(SysDictionaryEntity dic:dictionaryList) {
			//2.根据任务类型查询玩家任务信息  并遍历
			playerTasksEntities = playerTasksService.queryByType(dic.getCode());
			for(PlayerTasksEntity playerTask: playerTasksEntities) {
				int cycle=playerTask.getCycle()==null||playerTask.getCycle()<1?0:playerTask.getCycle()-1;
				cycle=cycle*-1;//周期(天)
				//3.查询在  任务id+会员id+周期内   会员已领取记录次数
				Integer whetherReceive =  playerTasksReceiveRecordService.queryCount(playerTask.getId(), user.getId(),DateUtils.addDayZeroPoint(new Date(),cycle));
				playerTask.setReceiveTaskNum(whetherReceive);//已领取记录次数
				//4.没领取或者领取次数没满       并通过任务类型，分类去判断会员是否达到领取要求，否的话还差多少达到要求
				playerTask.setWhetherReceive(false);//是否领取设为false
				//4.1任务类型为1（财神任务）对局任务
				if("1".equals(dic.getCode())) {
					//4.1.1 通过会员对局数 +已领取次数  =》 获取是否完成（通过已领取次数*条件判断），完成次数
					playerTask=userGameNumberService.mammonTask(user, playerTask, cycle);
					continue;
				}
				//4.2 任务类型为2（富豪任务）打码任务
				if("2".equals(dic.getCode())) {
					//4.2.1 通过会员打码量 +已领取次数   =》 获取是否完成（通过已领取次数*条件判断），完成次数
					playerTask = validBetTask(user, playerTask, cycle);
					continue;
				}
				//4.3 任务类型为3（福缘任务）分享任务
				if("3".equals(dic.getCode())) {
					//4.3.1  通过会员分享的次数 +已领取次数   =》 获取是否完成（通过已领取次数*条件判断），完成次数
					playerTask = shareRecordService.otherTask(user, playerTask, cycle);
					continue;
				}
			}
		}
		return R.ok().put("data", user.getUnTaskNum());
		
	}
}
