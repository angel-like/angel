package com.xmsy.server.zxyy.robot.modules.manager.game.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.modules.gamemanage.GameInfoInterface;
import com.xmsy.server.zxyy.robot.modules.manager.game.entity.GameParams;
import com.xmsy.server.zxyy.robot.modules.manager.game.service.GameInterfaceService;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.entity.RobotProfitRecordEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.service.RobotProfitRecordService;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 游戏信息
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-19 15:45:35
 */
@Slf4j
@RestController
@RequestMapping("gameinfo/gameinfo")
public class GameInfoController {
    @Autowired
    private RobotProfitRecordService RobotProfitRecordService;
	/**
	 * 所有游戏下拉	游戏--大厅
	 */
	@RequestMapping("/gameSelectForRobot")
	//@RequiresPermissions("gameinfo:gameinfo:gameSelectForRobot")
	public R gameSelectForRobot() {
		JSONObject returnObj = GameInfoInterface.gameSelectForRobot();
//		log.debug("returnObj 游戏下拉返回结果 {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			return R.ok().put("data", returnObj.get("data"));
		}
		return R.ok().put("data", returnObj);
	}

	/**
	 * 所有游戏下拉	游戏
	 */
	@RequestMapping("/gameSelect")
	//@RequiresPermissions("gameinfo:gameinfo:gameSelect")
	public R gameSelect() {
		JSONObject returnObj = GameInfoInterface.gameSelect();
//		log.debug("returnObj 游戏下拉返回结果 {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			return R.ok().put("data", returnObj.get("data"));
		}
		return R.ok().put("data", returnObj);
	}

	/**
	 * 所有房间下拉
	 */
	@RequestMapping("/roomSelect")
	//@RequiresPermissions("gameinfo:gameinfo:roomSelect")
	public R roomSelect() {
		JSONObject returnObj = GameInfoInterface.roomSelect();
//		log.debug("returnObj 房间下拉返回结果 {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			return R.ok().put("data", returnObj.get("data"));
		}
		return R.ok().put("data", returnObj);
	}


	/**
	 * 游戏列表(有机器人配置)
	 */
	@RequestMapping("/robotGameList")
	//	@RequiresPermissions("gameinfo:gameinfo:robotGameList")
	public R robotGameList(@RequestParam("roomId") Long roomId, PageParam pageParam) {
		Long profit = 0L;
		int limit = pageParam.getLimit();
		int page = pageParam.getPage();
		// 获取游戏大厅接口，获取到游戏列表
		JSONObject gamesObj = GameInfoInterface.gameSelectForRobot();
		List<GameParams> list = new ArrayList<GameParams>();
		JSONObject obj = new JSONObject();
		obj.put("roomId", roomId);
		// 获取所有游戏机器人
		JSONObject returnObj = GameInterfaceService.robotGameList(obj);
//		log.debug("robotGameList 游戏列表 {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			JSONArray arry = returnObj.getJSONArray("gamelist");
			Multimap<Long, Integer> map = ArrayListMultimap.create();
			if (!CollectionUtils.isEmpty(arry)) {
				for (int i = 0; i < arry.size(); i++) {
					JSONObject gameObj = arry.getJSONObject(i);
					// 获取所有房间的机器人
					if (null != gameObj && null != gameObj.getLong("gameId")) {
						map.put(gameObj.getLong("gameId"), gameObj.getInteger("robotCount"));

					}
				}
			}
			Set<Long> gameIds = new HashSet<Long>();
			// 获取到所有开启机器人的游戏ID列表
			if (!map.isEmpty()) {
				map.keys();
				gameIds = map.keySet();
				if (!CollectionUtils.isEmpty(gameIds)) {
					// 遍历所有游戏ID
					for (Long gameId : gameIds) {
						GameParams entity = new GameParams();
						entity.setId(gameId);
						// ===========================start获取游戏名称============================
						if (gamesObj.getInteger("code") != null && gamesObj.getInteger("code") == ResultDef.SUCCESS) {
							// 获取到游戏接口
							JSONArray gameJson = gamesObj.getJSONArray("data");
							// 便利游戏列表
							for (int i = 0; i < gameJson.size(); i++) {
								// 每个游戏对象
								JSONObject gameObj = (JSONObject) gameJson.get(i);
								// 如果游戏对象不为空，游戏ID和场次Id不为空
								if (null != gameObj && null != gameObj.getLong("gameId")
										&& null != gameObj.getLong("gradeId")) {
									// 如果游戏ID和场次Id相同就将游戏名称取出，并跳出游戏列表gameJson的循环
									if (gameObj.getLong("gameId") .equals(gameId) ) {
										entity.setName(gameObj.getString("gameName"));
										break;
									}
								}
							}
						}
						// ===========================end获取游戏名称============================

						// ===========================start统计每个游戏机器人数量============================
						List<Integer> nums = (List<Integer>) map.get(gameId);
						Integer number = 0;
						for (Integer num : nums) {
							if (num > 0) {
								number += num;
							}
						}
						entity.setNum(number);
						Long profitCoin = RobotProfitRecordService.countProfitCoin(new RobotProfitRecordEntity().setGameId(gameId));

						entity.setProfitCoin(profitCoin == null ? 0 : profitCoin);

						profit = profitCoin == null ? profit : profit + profitCoin;
						// ===========================end统计每个游戏机器人数量============================
						list.add(entity);
					}
				}

			}

			//去除未匹配到游戏名的游戏
			Iterator<GameParams> it = list.iterator();
			while (it.hasNext()) {
				GameParams next = (GameParams) it.next();
				if(StringUtils.isEmpty(next.getName())) {
					it.remove();
				}
			}
			int count = 0;
			int toIndex = 0;
			if (!CollectionUtils.isEmpty(list)) {
				count = list.size();
				if (list.size() < limit * page) {
					toIndex = list.size();
				} else {
					toIndex = limit * page;
				}
			}
			return R.ok().put("list", list.subList(limit * (page - 1), toIndex)).put("count", count).put("profit",
					profit);
		} else {
			return R.error(returnObj.getInteger("code"), returnObj.getString("msg"));
		}
	}

	/**
	 * 机器人配置
	 * 场次
	 */
	@RequestMapping("/robotGradeList")
	//	@RequiresPermissions("gameinfo:gameinfo:robotGradeList")
	public R robotGradeList(@Param("gameId") Long gameId, PageParam pageParam) {
		// 获取游戏大厅接口，获取到游戏列表
		JSONObject obj = new JSONObject();
		obj.put("start", pageParam.getLimit() * (pageParam.getPage() - 1));
		obj.put("count", pageParam.getLimit());
		obj.put("gameId", gameId);
		// 获取所有游戏机器人
		JSONObject returnObj = GameInterfaceService.robotGameList(obj);
//		log.debug("robotGameList 游戏列表 {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			if (!CollectionUtils.isEmpty(returnObj.getJSONArray("gamelist"))) {
				for (int i = 0; i < returnObj.getJSONArray("gamelist").size(); i++) {
					Long profitCoin = 0L;
					if (null != returnObj.getJSONArray("gamelist").getJSONObject(i).getLong("gradeId")) {
						RobotProfitRecordEntity entity = new RobotProfitRecordEntity();
						entity.setGameId(gameId);
						entity.setGradeId(returnObj.getJSONArray("gamelist").getJSONObject(i).getLong("gradeId"));
						profitCoin = RobotProfitRecordService.countProfitCoin(entity);
					}
					returnObj.getJSONArray("gamelist").getJSONObject(i).put("profitCoin", profitCoin);
				}
			}
			return R.ok().put("data", returnObj);
		} else {
			return R.error(returnObj.getInteger("code"), returnObj.getString("msg"));
		}
	}

	/**
	 * 获取游戏机器人配置
	 */
	@RequestMapping("/robotGameConfig")
	//	@RequiresPermissions("gameinfo:gameinfo:robotGameConfig")
	public R robotGameConfig(@RequestParam("gameId") String gameId) {
		log.debug("robotGameConfig gameId {}", gameId);
		List<JSONObject> list = new ArrayList<JSONObject>();
		// 获取游戏大厅接口，获取到游戏列表
		JSONObject obj = new JSONObject();
		obj.put("gameId", gameId);
		JSONObject returnObj = GameInterfaceService.robotGameList(obj);
//		log.debug("[robotGameConfig] 通过Id获取的游戏列表 {}", returnObj);
		if (null != returnObj.getInteger("code") && ResultDef.SUCCESS == returnObj.getInteger("code")) {
			if (!CollectionUtil.isEmpty(returnObj.getJSONArray("gamelist"))) {
				for (int i = 0; i < returnObj.getJSONArray("gamelist").size(); i++) {
					// 如果获取机器人配置的路径不为空
					log.debug("[robotGameConfig] 游戏对象 {}", returnObj.getJSONArray("gamelist").getJSONObject(i));
					if (null != returnObj.getJSONArray("gamelist").getJSONObject(i) && !StringUtils.isEmpty(
							returnObj.getJSONArray("gamelist").getJSONObject(i).getString("getRobotConfigUrl"))) {
						// 那么就通过路径获取机器人配置
						JSONObject robotConfig = GameInterfaceService.robotGameConfig(
								returnObj.getJSONArray("gamelist").getJSONObject(i).getString("getRobotConfigUrl"));
						log.debug("[robotGameConfig] 通过路径获取的配置 {}", robotConfig);
						if (null != robotConfig.getInteger("code")
								&& ResultDef.SUCCESS == robotConfig.getInteger("code")) {
							//将机器人提交路径保存到实体中
							robotConfig.put("putRobotConfigUrl",
									returnObj.getJSONArray("gamelist").getJSONObject(i).getString("putRobotConfigUrl"));
							robotConfig.put("name", returnObj.getJSONArray("gamelist").getJSONObject(i).getString("name"));
							log.debug("[robotGameConfig] 要展示的对象 {}", robotConfig);
							list.add(robotConfig);
						}

					}
				}
			}

		}
		log.debug("[robotGameConfig] list {}", list);
		return R.ok().put("list", list);
	}

	/**
	 * 提交机器人配置
	 */
	@RequestMapping("/saveConfig")
	//	@RequiresPermissions("gameinfo:gameinfo:saveConfig")
	public R saveConfig(@RequestBody JSONObject json) {
		log.debug("saveConfig 提交机器人配置 {}", json);
		JSONArray arry = json.getJSONArray("arry");// 提交参数
		if (CollectionUtils.isEmpty(arry)) {
			log.error("提交参数为空");
			return R.error("未知异常请联系管理员");
		}
		for(int i=0; i<arry.size();i++) {
			if(null==arry.getJSONObject(i)||CollectionUtil.isEmpty(arry.getJSONObject(i).getJSONArray("data"))||StringUtils.isEmpty(arry.getJSONObject(i).getString("putRobotConfigUrl"))) {
				log.error("提交参数有误");
				return R.error("网络错误");
			}
			JSONObject returnObj = GameInterfaceService.saveConfig(arry.getJSONObject(i).getString("putRobotConfigUrl"), arry.getJSONObject(i).getJSONArray("data"));
			if (returnObj.getInteger("code") == null || returnObj.getInteger("code") != ResultDef.SUCCESS) {
				return R.error(returnObj.getInteger("code"), returnObj.getString("msg"));
			}
		}
		return R.ok();
	}

	/**
	 * 将所有游戏中机器人数量设置为0
	 */
	@RequestMapping("/close")
	//	@RequiresPermissions("gameinfo:gameinfo:close")
	public R close() {
		// 获取所有游戏机器人
		JSONObject returnObj = GameInterfaceService.robotGameList(null);
		if (null == returnObj.getInteger("code") || ResultDef.SUCCESS != returnObj.getInteger("code")) {
			return R.error(returnObj.getInteger("code"), returnObj.getString("msg"));
		}
		JSONArray arry = returnObj.getJSONArray("gamelist");
		if (!CollectionUtil.isEmpty(arry)) {
			for (int i = 0; i < arry.size(); i++) {
				// 获取该机器人游戏配置路径-----并通过路径获取到配置
				String getRobotConfigUrl = arry.getJSONObject(i).getString("getRobotConfigUrl");
				// 机器人游戏配置提交路径-----并通过路径修改配置
				String putRobotConfigUrl = arry.getJSONObject(i).getString("putRobotConfigUrl");
				JSONObject configObj = GameInterfaceService.robotGameConfig(getRobotConfigUrl);
				if (null == configObj.getInteger("code") || ResultDef.SUCCESS != configObj.getInteger("code")) {
					return R.error(configObj.getInteger("code"), configObj.getString("msg"));
				}
				if (!CollectionUtil.isEmpty(configObj.getJSONArray("data"))) {
					// 循环获取每个配置，并将其中的数量改为0
					for (int j = 0; j < configObj.getJSONArray("data").size(); j++) {
						if (null != configObj.getJSONArray("data").getJSONObject(j)
								&& !StringUtils
										.isEmpty(configObj.getJSONArray("data").getJSONObject(j).getString("name"))
								&& ("num").equals(configObj.getJSONArray("data").getJSONObject(j).getString("name"))
								&& !("0").equals(configObj.getJSONArray("data").getJSONObject(j).getString("value"))) {
							// 将num值改为0
							configObj.getJSONArray("data").getJSONObject(j).put("value", "0");
							break;
						}
					}
					// 提交修改后的配置
					JSONObject putObj = GameInterfaceService.saveConfig(putRobotConfigUrl,
							configObj.getJSONArray("data"));
					if (null == putObj.getInteger("code") || ResultDef.SUCCESS != putObj.getInteger("code")) {
						return R.error(putObj.getInteger("code"), putObj.getString("msg"));
					}
				}

			}
		}
		return R.ok();
	}

	/**
	 * 强制回收指定游戏机器人
	 */
	@RequestMapping("/forceTaskRobot")
	//	@RequiresPermissions("gameinfo:gameinfo:forceTaskRobot")
	public R forceTaskRobot(@RequestBody Long[] ids ) {
		for(Long gameId:ids) {
			// 获取游戏大厅接口，获取到游戏列表
			JSONObject obj = new JSONObject();
			obj.put("gameId", gameId);
			JSONObject returnObj = GameInterfaceService.robotGameList(obj);
			if(null != returnObj.getInteger("code") && ResultDef.SUCCESS == returnObj.getInteger("code")) {
				JSONArray arry = returnObj.getJSONArray("gamelist");
				if(!CollectionUtils.isEmpty(arry)) {
					for(int i=0;i<arry.size();i++) {
						if(null!=arry.getJSONObject(i)&&!StringUtils.isEmpty(arry.getJSONObject(i).getString("forceoffroboturl"))) {
							JSONObject forceoffroboObj = GameInterfaceService.robotGameInterface(arry.getJSONObject(i).getString("forceoffroboturl"));
							if(null == forceoffroboObj.getInteger("code") || ResultDef.SUCCESS != forceoffroboObj.getInteger("code")) {
								return R.error(forceoffroboObj.getInteger("code"),forceoffroboObj.getString("msg"));
							}
						}
					}
				}else {
					return R.error("无可回收游戏");
				}
			}else {
				return R.error(returnObj.getInteger("code"),returnObj.getString("msg"));
			}
		}
		return R.ok();
	}
	/**
	 * 强制还原指定游戏机器人
	 */
	@RequestMapping("/forceOnTaskRobot")
	//	@RequiresPermissions("gameinfo:gameinfo:forceOnTaskRobot")
	public R forceOnTaskRobot(@RequestBody Long[] ids ) {
		for(Long gameId:ids) {
			// 获取游戏大厅接口，获取到游戏列表
			JSONObject obj = new JSONObject();
			obj.put("gameId", gameId);
			JSONObject returnObj = GameInterfaceService.robotGameList(obj);
			if(null != returnObj.getInteger("code") && ResultDef.SUCCESS == returnObj.getInteger("code")) {
				JSONArray arry = returnObj.getJSONArray("gamelist");
				if(!CollectionUtils.isEmpty(arry)) {
					for(int i=0;i<arry.size();i++) {
						if(null!=arry.getJSONObject(i)&&!StringUtils.isEmpty(arry.getJSONObject(i).getString("forceonroboturl"))) {
							JSONObject forceOnroboObj = GameInterfaceService.robotGameInterface(arry.getJSONObject(i).getString("forceonroboturl"));
							if(null == forceOnroboObj.getInteger("code") || ResultDef.SUCCESS != forceOnroboObj.getInteger("code")) {
								return R.error(forceOnroboObj.getInteger("code"),forceOnroboObj.getString("msg"));
							}
						}
					}
				}else {
					return R.error("无可回收游戏");
				}
			}else {
				return R.error(returnObj.getInteger("code"),returnObj.getString("msg"));
			}
		}
		return R.ok();
	}

}
