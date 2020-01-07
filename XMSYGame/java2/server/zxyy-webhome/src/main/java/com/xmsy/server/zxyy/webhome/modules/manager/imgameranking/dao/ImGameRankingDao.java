package com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.entity.ImGameRankingEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGameRankingResult;

/**
 * 33推荐热门游戏排行
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-24 11:10:56
 */
@Mapper
public interface ImGameRankingDao extends BaseMapper<ImGameRankingEntity> {

	List<ImGameRankingResult> selectListForWib();

}
