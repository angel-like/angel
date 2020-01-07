package com.xmsy.server.zxyy.manager.modules.game.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;

@Component
public class GameInfosService {
	
	/**
	 * 游戏信息   列表--新版页面
	 */
	public JSONObject getGameInfoListNew(JSONObject info) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/listNew";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(info.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 * 游戏信息   保存--新版页面
	 */
	public JSONObject saveGameInfoNew(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/saveNew";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 *  游戏信息   信息--新版页面
	 */
	public JSONObject getGameInfoInfoListNew(Long gameId) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/info/info/infoNew/"+gameId;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 * 游戏信息   修改--新版页面
	 */
	public JSONObject updateGameInfoNew(JSONObject gameInfoListJSONObject) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/updateNew";
		String result = HttpRequest.post(url).body(gameInfoListJSONObject.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 * 游戏配置信息    修改或保存  --新版页面
	 * 
	 * @throws CloneNotSupportedException
	 */
	public JSONObject updateNewGameConfig(JSONObject sysDictionaryListListJSONObject) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/updateNewGameConfig";
		String result = HttpRequest.post(url).body(sysDictionaryListListJSONObject.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 修改游戏状态（是否完成）
	 * 
	 * @throws CloneNotSupportedException
	 */
	public JSONObject updateEnable(Long id, Boolean finished) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/updateEnable";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("finished", finished);
		String result = HttpRequest.post(url).form(map).timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 修改游戏状态（是否维护）
	 * 
	 * @throws CloneNotSupportedException
	 */
	public JSONObject updateMaintenance(Long id, Boolean maintenance) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/updateMaintenance";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("maintenance", maintenance);
		String result = HttpRequest.post(url).form(map).timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
	public String generatorCodeNew(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/exportSqlNew";
		String string = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		//接收到的数据
		String s=string.substring(1, string.length()-1);
		String s1=s.replaceAll("\\\\n", "\\\r\\\n");
		return s1;
	}
	//============================上面新版本页面  下面原有的不动=========================
	/**
	 * 游戏信息列表
	 */
	public JSONObject getGameInfoList(JSONObject info) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(info.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 游戏信息列表
	 */
	public JSONObject getGameTypeList() {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/GameList";
		String result = HttpRequest.post(url)
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 *  游戏信息信息
	 */
	public JSONObject getGameInfoInfoList(Long id) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/info/info/info/"+id;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 游戏信息保存
	 */
	public JSONObject saveGameInfo(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/save";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 游戏信息修改
	 */
	public JSONObject updateGameInfo(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/update";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 *  游戏信息删除
	 */
	public JSONObject deleteGameInfo(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/delete";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	public String generatorCode(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/info/info/exportSql";
		String string = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		//接收到的数据
		String s=string.substring(1, string.length()-1);
		String s1=s.replaceAll("\\\\n", "\\\r\\\n");
		return s1;
	}
}
