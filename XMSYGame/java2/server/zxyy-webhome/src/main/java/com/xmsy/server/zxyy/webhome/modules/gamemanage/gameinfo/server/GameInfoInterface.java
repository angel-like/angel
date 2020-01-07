package com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.GameSelectParam;

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

	// /**
	// * 游戏列表(id,name.snace)
	// */
	// public static JSONObject gameSelect() {
	// String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
	// String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT();
	// String gameResult = HttpRequest.get(url).header("token",
	// zxyyToken).execute().body();
	// JSONObject returnObj = JSON.parseObject(gameResult);
	// return returnObj;
	// }

	// /**
	// * 游戏列表(id,name.snace)
	// */
	// public static JSONObject gameSelect(Long roomId) {
	// String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
	// String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT();
	// String gameResult = HttpRequest.get(url + "?roomId=" +
	// roomId).header("token", zxyyToken).execute().body();
	// JSONObject returnObj = JSON.parseObject(gameResult);
	// return returnObj;
	// }

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
}
