package com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.entity.GameRecordEntity;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-22 11:12:08
 */
@Mapper
public interface GameRecordDao extends BaseMapper<GameRecordEntity> {

	/**
	 * .根据时间获取用户有效下注
	 * 
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Long getUserValidBet(@Param("userId") Long userId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
}
