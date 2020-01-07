package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordSumEntity;

/**
 * 游戏记录
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-02 11:32:49
 */
@Mapper
public interface GameRecordDao extends BaseMapper<GameRecordEntity> {

	Long sumCountProfit(@Param("entity") GameRecordEntity gamerecord);

	void deleteGameRecord(@Param("date") String date);
	
	void deleteGameRecordByDays(@Param("days") int days);

	/**
	 * 获取指定房间不包含当日的指定时间盈利
	 *
	 * @param gamerecord
	 * @return
	 */
	Long sumProfitForGame(@Param("list") List<Long> gameIds, @Param("queryTime") String queryTime);

	/**
	 * 获取指定房间今日盈利
	 *
	 * @param gamerecord
	 * @return
	 */
	Long sumProfitTodayForGame(List<Long> gameIds);

	/**
	 * 获取指定场次的 每款游戏总盈利情况
	 * 
	 * @param gamerecord
	 * @return
	 */
	List<GameRecordSumEntity> sumProfitForRecord(@Param("list") List<Long> gameIds,
			@Param("queryStartTime") String queryStartTime, @Param("queryEndTime") String queryEndTime,
			@Param("timeStatus") Boolean timeStatus);

	/**
	 * 获取指定场次的 每款游戏每个场次的盈利情况
	 * 
	 * @param gameId
	 * @return
	 */
	List<GameRecordSumEntity> sumProfitForGradeRecord(@Param("gameId") Long gameId,
			@Param("queryStartTime") String queryStartTime, @Param("queryEndTime") String queryEndTime,
			@Param("timeStatus") Boolean timeStatus);

}
