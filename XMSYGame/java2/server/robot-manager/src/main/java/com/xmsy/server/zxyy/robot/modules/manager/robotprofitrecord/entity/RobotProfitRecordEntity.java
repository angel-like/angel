package com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.robot.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 机器人管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-18 10:37:36
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("robot_profit_record")
public class RobotProfitRecordEntity extends BaseEntity<RobotProfitRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 盈利金币额
	 */
    private Long profitCoin;
			/**
	 * 机器人账户名
	 */
    private String userAccount;
			/**
	 * 机器人ID
	 */
    private Long userId;
    		/**
	 * 所属游戏ID
	 */
    private Long gameId;
    		/**
   	 * 所属游戏场次ID
   	 */
    private Long gradeId;

	/**
	 * 所属游戏场次ID
	 */
//	private Long providerCode;


			/**
	 * 统计日期
	 */
    private Date recordTime;
    
    /**
	 *游戏名
	 */
    @TableField(exist=false)
    private String gameName;
    /**
	 *场次名称
	 */
    @TableField(exist=false)
    private String gradeName;
    
    
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
