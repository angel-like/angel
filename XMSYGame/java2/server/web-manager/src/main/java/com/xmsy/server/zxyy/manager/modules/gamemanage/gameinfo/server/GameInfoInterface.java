package com.xmsy.server.zxyy.manager.modules.gamemanage.gameinfo.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;
import com.xmsy.server.zxyy.manager.modules.gamemanage.GameSelectParam;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求游戏后台的接口
 * 
 * @author adu
 * @date 2019-02-26
 */
@Slf4j
public class GameInfoInterface {
	/**
	 * 游戏信息
	 */
	public static JSONObject gameInfo() {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_LIST();
		String result = HttpRequest.get(url).header("token", zxyyToken).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 游戏信息
	 */
	public static JSONObject hallList() {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_HALL_LIST();
		log.info("url {}", url);
		String result = HttpRequest.get(url).header("token", zxyyToken).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 游戏列表(id,name.snace)
	 */
	public static List<GameSelectParam> gameSelect(Long roomId) {
		List<GameSelectParam> list = new ArrayList<GameSelectParam>();
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT();
		try {
			String gameResult = HttpRequest.get(url + "?roomId=" + roomId).header("token", zxyyToken).execute().body();
			JSONObject returnObj = JSON.parseObject(gameResult);
			list = (List<GameSelectParam>) JSON.parseArray(returnObj.getString("data"), GameSelectParam.class);
		} catch (Exception e) {
			log.error("【获取游戏列表异常】", e.getMessage());
		}
		return list;
	}
	/**
	 * 游戏列表(id,name.snace)  --为任务获取游戏下拉
	 */
	public static List<GameSelectParam> gameSelectForTask(Long roomId) {
		List<GameSelectParam> list = new ArrayList<GameSelectParam>();
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT()+"ForTask";
		try {
			String gameResult = HttpRequest.get(url + "?roomId=" + roomId).header("token", zxyyToken).execute().body();
			JSONObject returnObj = JSON.parseObject(gameResult);
			list = (List<GameSelectParam>) JSON.parseArray(returnObj.getString("data"), GameSelectParam.class);
		} catch (Exception e) {
			log.error("【获取游戏列表异常】", e.getMessage());
		}
		return list;
	}
	
	/**
	 * 游戏列表(id,name.snace)
	 */
	public static List<GameSelectParam> gameSelect() {
		List<GameSelectParam> list = new ArrayList<GameSelectParam>();
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT();
		try {
			String gameResult = HttpRequest.get(url).header("token", zxyyToken).execute().body();
			JSONObject returnObj = JSON.parseObject(gameResult);
			list = (List<GameSelectParam>) JSON.parseArray(returnObj.getString("data"), GameSelectParam.class);
		} catch (Exception e) {
			log.error("【获取游戏列表异常】", e.getMessage());
		}
		return list;
	}

	/**
	 * 房间列表(id,name.snace)
	 */
	public static JSONObject roomSelect() {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_ROOM_SELECT();
		String roomResult = HttpRequest.get(url).header("token", zxyyToken).execute().body();
		JSONObject returnObj = JSON.parseObject(roomResult);
		return returnObj;
	}

	/**
	 * 获取游戏名称
	 */
	public static String getGameName(Long gameId) {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT();
		String gameResult = HttpRequest.get(url).header("token", zxyyToken).execute().body();
		JSONObject returnObj = JSON.parseObject(gameResult);
		String gameName = null;
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			if (!CollectionUtils.isEmpty(returnObj.getJSONArray("data"))) {
				for (int i = 0; i < returnObj.getJSONArray("data").size(); i++) {
					JSONObject obj = returnObj.getJSONArray("data").getJSONObject(i);
					if (null != obj && null != obj.getLong("id") && obj.getLong("id") == gameId) {
						gameName = obj.getString("name");
					}
				}
			}
		}
		return gameName;
	}
	/**
	 * 通过游戏id获取 该游戏下不同厅次
	 * @param gameId
	 * @return
	 */
	public static List<GameSelectParam> gradeSelectForTask(Long gameId) {
		List<GameSelectParam> list = new ArrayList<GameSelectParam>();
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT()+"GradeIdForTask";
		try {
			String gameResult = HttpRequest.get(url + "?gameId=" + gameId).header("token", zxyyToken).execute().body();
			JSONObject returnObj = JSON.parseObject(gameResult);
			list = (List<GameSelectParam>) JSON.parseArray(returnObj.getString("data"), GameSelectParam.class);
		} catch (Exception e) {
			log.error("【获取游戏列表异常】", e.getMessage());
		}
		return list;
	}
}
