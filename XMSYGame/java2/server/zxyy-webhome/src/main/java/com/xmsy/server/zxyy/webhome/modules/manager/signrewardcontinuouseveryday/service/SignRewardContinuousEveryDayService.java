package com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;


/**
 * 每天签到奖励
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
public interface SignRewardContinuousEveryDayService extends IService<SignRewardContinuousEveryDayEntity> {
	
	/**
	 * 根据vipID查询签到信息
	 * @param vipId
	 * @return
	 */
	List<SignRewardContinuousEveryDayEntity> selectSignIn(Long vipId);
	/**
	 * 获取VIP签到的数据
	 * @param listSignReward
	 * @return
	 */
	List<Map<String,Object>> getSignRewardData(List<SignRewardContinuousEveryDayEntity> listSignReward);

}

