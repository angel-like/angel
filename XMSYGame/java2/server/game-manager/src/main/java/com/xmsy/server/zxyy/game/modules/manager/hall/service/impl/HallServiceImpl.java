package com.xmsy.server.zxyy.game.modules.manager.hall.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.collect.Maps;
import com.xmsy.server.zxyy.game.cache.EhCacheName;
import com.xmsy.server.zxyy.game.modules.manager.hall.dao.HallDao;
import com.xmsy.server.zxyy.game.modules.manager.hall.entity.HallEntity;
import com.xmsy.server.zxyy.game.modules.manager.hall.service.HallService;
import com.xmsy.server.zxyy.game.modules.manager.room.service.RoomService;
import com.xmsy.server.zxyy.game.modules.web.hall.result.HallResult;
import com.xmsy.server.zxyy.game.modules.web.room.param.RoomResult;

@Service("hallService")
public class HallServiceImpl extends ServiceImpl<HallDao, HallEntity> implements HallService {
	

	@Resource
	private RoomService roomService;
	
//	@Resource
//	private ReloadRedis reloadRedis;

	@Override
	@CacheEvict(value = EhCacheName.HALL_CACHE, allEntries = true)
	public boolean insert(HallEntity entity) {
		//mqClient.gamePush();
		boolean flag = super.insert(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@CacheEvict(value = EhCacheName.HALL_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		//mqClient.gamePush();
		boolean flag = super.deleteById(id);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@CacheEvict(value = EhCacheName.HALL_CACHE, allEntries = true)
	public boolean updateById(HallEntity entity) {
		//mqClient.gamePush();
		boolean flag = super.updateById(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@Cacheable(value = EhCacheName.HALL_CACHE, key = "#id")
	public HallEntity selectById(Serializable id) {
		// TODO Auto-generated method stub
		return super.selectById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.HALL_CACHE, allEntries = true)
	public Integer updateHallById(HallEntity entity) {
		//mqClient.gamePush();
		Integer flag = baseMapper.updateHallById(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}

//	@Cacheable(cacheNames = EhCacheName.HALL_CACHE, key = "'hall'")
	public Map<Long, HallResult> selectAll() throws Exception {
		String[] roomIds = null;
		// 房间集合
		Map<Long, RoomResult> roomMap = roomService.selectAll();
		// 大厅集合
		Map<Long, HallResult> hallMap = Maps.newConcurrentMap();
		// 数据库查询大厅集合
		List<HallEntity> hallColl = super.selectList(new EntityWrapper<HallEntity>(new HallEntity()));
		HallResult hallResult = null;
		RoomResult room = null;
		// 为每个大厅对象设置对应的房间集合
		for (HallEntity entity : hallColl) {
			roomIds = entity.getRoomIds().split(",");
			hallResult = new HallResult();
			hallResult.setId(entity.getId());
			hallResult.setName(entity.getName());
			for (String roomId : roomIds) {
				if (StringUtils.isEmpty(roomId)) {
					continue;
				}
				room = roomMap.get(Long.valueOf(roomId));
				if (null == room) {
					continue;
				}
				hallResult.getRooms().add(room.clone());
			}
			hallMap.put(entity.getId(), hallResult);
		}
		return hallMap;
	}

}
