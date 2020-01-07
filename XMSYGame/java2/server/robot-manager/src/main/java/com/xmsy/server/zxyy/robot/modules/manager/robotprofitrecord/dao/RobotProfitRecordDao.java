package com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.entity.RobotProfitRecordEntity;

/**
 * 机器人管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-18 10:37:36
 */
@Mapper
public interface RobotProfitRecordDao extends BaseMapper<RobotProfitRecordEntity> {

	List<RobotProfitRecordEntity> sumYesterdayProfit();

	void deleteByDate();
	
	Long countProfitCoin(@Param("entity")RobotProfitRecordEntity entity);
}
