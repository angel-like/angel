package com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.app.configuration.param.AppPopularGamesParam;
import com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.dao.AppPopularGamesDao;
import com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.entity.AppPopularGamesEntity;
import com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.service.AppPopularGamesService;


@Service("appPopularGamesService")
public class AppPopularGamesServiceImpl extends ServiceImpl<AppPopularGamesDao, AppPopularGamesEntity> implements AppPopularGamesService {

	@Override
	public List<AppPopularGamesParam> selectListForApp() {
		// TODO Auto-generated method stub
		return baseMapper.selectListForApp();
	}

	@Override
	public void deleteByGameId(Long id) {
		baseMapper.deleteByGameId(id);
		
	}


}
