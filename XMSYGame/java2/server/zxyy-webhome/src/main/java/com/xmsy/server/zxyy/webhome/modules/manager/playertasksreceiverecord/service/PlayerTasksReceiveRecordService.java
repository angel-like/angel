package com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.service;

import java.util.Date;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.playertasks.param.ReceiveParam;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.entity.PlayerTasksReceiveRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;


/**
 * 玩家任务领取记录表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-19 09:57:53
 */
public interface PlayerTasksReceiveRecordService extends IService<PlayerTasksReceiveRecordEntity> {
	
	/**
	 * 查询会员任务是否领取
	 * @param taskId
	 * @param userId
	 * @return
	 */
	Integer queryCount(Long taskId,Long userId,Date finishDate);
	
	void userIsNotReward(UserEntity user,ReceiveParam receiveParam);
	/**
	 * 新版本 会员任务领取奖励接口
	 * @param user
	 * @param receiveParam
	 */
	void userIsNotRewardNew(UserEntity user,ReceiveParam receiveParam);

}

