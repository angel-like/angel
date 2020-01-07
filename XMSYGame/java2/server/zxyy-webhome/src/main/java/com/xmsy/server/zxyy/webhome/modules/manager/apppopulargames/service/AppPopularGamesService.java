package com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.AppPopularGamesParam;
import com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.entity.AppPopularGamesEntity;


/**
 * APP热门游戏
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-04 11:37:38
 */
public interface AppPopularGamesService extends IService<AppPopularGamesEntity> {

	List<AppPopularGamesParam> selectListForApp();
	List<Long> selectListForAppReturnIdList(List<Long> recentGameId,List<Long> games);
	List<Long> selectListForAppHotgamesList();

	List<Long> selectListForAppRecentgames(Long userId,List<Long> games);

	List<Long> selectListForAppNewgemes(List<Long> recentGameId,List<Long> hotGameId,List<Long> games);

	List<Long> selectSoonList();
	List<Map<String,Object>> selectsoongamesList();
}

