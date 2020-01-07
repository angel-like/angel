package com.xmsy.server.zxyy.webhome.modules.manager.rankinglistweek.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistweek.entity.RankingListWeekEntity;

/**
 * 周排行榜
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Mapper
public interface RankingListWeekDao extends BaseMapper<RankingListWeekEntity> {
	
	/**
	 * 取出前100条数据
	 * 根据榜单id和周数，并且id不一致
	 * @param entity
	 * @return
	 */
	List<RankingListWeekEntity> getRankingListByWeekAndRidTop100( RankingListWeekEntity entity);
	/**
	 * 物理删除
	 * 根据id
	 * @param id
	 * @return
	 */
	Integer physicalDeleteById(@Param("id") Long id);
	
	
}
