package com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistweek.entity.RankingListWeekEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 日排行榜
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Mapper
public interface RankingListDayDao extends BaseMapper<RankingListDayEntity> {
	/**
	 * 取出前100条数据
	 * 根据榜单id和日期，并且id不一致
	 * @param entity
	 * @return
	 */
	List<RankingListDayEntity> getRankingListByDayAndRidTop100( RankingListDayEntity entity);
	/**
	 * 物理删除
	 * 根据id
	 * @param id
	 * @return
	 */
	Integer physicalDeleteById(@Param("id") Long id);
	
	/**
     * 根据时间范围统计榜单
     * @param rankingListId
     * @param startDate
     * @param endDate
     * @return
     */
    List<RankingListWeekEntity> statisticsRankingListByDateRange(@Param("rankingListId") Long rankingListId,
    		@Param("startTime")String startDate,@Param("endTime")String endDate); 
    
	
}
