package com.xmsy.server.zxyy.robot.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.collect.Maps;
import com.xmsy.server.zxyy.robot.modules.manager.robot.entity.RobotEntity;

/**
 * 全局定义
 * 
 * @author Administrator
 *
 */
public class SysConstant {

	/** token */
	public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0b2tlbiIsImlhdCI6MTU1Mjg5ODIyMn0.uSfTj7U9XcEAHddfjv742Ti44ZthUhRTWk4_ke1s9R8";

	// 机器人状态
	/** 空闲 */
	public static final Boolean ROBOT_STATUS_FALSE = false;
	/** 游戏中 */
	public static final Boolean ROBOT_STATUS_TRUE = true;
	// 机器人是否启用
	/** 结算中（不可用） */
	public static final Boolean ROBOT_ENABLE_FALSE = false;
	/** 可用 */
	public static final Boolean ROBOT_ENABLE_TRUE = true;
	// 监听的机器人归还队列名称
	public static final String LISTENER_QUEUE = "robot";
	// 重试次数
	public static final int RETRY_TIME = 5;
	// 重试计数器
	public static final Map<Long, Integer> MESSAGE = Maps.newConcurrentMap();
	/**
	 * 游戏大厅Ip和端口默认Map
	 */
	public static Map<Integer, String> hallAddressMap = new HashMap<Integer, String>();
	/**
	 * 用于时间拼接开始
	 */
	public static final String START_TIME = " 00:00:00";
	/**
	 * 用于时间拼接结束
	 */
	public static final String END_TIME = " 23:59:59";

	// 空闲机器人
	public static final Map<String, Queue<RobotEntity>> idleRobotMap = new ConcurrentHashMap<String, Queue<RobotEntity>>();
	// 等待更新的
	public static final Queue<RobotEntity> modifyRobotMap = new ConcurrentLinkedQueue<RobotEntity>();;
	/**
	 * 大厅缓存key拼接字段
	 */
	public static final String HALL_ID = "hallId/";
	/**
	 * 机器人缓存key拼接字段
	 */
	public static final String ROBOT = "robot/";
	/**
	 * 匹配房间
	 */
	public static final Long ROOMID=1L;
}
