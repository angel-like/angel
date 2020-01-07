package com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.AppPopularGamesParam;
import com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.dao.AppPopularGamesDao;
import com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.entity.AppPopularGamesEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.service.AppPopularGamesService;


@Service("appPopularGamesService")
public class AppPopularGamesServiceImpl extends ServiceImpl<AppPopularGamesDao, AppPopularGamesEntity> implements AppPopularGamesService {

	@Override
	public List<AppPopularGamesParam> selectListForApp() {
		// TODO Auto-generated method stub
		return baseMapper.selectListForApp();
	}

	@Override
	public List<Long> selectListForAppReturnIdList(List<Long> recentGameId,List<Long> games) {
		return baseMapper.selectListForAppReturnIdList(recentGameId,games);
	}

	@Override
	public List<Long> selectListForAppHotgamesList() {
		return baseMapper.selectListForAppHotgamesList();
	}

	@Override
	public List<Long> selectListForAppRecentgames(Long userId,List<Long> games) {
		return baseMapper.selectListForAppRecentgames(userId,games);
	}

	@Override
	public List<Long> selectListForAppNewgemes(List<Long> recentGameId,List<Long> hotGameId,List<Long> games) {
		Long[] ignore= new Long[recentGameId.size()+hotGameId.size()];

		System.arraycopy(recentGameId.toArray(), 0, ignore, 0, recentGameId.size());

		System.arraycopy(hotGameId.toArray(), 0, ignore, recentGameId.size(), hotGameId.size());
		return baseMapper.selectListForAppNewgemes(ignore, games);
	}

	@Override
	public List<Long> selectSoonList() {
		return baseMapper.selectSoonList();
	}
	@Override
	public List<Map<String,Object>> selectsoongamesList() {
		return baseMapper.selectsoongamesList();
	}

}
