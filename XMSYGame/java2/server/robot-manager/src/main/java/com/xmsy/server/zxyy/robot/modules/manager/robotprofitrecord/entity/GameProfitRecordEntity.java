package com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Data;


/**
 * 游戏盈利统计
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-18 10:37:36
 */
@Data
public class GameProfitRecordEntity {
	
	/**
	 * 所属游戏ID
	 */
    private Long gameId;
    
			/**
	 * 机器人账户名
	 */
    private String userAccount;
			/**
	 * 机器人ID
	 */
    private Long userId;
    		
    		/**
   	 * 所属游戏场次ID
   	 */
    private Long gradeId;
			/**
	 * 统计日期
	 */
    private Date recordTime;
    
    
    //***********************/用于查询/***************
    /**
	 * 开始日期
	 */
    @TableField(exist=false)
    private String startTime;
    /**
	 * 结束日期
	 */
    @TableField(exist=false)
    private String endTime;
	}
