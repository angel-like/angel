package com.xmsy.server.zxyy.manager.modules.manager.gameinfo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;
import com.xmsy.server.zxyy.manager.modules.gamemanage.GameSelectParam;
import com.xmsy.server.zxyy.manager.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.manager.modules.robot.service.GameInfoService;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 游戏信息
 *
 * @author lzy
 * @email xxxxx
 * @date 2019-01-16 16:45:35
 */
@Slf4j
@RestController
@RequestMapping("gameinfo/gameinfo")
public class GameInfoController {

	/**
	 * 游戏下拉
	 */
	@RequestMapping("/gameSelect")
	public R gameSelect() {
		List<GameSelectParam> gameList2 = new ArrayList<>();
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect();
		for (GameSelectParam gsp : gameList) {
			if (!gsp.getRoom().equals(2l)) {
				gameList2.add(gsp);
			}
		}

		return R.ok().put("data", gameList2);
	}

	/**
	 * 游戏下拉
	 */
	@RequestMapping("/gamesSelect")
	public R gamesSelect(@RequestParam(value = "roomId", required = false) Long roomId) {
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect(roomId);
		return R.ok().put("data", gameList);
	}
	
	/**
	 * 游戏下拉 任务使用
	 */
	@RequestMapping("/gameSelectForTask")
	public R gameSelectForTask(@RequestParam(value = "roomId", required = false) Long roomId) {
		List<GameSelectParam> gameList = GameInfoInterface.gameSelectForTask(roomId);
		return R.ok().put("data", gameList);
	}
	/**
	 * 每个游戏对应 厅次下拉 任务使用
	 */
	@RequestMapping("/gradeSelectForTask")
	public R gradeSelectForTask(@RequestParam(value = "gameId", required = false) Long gameId) {
		List<GameSelectParam> gameList = GameInfoInterface.gradeSelectForTask(gameId);
		return R.ok().put("data", gameList);
	}
	/**
	 * 游戏下拉
	 */
	@RequestMapping("/roomSelect")
	public R roomSelect() {
		JSONObject returnObj = GameInfoInterface.roomSelect();
		log.info("returnObj 房间下拉返回结果 {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			return R.ok().put("data", returnObj.get("data"));
		}
		return R.ok().put("data", returnObj);
	}

	/**
	 * 游戏菜单
	 */
	@RequestMapping("/gameMenu")
	public R gameMenu() {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_MENU();
		String result = HttpRequest.get(url).header("token", zxyyToken).execute().body();
		JSONObject returnObj = JSON.parseObject(result);
		log.info("returnObj 游戏下拉返回结果 {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			return R.ok().put("data", returnObj.get("data"));
		}
		return R.ok().put("data", result);
	}
	
	//============================机器人页面使用的功能==========================
	@Autowired
    private GameInfoService GameInfoservice;
	/**
	 * 所有游戏下拉  +每个游戏包含的几个大厅  下拉68个
	 */
	@RequestMapping("/gameSelectForRobot")
	//@RequiresPermissions("gameinfo:gameinfo:gameSelectForRobot")
	public JSONObject gameSelectForRobot() {
		return GameInfoservice.gameSelectForRobot();
	}
}
