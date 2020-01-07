package com.xmsy.server.zxyy.manager.modules.game.service;


import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
@Component
public class GameConfigService {

	/**
	 * 游戏配置列表
	 */
	public JSONObject getGameConfigList(JSONObject game) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gameconfig/gameconfig/gamelist";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).
				form(JSONObject.parseObject(game.toJSONString(), Map.class)).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 游戏配置列表
	 */
	public JSONObject getList(JSONObject game) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gameconfig/gameconfig/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).
				form(JSONObject.parseObject(game.toJSONString(), Map.class)).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 *  游戏配置信息
	 */
	public JSONObject getGameConfigInfoList(Long id) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gameconfig/gameconfig/info/"+id;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 *  下拉
	 */
	public JSONObject selectDictionary(boolean interval) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gameconfig/gameconfig/select/"+interval;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 *  通过游戏id获取区间游戏胜率信息
	 */
	public JSONObject selectIntervalByGameId(Long gameId) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gameconfig/gameconfig/selectIntervalByGameId/"+gameId;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 *  批量新增修改时：获取当前选中游戏厅的所有信息，并放入数据字典里
	 */
	public JSONObject selectDataPerfectByGameId(Long gameId) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gameconfig/gameconfig/selectDataPerfectByGameId/"+gameId;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 游戏配置保存
	 */
	public JSONObject saveGameConfig(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameconfig/gameconfig/save";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 游戏配置修改
	 */
	public JSONObject updateGameConfig(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameconfig/gameconfig/update";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 保存游戏区间
	 */
	public JSONObject updateIntervalGameRateByGameId(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameconfig/gameconfig/saveOrUpdateIntervalGameRate";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 批量新增修改时：对传过来的数据进行更新或者插入
	 */
	public JSONObject saveOrUpdatePerfect(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameconfig/gameconfig/saveOrUpdatePerfect";
		String result = HttpRequest.post(url).body(json.toJSONString()).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 *  删除
	 */
	public JSONObject deleteGameConfig(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameconfig/gameconfig/delete";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}



}
