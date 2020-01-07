package com.xmsy.server.zxyy.robot.modules.manager.robot.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.robot.modules.manager.robot.entity.RobotEntity;
import com.xmsy.server.zxyy.robot.modules.robotinterface.distribution.param.RobotResultParam;
import com.xmsy.server.zxyy.robot.rabbitmq.param.RobotParam;

/**
 * 机器人管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 14:58:45
 */
public interface RobotService extends IService<RobotEntity> {

	// 单次回收机器人
	void recyclingRobot(RobotParam entity);

	RobotResultParam distributionRobot(Long gameId, Long gradeId, Long hallId, Long coin);

	// 定时回收机器人
	void taskRecyclingRobot();

	Long countProfitCoin(RobotEntity entity);

	// 根据游戏场次及游戏回收机器人
	void taskRobot(String[] ids, Long gameId, Long gradeId);

	boolean updateRobot(RobotEntity robot);

}
