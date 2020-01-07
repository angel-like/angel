package com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.AppPopularGamesParam;
import com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.entity.AppPopularGamesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
	List<Long> selectListForAppReturnIdList(@Param("recentGameId") List<Long> recentGameId,@Param("games") List<Long> games);

	List<Long> selectListForAppRecentgames(@Param("userId") Long userId,@Param("games") List<Long> games);

	List<Long> selectListForAppNewgemes(@Param("ignore") Long [] ignore,@Param("games") List<Long> games);

	List<Long> selectListForAppHotgamesList();

	List<Long> selectSoonList();
	List<Map<String,Object>>   selectsoongamesList();
}
