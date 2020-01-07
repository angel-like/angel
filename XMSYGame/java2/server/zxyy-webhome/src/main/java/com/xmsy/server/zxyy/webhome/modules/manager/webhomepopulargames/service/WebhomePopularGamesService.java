package com.xmsy.server.zxyy.webhome.modules.manager.webhomepopulargames.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepopulargames.entity.WebhomePopularGamesEntity;


/**
 * 官网热门游戏
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-29 16:07:37
 */
public interface WebhomePopularGamesService extends IService<WebhomePopularGamesEntity> {

	/**
	 * 获取所有状态为启用的
	 */
	List<WebhomePopularGamesEntity> selectEnableListForWeb();

}

