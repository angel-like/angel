package com.xmsy.server.zxyy.robot.modules.manager.robot.entity;

import lombok.Data;


/**
 * 机器人管理
 * 定时任务
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 14:58:45
 */
@Data

public class RobotTaskEntity  {
		/**
	* id
	*/
	private Long id;
							/**
	 * 金币余额
	 */
    private Long coin;
			/**
	 * 盈利金币额
	 */
    private Long profitCoin;
			/**
	 * 机器人代号
	 */
    private String name;
			
			/**
	 * 游戏ID
	 */
    private Long gameId;
			/**
	 * 场次id
	 */
    private Long gradeId;
    /**
	 * 派出时间
	 */
    private String updateTime;
 
    
	}
