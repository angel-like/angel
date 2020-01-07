package com.xmsy.server.zxyy.manager.modules.manager.reportplayergamedaily.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.reportplayergamedaily.entity.ReportPlayerGameDailyEntity;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 每日玩家游戏投入产出报表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
@Mapper
public interface ReportPlayerGameDailyDao extends BaseMapper<ReportPlayerGameDailyEntity> {
	/**
	 * 查询同个用户同个场次的总投入、总产出、总输赢
	 * @return
	 */
	Map<String, Object> querySum(@Param("userId") Long userId,@Param("gradeId") Long gradeId,@Param("gameId") Long gameId);
	
	
}
