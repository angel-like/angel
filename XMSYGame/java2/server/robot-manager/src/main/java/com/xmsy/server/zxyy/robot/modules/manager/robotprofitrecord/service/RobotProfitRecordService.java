package com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.entity.RobotProfitRecordEntity;


/**
 * 机器人管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-18 10:37:36
 */
public interface RobotProfitRecordService extends IService<RobotProfitRecordEntity> {
	
	Boolean sumYesterdayProfit();

	Long countProfitCoin(RobotProfitRecordEntity entity);
}

