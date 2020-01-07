package com.xmsy.server.zxyy.game.modules.manager.gamegrade.service.impl;

import java.io.Serializable;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.game.cache.EhCacheName;
import com.xmsy.server.zxyy.game.modules.manager.gamegrade.dao.GameGradeDao;
import com.xmsy.server.zxyy.game.modules.manager.gamegrade.entity.GameGradeEntity;
import com.xmsy.server.zxyy.game.modules.manager.gamegrade.service.GameGradeService;

@Service("gameGradeService")
public class GameGradeServiceImpl extends ServiceImpl<GameGradeDao, GameGradeEntity> implements GameGradeService {
	
	
//	@Resource
//	private ReloadRedis reloadRedis;

	@Override
	@CacheEvict(value = { EhCacheName.GAME_INFO_CACHE, EhCacheName.ROOM_CACHE, EhCacheName.HALL_CACHE,
			EhCacheName.GRADE_INFO_CACHE }, allEntries = true)
	public boolean insert(GameGradeEntity entity) {
		//mqClient.gamePush();
		boolean flag = super.insert(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}
	@Override
	@CacheEvict(value = { EhCacheName.GAME_INFO_CACHE, EhCacheName.ROOM_CACHE, EhCacheName.HALL_CACHE,
			EhCacheName.GRADE_INFO_CACHE }, allEntries = true)
	public boolean updateById(GameGradeEntity entity) {
		//mqClient.gamePush();
		boolean flag = super.updateById(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}


	@Override
	@CacheEvict(value = { EhCacheName.GAME_INFO_CACHE, EhCacheName.ROOM_CACHE, EhCacheName.HALL_CACHE,
			EhCacheName.GRADE_INFO_CACHE }, allEntries = true)
	public boolean deleteById(Serializable id) {
		//mqClient.gamePush();
		boolean flag = super.deleteById(id);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@Cacheable(value = EhCacheName.GRADE_INFO_CACHE, key = "#id")
	public GameGradeEntity selectById(Serializable id) {
		return super.selectById(id);
	}

}
