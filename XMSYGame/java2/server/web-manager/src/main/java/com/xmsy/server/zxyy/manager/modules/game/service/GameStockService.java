package com.xmsy.server.zxyy.manager.modules.game.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;

@Component
public class GameStockService {
	/**
	 * 游戏信息列表
	 */
	public JSONObject getGameStockList(JSONObject info) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamestock/gamestock/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(info.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	
	/**
	 *  信息
	 */
	public JSONObject getGameStockInfoList(Long id) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gamestock/gamestock/info/"+id;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 保存
	 */
	public JSONObject saveGameStock(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamestock/gamestock/save";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 修改
	 */
	public JSONObject updateGameStock(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamestock/gamestock/update";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 *  删除
	 */
	public JSONObject deleteGameStock(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamestock/gamestock/delete";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	public JSONObject getInfo() {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamestock/gamestock/getInfo";
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
}
