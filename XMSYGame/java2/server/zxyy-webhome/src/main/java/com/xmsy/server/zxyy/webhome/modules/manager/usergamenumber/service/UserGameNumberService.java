package com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.service;

import java.util.Date;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.entity.UserGameNumberEntity;


/**
 * 用户每日游戏次数统计表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-06-18 10:46:57
 */
public interface UserGameNumberService extends IService<UserGameNumberEntity> {
	void saveUserGameNumber(GameRecordEntity gameRecord);
	
	/**
	 * 查询输赢，对局次数
	 * @param userGameNumberEntity
	 * @return
	 */
	UserGameNumberEntity queryNum(Long userId,Long roomId,Long gameId,Date countDate);
	/**
	 * 财神任务计算  获取 要求对局的  完成次数 ， 以及是否完成（只包含一次）
	 * @param user  会员实体
	 * @param playerTask 任务实体
	 * @param cycle  周期
	 * @return
	 */
	PlayerTasksEntity mammonTask(UserEntity user,PlayerTasksEntity playerTask,Integer cycle);
}

