package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller;

import java.util.HashMap;
import java.util.Map;

import com.xmsy.common.define.constant.KaiYuanGameRoomEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server.GameInfoInterface;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏信息缓存
 * 
 * @author adu
 * @date 2019-02-25 18:04:27
 */
@Slf4j
@Component
public class GameInfoComponent {

	@Autowired
	private LocalContentCache localContentCache;
	
	private static int GAME_KEY=1;
	private static int GRADE_KEY=2;
	private static int ROOM_KEY=3;

	/**
	 * 根据id取得游戏名称
	 * 
	 * @param gameId
	 * @return
	 */
	public String getGameName(Long gameId) {
		return getName(GAME_KEY,gameId);
	}

	/**
	 * 根据id取得游戏场次名称
	 * 
	 * @param gradeId
	 * @return
	 */
	public String getGradeName(Long gradeId) {
		return getName(GRADE_KEY,gradeId);
	}
	/**
	 * 根据id取得游戏场次名称
	 * 
	 * @param gradeId
	 * @return
	 */
	public String getRoomName(Long roomId) {
		return getName(ROOM_KEY,roomId);
	}
	
	/**
	 * 取得游戏map
	 * 
	 * @return
	 */
	public Map<Long, String>  getGameMap() {
		return getMap(GAME_KEY);
	}

	/**
	 * 取得游戏场次map
	 * 
	 * @return
	 */
	public Map<Long, String>  getGradeMap() {
		return getMap(GRADE_KEY);
	}

	/**
	 * 取得房间map
	 * @return
	 */
	public Map<Long, String>  getRoomMap() {
		return getMap(ROOM_KEY);
	}
	/**
	 * 获取相应的名称
	 * @param key
	 * @param Id
	 * @return
	 */
	private String getName(int key,Long Id) {
		String name="";
		if(key==GAME_KEY) {
			name=localContentCache.getGameName(Id);
		}else if(key == GRADE_KEY) {
			name=localContentCache.getGradeName(Id);
		}else if(key == ROOM_KEY) {
			name=localContentCache.getRoomName(Id);
		}
		if(StringUtils.isEmpty(name)) {
			name= KaiYuanGameRoomEnum.getRoomName(Id);
			if(!StringUtils.isEmpty(name)) {
				return name;
			}
			updateMap();
		   if(key==GAME_KEY) {
				name=localContentCache.getGameName(Id);
			}else if(key == GRADE_KEY) {
				name=localContentCache.getGradeName(Id);
			}else if(key == ROOM_KEY) {
				name=localContentCache.getRoomName(Id);
			}
		}
		return name;
		
	}
	private Map<Long, String> getMap(int key){
		Map<Long, String> map =null;
		if(key==GAME_KEY) {
			map=localContentCache.getGameMap();
		}else if(key == GRADE_KEY) {
			map=localContentCache.getGradeMap();
		}else if(key == ROOM_KEY) {
			map=localContentCache.getRoomMap();
		}
		if(map == null || map.isEmpty()) {
			updateMap();
			if(key==GAME_KEY) {
				map=localContentCache.getGameMap();
			}else if(key == GRADE_KEY) {
				map=localContentCache.getGradeMap();
			}else if(key == ROOM_KEY) {
				map=localContentCache.getRoomMap();
			}
		}
		return map;
	}
	
	private void updateMap() {
		JSONObject returnObj = null;
		try {
		    returnObj = GameInfoInterface.gameInfo();
		} catch (Exception e) {
			log.error("游戏信息获取失败 error {}",e);
		}
	   if(returnObj !=null && returnObj.getInteger("code")==200
			   && returnObj.get("gameinfo") !=null) {
		   JSONArray gamaInfoList = returnObj.getJSONArray("gameinfo");
		   Map<Long, String> gameMap=new HashMap<>();
		   Map<Long, String> gradeMap=new HashMap<>();
		   Map<Long, String> roomMap=new HashMap<>();
		   if(gamaInfoList!=null && !gamaInfoList.isEmpty()) {
			   for(int i=0;i<gamaInfoList.size();i++) {
				   JSONObject obj=gamaInfoList.getJSONObject(i);
				   gameMap.put(obj.getLongValue("gameId"), obj.getString("gameName"));
				   gradeMap.put(obj.getLongValue("gradeId"), obj.getString("gradeName"));
				   roomMap.put(obj.getLongValue("roomId"), obj.getString("roomName"));
			   }
		   }
		   localContentCache.putGameMap(gameMap);
		   localContentCache.putGradeMap(gradeMap);
		   localContentCache.putRoomMap(roomMap);
		    
	   }
	}
}
