package com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.playertasks.param.ReceiveParam;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.dao.PlayerTasksDao;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.dao.PlayerTasksReceiveRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.entity.PlayerTasksReceiveRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.service.PlayerTasksReceiveRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproprecord.entity.ShopPropRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("playerTasksReceiveRecordService")
public class PlayerTasksReceiveRecordServiceImpl extends ServiceImpl<PlayerTasksReceiveRecordDao, PlayerTasksReceiveRecordEntity> implements PlayerTasksReceiveRecordService {

	@Autowired
	private PlayerTasksDao playerTasksDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private OrderCashExamineService orderCashExamineService;
	@Override
	public Integer queryCount(Long taskId, Long userId,Date finishDate) {
		return baseMapper.queryCount(taskId, userId,finishDate);
	}

	@Override
	public void userIsNotReward(UserEntity user, ReceiveParam receiveParam) {
		if (receiveParam.getTaskId() == null) {
			throw new RRException(ErrorCode.UserTaskCode.TASK_ERRO.getErrMsg(),
					ErrorCode.UserTaskCode.TASK_ERRO.getCode());
		}
		PlayerTasksEntity entity =  playerTasksDao.selectById(receiveParam.getTaskId());
		int cycle=entity.getCycle()==null||entity.getCycle()<1?0:entity.getCycle()-1;
		cycle=cycle*-1;
		Integer whetherReceive = baseMapper.queryCount(receiveParam.getTaskId(),user.getId(),DateUtils.addDayZeroPoint(new Date(),cycle));
		if(whetherReceive <=0) {
			UserEntity userParam = new UserEntity();
			userParam.setId(user.getId());
			 if(entity.getPropId()==Constant.COIN_PROP_ID) {
				 userService.updateUserCoin(user,entity.getPropNum().longValue(),
						 Constant.TransactionType.TASK_RECEIVE.getValue(), 
						 Constant.TransactionDetailType.TASKRECEIVE.getValue());
				 userParam.setCoin(entity.getPropNum().longValue());
			 }else if(entity.getPropId()==Constant.ROOMCART_PROP_ID){
					
					userParam.setRoomCard(entity.getPropNum().longValue());
					Integer i = userDao.updateUserWalletByUserId(userParam);
					if(i>0) {
						// 添加金币兑换房卡表（道具交易记录表）
						ShopPropRecordEntity shopPropRecord = new ShopPropRecordEntity();
						shopPropRecord.setSysPropId(entity.getPropId());// 道具名称id(0表示在道具列表里不存在)
						shopPropRecord.setPropNumber(Integer.parseInt(String.valueOf(entity.getPropNum())));// 兑换的道具数量
						shopPropRecord.setType(Constant.PROP_USE_TYPE_2);// 类型0：购买 1：使用 2 完成任务领取
						shopPropRecord.setProduceId(0L); // 产品id(0表示在商城产品表里不存在)
						shopPropRecord.setUserId(user.getId());// 会员id
						shopPropRecord.setUserAccount(user.getAccount());// 会员账号
						shopPropRecord.insert();
					}
					
			 }
			PlayerTasksReceiveRecordEntity recordEntity = new PlayerTasksReceiveRecordEntity();
			recordEntity.setTaskId(receiveParam.getTaskId());
			recordEntity.setUserId(user.getId());
			recordEntity.setUserAccount(user.getAccount());
			recordEntity.setFinishDate(new Date());
			recordEntity.insert();
			UserInfoMessage message = new UserInfoMessage(userParam, null);
			log.info("[兑换房卡-推送消息] message {}", message);
			pushService.pushUserInfo(message);
		}else {
			throw new RRException(ErrorCode.UserTaskCode.ALREADY_RECIVE.getErrMsg(),
					ErrorCode.UserTaskCode.ALREADY_RECIVE.getCode());
		}
		
	}
	/**
	 * 新版本 会员任务领取奖励接口
	 * @param user
	 * @param receiveParam
	 */
	@Override
	public void userIsNotRewardNew(UserEntity user, ReceiveParam receiveParam) {
		PlayerTasksEntity playerTask =  playerTasksDao.selectById(receiveParam.getTaskId());
		int cycle=playerTask.getCycle()==null||playerTask.getCycle()<1?0:playerTask.getCycle()-1;
		cycle=cycle*-1;
		//查询在  任务id+会员id+周期内   会员已领取记录次数
		Integer whetherReceive = baseMapper.queryCount(receiveParam.getTaskId(),user.getId(),DateUtils.addDayZeroPoint(new Date(),cycle));
		//1.如果已经领取的次数 >=任务次数  抛异常
		if(whetherReceive >=playerTask.getTaskNum()) {
			throw new RRException(ErrorCode.UserTaskCode.ALREADY_RECIVE.getErrMsg(),
					ErrorCode.UserTaskCode.ALREADY_RECIVE.getCode());
		}
		//2.去更新会员金币  或者房卡数量
		UserEntity userParam = new UserEntity();
		userParam.setId(user.getId());
		if (playerTask.getPropId() == Constant.COIN_PROP_ID) {//2.1金币
			userParam.setCoin(playerTask.getPropNum().longValue());//推送消息使用
			userService.updateUserCoin(user, playerTask.getPropNum().longValue(),
					Constant.TransactionType.TASK_RECEIVE.getValue(),
					Constant.TransactionDetailType.TASKRECEIVE.getValue());
			// 新增稽查记录
			try {
				orderCashExamineService.bindUserinfoGiftCashExamine(user, playerTask.getPropNum().longValue());
			} catch (Exception e) {
				log.info("[稽查记录]新增失败（金币单位分）=》", playerTask.getPropNum().longValue());
			}
		} else if (playerTask.getPropId() == Constant.ROOMCART_PROP_ID) {//2.2房卡
			userParam.setRoomCard(playerTask.getPropNum().longValue());//推送消息使用
			if (userDao.updateUserWalletByUserId(userParam) > 0) {
				// 添加金币兑换房卡表（道具交易记录表）
				ShopPropRecordEntity shopPropRecord = new ShopPropRecordEntity();
				shopPropRecord.setSysPropId(playerTask.getPropId());// 道具名称id(0表示在道具列表里不存在)
				shopPropRecord.setPropNumber(playerTask.getPropNum());// 兑换的道具数量
				shopPropRecord.setType(Constant.PROP_USE_TYPE_2);// 类型0：购买    1：使用      2 ：完成任务领取
				shopPropRecord.setProduceId(0L); // 产品id(0表示在商城产品表里不存在)
				shopPropRecord.setUserId(user.getId());// 会员id
				shopPropRecord.setUserAccount(user.getAccount());// 会员账号
				shopPropRecord.insert();
			}
		}
		//3.增加会员任务领取记录
		PlayerTasksReceiveRecordEntity recordEntity = new PlayerTasksReceiveRecordEntity();
		recordEntity.setTaskId(receiveParam.getTaskId());
		recordEntity.setUserId(user.getId());
		recordEntity.setUserAccount(user.getAccount());
		recordEntity.setFinishDate(new Date());
		recordEntity.insert();
		UserInfoMessage message = new UserInfoMessage(userParam, null);
		//message.setUnTaskNum(-1);//完成任务  任务可完成个数-1
		log.info("[兑换房卡-推送消息] message {}", message);
		pushService.pushUserInfo(message);
		
		
	}
}
