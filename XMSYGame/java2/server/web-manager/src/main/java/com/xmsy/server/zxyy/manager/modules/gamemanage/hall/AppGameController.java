package com.xmsy.server.zxyy.manager.modules.gamemanage.hall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.service.HierarchyGameRoleService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;

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
public class AppGameController {
	@Autowired
	private UserService userService;
	@Autowired
	private HierarchyGameRoleService hierarchyGameRoleService;
	@Autowired
	private LocalContentCache localContentCache;


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

//		String hallStr = SysConstant.hallMap.get(hallId.intValue());
		String hallStr = localContentCache.getHall(hallId);
		if (hallStr == null) {
//			log.info("游戏大厅从SysConstant.hallMap取得为null");
			log.info("游戏大厅从localContentCache.getHall取得为null");
			throw new RRException(ErrorCode.GameErrorCode.GAMEHALL_IS_NULL.getErrMsg(),
					ErrorCode.GameErrorCode.GAMEHALL_IS_NULL.getCode());
		}
		JSONArray hallList = new JSONArray();
		JSONObject hallobj = JSON.parseObject(hallStr);
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
				if (null == gamesObj || null == roomsObj.getJSONArray("games")) {
					continue;
				}
				if (!gameIds.contains(gamesObj.getLong("id"))) {
					// 如果id不在该用户游戏ID中就删除
					gameIter.remove();
				}
			}
		}
		return R.ok().put("data", ImmutableMap.of("hall", hallList));
	}

}
