package com.xmsy.server.zxyy.game.modules.manager.room.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.cache.EhCacheName;
import com.xmsy.server.zxyy.game.common.utils.SpringUtil;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.result.GameResult;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;
import com.xmsy.server.zxyy.game.modules.manager.hall.entity.HallEntity;
import com.xmsy.server.zxyy.game.modules.manager.hall.service.HallService;
import com.xmsy.server.zxyy.game.modules.manager.room.dao.RoomDao;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.RoomEntity;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.param.RoomVo;
import com.xmsy.server.zxyy.game.modules.manager.room.service.RoomService;
import com.xmsy.server.zxyy.game.modules.web.game.param.GameInfoResult;
import com.xmsy.server.zxyy.game.modules.web.room.param.RoomResult;

@Service("roomService")
public class RoomServiceImpl extends ServiceImpl<RoomDao, RoomEntity> implements RoomService {

	@Resource
	private GameInfoService gameInfoService;
//	@Resource
//	private ReloadRedis reloadRedis;
	
	@Override
	@CacheEvict(value = { EhCacheName.ROOM_CACHE, EhCacheName.HALL_CACHE }, allEntries = true)
	public boolean insert(RoomEntity entity) {
		//mqClient.gamePush();
		boolean flag = super.insert(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@CacheEvict(value = { EhCacheName.ROOM_CACHE, EhCacheName.HALL_CACHE }, allEntries = true)
	public boolean deleteById(Serializable id) {
		//mqClient.gamePush();
		boolean flag = super.deleteById(id);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@CacheEvict(value = { EhCacheName.ROOM_CACHE, EhCacheName.HALL_CACHE }, allEntries = true)
	public boolean updateById(RoomEntity entity) {
		//mqClient.gamePush();
		boolean flag = super.updateById(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@Cacheable(value = EhCacheName.ROOM_CACHE, key = "#id")
	public RoomEntity selectById(Serializable id) {
		return super.selectById(id);
	}

	@Override
	@CacheEvict(value = { EhCacheName.ROOM_CACHE, EhCacheName.HALL_CACHE }, allEntries = true)
	public Integer updateRoomById(RoomEntity entity) {
		//mqClient.gamePush();
		Integer flag = this.baseMapper.updateRoomById(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.ROOM_CACHE, key = "'roomMap'")
	public Map<Long, RoomEntity> getRoomMap() {
		List<RoomEntity> roomColl = super.selectList(new EntityWrapper<RoomEntity>(new RoomEntity()));
		Map<Long, RoomEntity> result = Maps.newConcurrentMap();
		for (RoomEntity room : roomColl) {
			result.put(room.getId(), room);
		}
		return result;
	}

//	@Cacheable(cacheNames = EhCacheName.ROOM_CACHE, key = "'room'")
	public Map<Long, RoomResult> selectAll() throws Exception {
		String[] gameIds = null;
		// 游戏集合
		Map<Long, GameResult> gameInfoMap = gameInfoService.selectGameInfoList();
		// 游戏集合
		Map<Long, GameInfoResult> gameInfoResultMap = null;
		// 房间集合
		Map<Long, RoomResult> roomMap = Maps.newConcurrentMap();
		// 数据库查询房间列表
		List<RoomEntity> roomColl = super.selectList(new EntityWrapper<RoomEntity>(new RoomEntity()));
		GameInfoResult gameInfoResult = null;
		Long gameId = null;
		RoomResult room = null;
		// 为每个房间设置对应的游戏集合
		for (RoomEntity entity : roomColl) {
			gameIds = entity.getGameIds().split(",");
			gameInfoResultMap = gameInfoService.selectGameByIds(Lists.newArrayList(gameIds));
			room = new RoomResult();
			room.setId(entity.getId());
			room.setName(entity.getName());
			roomMap.put(entity.getId(), room);
			if (null == gameInfoResultMap) {
				continue;
			}
			for (String id : gameIds) {
				if (StringUtils.isEmpty(id) || null == gameInfoMap.get(Long.valueOf(id))) {
					continue;
				}
				gameId = gameInfoMap.get(Long.valueOf(id)).getGameId();
				if (null == gameId) {
					continue;
				}
				gameInfoResult = gameInfoResultMap.get(gameId);
				if (null == gameInfoResult) {
					continue;
				}
				room.getGames().add(gameInfoResult);
			}

		}
		return roomMap;
	}

	@Override
	public Page<RoomVo> getRooms(RoomEntity room, PageParam pageParam) {
		Page<RoomVo> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getRooms(room, pageParam));
	}

	@Override
	public void deleteRoom(Long id) {
		// 删除房间
		baseMapper.deleteById(id);
		String idStr = String.valueOf(id);
		HallService hallService = SpringUtil.getApplicationContext().getBean(HallService.class);
		// 删除大厅关联的房间
		Collection<HallEntity> hallColl = hallService.selectList(new EntityWrapper<HallEntity>());
		List<String> hallList = null;
		for (HallEntity hall : hallColl) {
			hallList = Lists.newArrayList(hall.getRoomIds().split(","));
			if (hallList.contains(idStr)) {
				hallList.remove(idStr);
				hall.setRoomIds(Joiner.on(",").join(hallList));
				hallService.updateById(hall);
			}
		}

	}

}
