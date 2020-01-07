package com.xmsy.server.zxyy.robot.modules.manager.robot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.robot.modules.manager.robot.entity.RobotEntity;

/**
 * 机器人管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 14:58:45
 */
@Mapper
public interface RobotDao extends BaseMapper<RobotEntity> {

	boolean updateProfitCoin(@Param("id") Long id, @Param("variableCoin") Long variableCoin,
			@Param("version") int version);

	void insertGetId(RobotEntity robotEntity);

	List<RobotEntity> taskRecyclingRobot(@Param("gameId") Long gameId, @Param("gradeId") Long gradeId);

	Long countProfitCoin(@Param("entity") RobotEntity entity);

	boolean updateRobot(@Param("entity") RobotEntity robot);

}
