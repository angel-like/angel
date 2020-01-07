package com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.entity.ImPrizePoolRankingEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGamePrizeRankingResult;

/**
 * 33推荐热门游戏排行
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-24 15:25:30
 */
@Mapper
public interface ImPrizePoolRankingDao extends BaseMapper<ImPrizePoolRankingEntity> {

	List<ImGamePrizeRankingResult> selectListForWib();

}
