package com.xmsy.server.zxyy.manager.modules.manager.webhomepopulargames.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomepopulargames.entity.WebhomePopularGamesEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 官网热门游戏
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-29 16:07:37
 */
@Mapper
public interface WebhomePopularGamesDao extends BaseMapper<WebhomePopularGamesEntity> {

	List<WebhomePopularGamesEntity> selectEnableListForWeb();
	
}
