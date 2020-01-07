package com.xmsy.server.zxyy.webhome.modules.gamemanage.hall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.service.HierarchyGameRoleService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏列表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-20 16:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/game")
public class AppGameController {
	@Autowired
	private UserService userService;
	@Autowired
	private HierarchyGameRoleService hierarchyGameRoleService;
	@Autowired
	private LocalContentCache localContentCache;
			
//	=new String[]{"","hundredIds","match","room","tiger","","",""};
	/**
	 * 游戏信息
	 */
	@GetMapping("/gamelist")
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
		while (roomIter.hasNext()) {
			JSONObject roomsObj = (JSONObject) roomIter.next();
			if (null == roomsObj || null == roomsObj.getJSONArray("games")) {
				continue;
			}
			
			JSONArray gamesArry = roomsObj.getJSONArray("games");
			// 迭代游戏列表
			Iterator<Object> gameIter = gamesArry.iterator();
			while (gameIter.hasNext()) {
				JSONObject gamesObj = (JSONObject) gameIter.next();
				if (null == gamesObj ) {
					continue;
				}
				Long gameId=gamesObj.getLong("id");
				if (!gameIds.contains(gameId)) {
					// 如果id不在该用户游戏ID中就删除
					gameIter.remove();
				}
			}
		}
		return R.ok().put("data", ImmutableMap.of("hall", hallList));
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
