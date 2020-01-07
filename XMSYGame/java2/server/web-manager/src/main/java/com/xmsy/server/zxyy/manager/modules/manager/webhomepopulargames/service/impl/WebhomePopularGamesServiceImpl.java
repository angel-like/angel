package com.xmsy.server.zxyy.manager.modules.manager.webhomepopulargames.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.webhomepopulargames.dao.WebhomePopularGamesDao;
import com.xmsy.server.zxyy.manager.modules.manager.webhomepopulargames.entity.WebhomePopularGamesEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomepopulargames.service.WebhomePopularGamesService;


@Service("webhomePopularGamesService")
public class WebhomePopularGamesServiceImpl extends ServiceImpl<WebhomePopularGamesDao, WebhomePopularGamesEntity> implements WebhomePopularGamesService {

	
	/**
	 * 获取状态为启用的list
	 */
	@Override
	public List<WebhomePopularGamesEntity> selectEnableListForWeb() {
		return baseMapper.selectEnableListForWeb();
	}


}
