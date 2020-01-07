package com.xmsy.server.zxyy.manager.cache;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class LocalContentCache {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private ValueOperations<String, Object> valueOperations;

	public static final String VERIFICATION_PREFIX = "VerificationCode:";
	public static final long VERIFICATION_CODE_TIME_OUT = 2; //
	public static final long TOKEN_TIME_OUT = 120; // token过期时间
	public static final long DEFAULT_TIME_OUT = 360; // 默认过期时间
	public static final long APP_PACK_TIME_OUT=900;//app打包过期时间  这里的单位： 秒
	
	public static final String GAME_ROOM_KEY = "gameserver:game:room";
	public static final String GAME_KEY = "gameserver:game";
	public static final String GRADE_KEY = "gameserver:grade";
	public static final String ROOM_KEY = "gameserver:room";
	public static final String HALL_KEY = "hallserver:hall";
	public static final String HALLADDRESS_KEY = "hallserver:hallAddress";
	public static final String VIP_KEY_PREFIX = "webserver:vip:";
	public static final String HIERARCHY_KEY_PREFIX= "webserver:hierarchy:";

	public void put(String key, Object value) {
		valueOperations.set(key, value, DEFAULT_TIME_OUT, TimeUnit.MINUTES);
	}

	public Object get(String key) {
		return valueOperations.get(key);
	}

	public void putToken(String key, Object value) {
		valueOperations.set(key, value, TOKEN_TIME_OUT, TimeUnit.MINUTES);
	}
	
	public Object getToken(String key) {
		Object object = valueOperations.get(key);
		if (null != object) {
			valueOperations.set(key, object, TOKEN_TIME_OUT, TimeUnit.MINUTES);
		}
		return object;
	}
	
	public void putAPPPack(String key, Object value) {
		valueOperations.set(key, value, APP_PACK_TIME_OUT, TimeUnit.SECONDS);
	}
	public Object getAPPPack(String key) {
		Object object = valueOperations.get(key);
		/*if (null != object) {
			valueOperations.set(key, object, APP_PACK_TIME_OUT, TimeUnit.SECONDS);
		}*/
		return object;
	}
	public void remove(String key) {
		redisTemplate.delete(key);
	}

	public void putPhoneVerificationCode(String key, Object value) {
		valueOperations.set(key, value, VERIFICATION_CODE_TIME_OUT, TimeUnit.MINUTES);
	}

	public Object getPhoneVerificationCode(String key) {
		return valueOperations.get(VERIFICATION_PREFIX + key);
	}

	public boolean removePhoneVerificationCode(String key) {
		return redisTemplate.delete(VERIFICATION_PREFIX + key);
	}
	@SuppressWarnings("unchecked")
	public Map<Long, String> getMap(String key) {
		Object obj = valueOperations.get(key);
		return obj==null?null:(Map<Long, String>)obj;
	}
	public String getNameForMap(String key,Long id) {
		Map<Long, String> map=getMap(key);
		if(map==null ||map.get(id)==null) {
			return "";
		}
		return map.get(id);
	}
	
	/**
	 * 游戏id与房间id默认Map
	 */
	public void putGameRoomMap(Object value) {
		valueOperations.set(GAME_ROOM_KEY, value);
	}
	public Map<Long, String> getGameRoomMap() {
		 return getMap(GAME_ROOM_KEY);
	}
	/**
	 * 游戏名称默认Map
	 */
	public void putGameMap(Object value) {
		valueOperations.set(GAME_KEY, value);
	}
	public Map<Long, String> getGameMap() {
		return getMap(GAME_KEY);
	}
	public String getGameName(Long gameId) {
		return getNameForMap(GAME_KEY,gameId);
	}
	/**
	 * 游戏场次默认Map
	 */
	public void putGradeMap(Object value) {
		valueOperations.set(GRADE_KEY, value);
	}
	public Map<Long, String> getGradeMap() {
		return getMap(GRADE_KEY);
	}
	public String getGradeName(Long gradeId) {
		return getNameForMap(GRADE_KEY,gradeId);
	}
	/**
	 * 游戏房间默认Map
	 */
	public void putRoomMap(Object value) {
		valueOperations.set(ROOM_KEY, value);
	}
	public Map<Long, String> getRoomMap() {
		return getMap(ROOM_KEY);
	}
	public String getRoomName(Long roomId) {
		return getNameForMap(ROOM_KEY,roomId);
	}
	/**
	 * 游戏大厅默认Map
	 */
	public void putHallMap(Object value) {
		valueOperations.set(HALL_KEY, value);
	}
	public Map<Long, String> getHallMap() {
		return getMap(HALL_KEY);
	}
	public String getHall(Long hallId) {
		return getNameForMap(HALL_KEY,hallId);
	}
	/**
	 * 游戏大厅Ip和端口默认Map
	 */
	public void putHallAddressMap(Object value) {
		valueOperations.set(HALLADDRESS_KEY, value);
	}
	public Map<Long, String> getHallAddressMap() {
		return getMap(HALLADDRESS_KEY);
	}
	public String getHallAddress(Long hallId) {
		return getNameForMap(HALLADDRESS_KEY,hallId);
	}
	
	/**
	 * Vip名称
	 */
	public void putVipName(Long id,Object name) {
		valueOperations.set(VIP_KEY_PREFIX+id, name);
	}
	public String getVipName(Long id) {
		Object obj = valueOperations.get(VIP_KEY_PREFIX+id);
		return obj==null?null:obj.toString();
	}
	
	public void removeVipName(Long id) {
		this.remove(VIP_KEY_PREFIX+id);
	}
	
	/**
	 * 层级名称
	 */
	public void putHierarchypName(Long id,Object name) {
		valueOperations.set(HIERARCHY_KEY_PREFIX+id, name);
	}
	public String getHierarchyName(Long id) {
		Object obj = valueOperations.get(HIERARCHY_KEY_PREFIX+id);
		return obj==null?null:obj.toString();
	}
	public void removeHierarchyName(Long id) {
		this.remove(HIERARCHY_KEY_PREFIX+id);
	}
	
	

}
