package com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.configuration.param.AppPopularGamesParam;
import com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.entity.AppPopularGamesEntity;


/**
 * APP热门游戏
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-04 11:37:38
 */
public interface AppPopularGamesService extends IService<AppPopularGamesEntity> {

	List<AppPopularGamesParam> selectListForApp();
	
	/**
	 * 根据游戏id删除APP热门游戏
	 * @param id
	 */
	void deleteByGameId(Long id);

}

