package com.xmsy.server.zxyy.webhome.modules.gamemanage.hall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.ImmutableMap;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.service.AppPopularGamesService;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.service.HierarchyGameRoleService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏记录
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-20 16:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/game")
public class AppGameController2 {
	@Autowired
	private UserService userService;
	@Autowired
	private HierarchyGameRoleService hierarchyGameRoleService;
	@Autowired
	private AppPopularGamesService appPopularGamesService;
	@Autowired
	private LocalContentCache localContentCache;
	
	private static Map<Long, String> roomKey= ImmutableMap.<Long, String>builder()
			.put(1l,"match")
			.put(2l,"room")
			.put(3l,"hundred")
			.put(4l,"tiger")
			.put(5l,"tiger")
			.put(23l,"experience")
			.build();  
			
//	=new String[]{"","hundredIds","match","room","tiger","","",""};
	/**
	 * 游戏信息
	 */
	@GetMapping("/gameinfo")
	public R gamelist(@RequestParam(value = "hallId") Long hallId, @RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity entity = userService.selectById(userId);
		// 判断用户是否获取失败
		if (entity == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 通过层级ID获取相应游戏ID
		Long hierarchyId = entity.getHierarchyId();
		List<HierarchyGameRoleEntity> list = hierarchyGameRoleService.selectList(
				new EntityWrapper<HierarchyGameRoleEntity>(new HierarchyGameRoleEntity().setHierarchyId(hierarchyId)));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.GameErrorCode.GAMEROLE_IS_NULL.getErrMsg(),
					ErrorCode.GameErrorCode.GAMEROLE_IS_NULL.getCode());
		}
		List<Long> gameIds = new ArrayList<>();
		for (HierarchyGameRoleEntity roleEntity : list) {
			gameIds.add(roleEntity.getGameId());
		}

		JSONArray hallList = new JSONArray();
		JSONObject hallobj =getHall(hallId);
		hallList.add(hallobj);
		if (hallobj.getJSONArray("rooms") == null) {
			return R.ok().put("data", ImmutableMap.of("hall", hallList));
		}
		JSONArray roomsArry = hallobj.getJSONArray("rooms");
		// 迭代房间列表
		Iterator<Object> roomIter = roomsArry.iterator();
		Map<String, Object> rooms = new HashMap<>();
		@SuppressWarnings("unused")
		List<Long> roomGames = new ArrayList<>();
		//推荐游戏
		Map<String, Object> recomgames = new HashMap<>();
		JSONArray games = new JSONArray();
		List<Long> gamesId = new ArrayList<>();
		List<Map<String, Object>> maps = appPopularGamesService.selectsoongamesList();
		while (roomIter.hasNext()) {
			JSONObject roomsObj = (JSONObject) roomIter.next();
			if (null == roomsObj || null == roomsObj.getJSONArray("games")) {
				continue;
			}
			
			JSONArray gamesArry = roomsObj.getJSONArray("games");


			// 迭代游戏列表
			List<Long> gameList = new ArrayList<>();
			Iterator<Object> gameIter = gamesArry.iterator();
			while (gameIter.hasNext()) {
				JSONObject gamesObj = (JSONObject) gameIter.next();
				if (null == gamesObj ) {
					continue;
				}
				if(gamesObj.getIntValue("provider") >0){
					gamesObj.put("grades",null);
				}

				Long gameId=gamesObj.getLong("id");
				if (gameIds.contains(gameId)) {
					gameList.add(gameId);
					if(!gamesId.contains(gameId)) {
						gamesId.add(gameId);

						games.add(gamesObj);
					}
					
				}
			}
			String roomK=roomKey.get(roomsObj.getLong("id"));
			for (int i = 0; i <maps.size() ; i++) {
				Map<String, Object> map = maps.get(i);
				Long   gameId	 =     (Long) map.get("id");
				Long   roomId = (Long)  map.get("roomId");
				String s = roomKey.get(roomId);
				if(s.equals(roomK)){
					gameList.add(gameId);
				}


			}
			if(!StringUtils.isBlank(roomK)) {
				if(rooms.get(roomK)!=null) {
                    @SuppressWarnings("unchecked")
					List<Long> gameListTmp  =(List<Long>) rooms.get(roomK);
                    gameListTmp.addAll(gameList);
                    rooms.put(roomK,gameListTmp);
                }else {
                    rooms.put(roomK,gameList);
                }
			}
			if("room".equals(roomK)){
				//房卡游戏
				roomGames = gameList;
			}
			
		}
		Map<String, Object> data = new HashMap<>();
		//查询最近游戏
		List<Long> recentGameId = appPopularGamesService.selectListForAppRecentgames(userId,gameIds);

		//热门游戏IDlist
		List<Long> hotGameId = appPopularGamesService.selectListForAppReturnIdList(recentGameId,gameIds);
        recomgames.put("recentgames",recentGameId);
        recomgames.put("hotgames",hotGameId);

		//最新游戏

        List<Long> newGameId = appPopularGamesService.selectListForAppNewgemes(recentGameId,hotGameId,gameIds);

        recomgames.put("newGameId",newGameId);
		data.put("recomgames", recomgames);
		List<Long> soonId = new ArrayList<>();
		for (int i = 0; i <maps.size() ; i++) {
			Map<String, Object> map = maps.get(i);
			Long   gameId	 =     (Long) map.get("id");
			soonId.add(gameId);
		}
		recomgames.put("soongames",soonId);
		List<Long> hot = appPopularGamesService.selectListForAppHotgamesList();
		data.put("rooms", rooms);
		data.put("hotgames", hot);
		data.put("games", games);
		return R.ok().put("data", data);
//		return R.ok().put("data", ImmutableMap.of("hall", hallList));
	}
	/**
	 * 获取大厅信息
	 * @param hallId
	 * @return
	 */
	private JSONObject getHall(Long hallId) {
		String hallStr = localContentCache.getHall(hallId);
		if (hallStr == null || "".equals(hallStr)) {
			JSONObject hallReturnObj = null;
		   try {
			   hallReturnObj = GameInfoInterface.hallList();
			} catch (Exception e) {
				log.info("hallId : {}", hallId);
				log.error("大厅列表获取失败 error {}",e);
			}
		   //log.info("hallReturnObj 大厅列表返回结果 {}", hallReturnObj);
		   Integer index=null;
		   if (hallReturnObj !=null && hallReturnObj.getInteger("code") != null && hallReturnObj.getInteger("code") == ResultDef.SUCCESS) {
				if (hallReturnObj.getJSONObject("data").getJSONArray("hall") != null) {
					JSONArray hallArry = hallReturnObj.getJSONObject("data").getJSONArray("hall");
					Map<Long, String> hallMap=new HashMap<Long, String>();
					if (hallArry.size()>0) {
						for(int i=0;i<hallArry.size();i++) {
							JSONObject hallobj = hallArry.getJSONObject(i);
							if(hallId.equals(hallobj.getLong("id"))) {
								index=i;
							}
							hallMap.put(hallobj.getLong("id"), hallobj.toJSONString());
						}
					}
					localContentCache.putHallMap(hallMap);
//					log.info("redis hallmap {}",localContentCache.getHallMap());
					 if(index!=null) {
						   return hallArry.getJSONObject(index);
				    }
				}
			}
			throw new RRException(ErrorCode.GameErrorCode.GAMEHALL_IS_NULL.getErrMsg(),
					ErrorCode.GameErrorCode.GAMEHALL_IS_NULL.getCode());
		   
		}
		return JSON.parseObject(hallStr);
	}

}
