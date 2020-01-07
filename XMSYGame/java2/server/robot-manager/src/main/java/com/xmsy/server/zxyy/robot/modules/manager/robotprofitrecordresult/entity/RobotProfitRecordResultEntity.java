package com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecordresult.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.robot.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 每日统计结果表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-18 16:29:12
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("robot_profit_record_result")
public class RobotProfitRecordResultEntity extends BaseEntity<RobotProfitRecordResultEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 统计结果0失败，1成功
	 */
    private Boolean resultEnable;
    //查询时间
    @TableField(exist=false)
    private String queryTime;
	}
