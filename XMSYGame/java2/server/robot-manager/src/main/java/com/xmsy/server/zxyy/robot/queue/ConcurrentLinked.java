package com.xmsy.server.zxyy.robot.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.manager.robot.entity.RobotEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrentLinked {
	// 空闲队列===新增机器人
	public static void putIdle(RobotEntity robot) {
		if (null == robot) {
			log.error("[ConcurrentLinked->putIdle] robot is null");
			return;
		}
		// 从指定游戏场次的空闲机器人队列获取机器人队列，并将机器人加入队列
		Queue<RobotEntity> idleRobot = SysConstant.idleRobotMap.get(getKey(robot.getGameId(), robot.getGradeId()));
		if (null == idleRobot) {
			Queue<RobotEntity> concurrentLinked = new ConcurrentLinkedQueue<RobotEntity>();
			concurrentLinked.add(robot);
			SysConstant.idleRobotMap.put(getKey(robot.getGameId(), robot.getGradeId()), concurrentLinked);
		} else {
			idleRobot.add(robot);
		}
	}

	// 空闲队列===获取机器人
	public static RobotEntity getIdle(String key) {
		// 通过key(游戏和场次ID),获取空闲机器人队列
		Queue<RobotEntity> idleRobot = SysConstant.idleRobotMap.get(key);
		if (null != idleRobot) {
			// 获取空闲机器人队列中的机器人
			RobotEntity entity = idleRobot.poll();
			return entity;
		}
		return null;
	}

	// 队列新增待更新机器人
	public static void putModify(RobotEntity robot) {
		if (null == robot) {
			log.error("[ConcurrentLinked->putModify] robot is null");
			return;
		}
		SysConstant.modifyRobotMap.add(robot);
	}

	// 通过游戏Id和场次Id获取key
	public static String getKey(Long gameId, Long gradeId) {
		return gameId + "-" + gradeId;
	}
}
