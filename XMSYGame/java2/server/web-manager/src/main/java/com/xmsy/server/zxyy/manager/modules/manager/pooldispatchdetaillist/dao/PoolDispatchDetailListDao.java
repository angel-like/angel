package com.xmsy.server.zxyy.manager.modules.manager.pooldispatchdetaillist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchdetaillist.entity.PoolDispatchDetailListEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistday.entity.RankingListDayEntity;

/**
 * 派奖明细记录表
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Mapper
public interface PoolDispatchDetailListDao extends BaseMapper<PoolDispatchDetailListEntity> {
	List<RankingListDayEntity>	statisticsPieAwardByDay(@Param("startTime") String startTime,
			@Param("endTime") String endTime);
}
