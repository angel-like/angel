package com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.app.configuration.param.AppPopularGamesParam;
import com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.entity.AppPopularGamesEntity;

/**
 * APP热门游戏
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-04 11:37:38
 */
@Mapper
public interface AppPopularGamesDao extends BaseMapper<AppPopularGamesEntity> {

	List<AppPopularGamesParam> selectListForApp();
	
	void deleteByGameId(@Param("id") Long id);
	
}
