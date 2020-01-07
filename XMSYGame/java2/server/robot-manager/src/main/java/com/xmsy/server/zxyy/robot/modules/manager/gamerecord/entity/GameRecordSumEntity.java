package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity;

import lombok.Data;


/**
 * 游戏记录
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-11 09:32:49
 */
@Data
public class GameRecordSumEntity  {
			
			/**
	 * 游戏ID
	 */
    private Long gameId;
			
			/**
	 * 场次id
	 */
    private Long gradeId;
    /**
	 * 盈利
	 */
    private Long prizeCoin;
    /**
	 * 游戏名
	 */
    private String gameName;
    /**
   	 * 场次名
   	 */
    private String gradeName;
			
	}
