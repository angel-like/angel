package com.xmsy.server.zxyy.game.cache;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
@Component
public class LocalContentCache {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private ValueOperations<String, Object> valueOperations;

	public static final String GAME_ROOM_KEY = "gameserver:game:room";
	public static final String GAME_KEY = "gameserver:game";
	public static final String GRADE_KEY = "gameserver:grade";
	public static final String ROOM_KEY = "gameserver:room";
	public static final String HALL_KEY = "hallserver:hall";
	public static final String HALLADDRESS_KEY = "hallserver:hallAddress";
	public static final String GAME_ROOM_GRADE_KEY = "gameserver:rgdefault:";
	public static final String GAME_GRADE_KEY = "gameserver:ggfault:";

	public final static long expireTime = 10; //分钟
	public Cache<String, Object> cache = CacheBuilder.newBuilder()
			.expireAfterAccess(expireTime, TimeUnit.MINUTES).removalListener(new MyRemovalListener()).build(new CacheLoader<String, Object>() {
				public Object load(String token) throws Exception {
					return null;
				}
			});

	private static class MyRemovalListener implements RemovalListener<String, Object> {
	    @Override
	    public void onRemoval(RemovalNotification<String, Object> notification) {
	        //doSomething();
	    	System.out.println("cache监听事件");
	    	String tips = String.format("key=%s,value=%s,reason=%s", notification.getKey(), notification.getValue(), notification.getCause());
	    	System.out.println(tips);
	    }
	}

	/**
	 * 游戏名称默认Map
	 */
	public void putGameMap(Object value) {
		valueOperations.set(GAME_KEY, value);
	}
	
	/**
	 * 游戏房间场次默认信息Map
	 */
	public void putRoomGradeInfo(Long roomId,Long gradeId,Object info) {
		valueOperations.set(GAME_ROOM_GRADE_KEY+roomId+":"+gradeId, info);
		//gameserver:rgdefault:1:1
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getRoomGradeInfo(Long roomId,Long gradeId) {
		Object obj = valueOperations.get(GAME_ROOM_GRADE_KEY+roomId+":"+gradeId);
		return obj==null?null:(Map<String, Object>)obj;
	}
	
	/**
	 * 游戏场次默认信息Map
	 */
	public void putGameGradeInfo(Long gameId,Long gradeId,Object info) {
		valueOperations.set(GAME_GRADE_KEY+gameId+":"+gradeId, info);
		//gameserver:ggdefault:1:1
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getGameRoomGradeInfo(Long gameId,Long gradeId) {
		Object obj = valueOperations.get(GAME_GRADE_KEY+gameId+":"+gradeId);
		return obj==null?null:(Map<String, Object>)obj;
	}
	
	/**
	 * 游戏场次默认Map
	 */
	public void putGradeMap(Object value) {
		valueOperations.set(GRADE_KEY, value);
	}
	/**
	 * 游戏房间默认Map
	 */
	public void putRoomMap(Object value) {
		valueOperations.set(ROOM_KEY, value);
	}
	/**
	 * 游戏id与房间id默认Map
	 */
	public void putGameRoomMap(Object value) {
		valueOperations.set(GAME_ROOM_KEY, value);
	}
	/**
	 * 游戏大厅默认Map
	 */
	public void putHallMap(Object value) {
		valueOperations.set(HALL_KEY, value);
	}
	/**
	 * 游戏大厅Ip和端口默认Map
	 */
	public void putHallAddressMap(Object value) {
		valueOperations.set(HALLADDRESS_KEY, value);
	}
	/**
	 *
	 */
	public void setVal(String key,Object value) {
		valueOperations.set(key, value);
	}
	/**
	 * 按照 key 删除对应的redis数据
	 * @param key
	 */
	public void remove(String key) {
		redisTemplate.delete(key);
	}
	public Object getVal(String key) {
		return valueOperations.get(key);
	}

	public Map<Long, String> getRoomMap() {
		return getMap(ROOM_KEY);
	}

	@SuppressWarnings("unchecked")
	public Map<Long, String> getMap(String key) {
		return (Map<Long, String>) valueOperations.get(key);
	}
}
