package com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;


/**
 * 每天签到奖励
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
public interface SignRewardContinuousEveryDayService extends IService<SignRewardContinuousEveryDayEntity> {
	
	PageUtils findSignRewardContinuousEveryDayPage(SignRewardContinuousEveryDayEntity signRewardContinuousEveryDay, PageParam pageParam);
	
	

}

