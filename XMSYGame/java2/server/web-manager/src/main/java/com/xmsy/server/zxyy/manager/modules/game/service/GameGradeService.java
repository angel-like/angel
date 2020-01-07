package com.xmsy.server.zxyy.manager.modules.game.service;


import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;

@Component
public class GameGradeService {
	/**
	 * 游戏场次等级列表
	 */
	public JSONObject getGameGradeList(JSONObject gamegrade) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamegrade/gamegrade/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(gamegrade.toJSONString(), Map.class))
				.timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 游戏场次等级列表
	 */
	public JSONObject getGradeList() {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamegrade/gamegrade/GradeList";
		String result = HttpRequest.post(url)
				.timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 游戏场次等级信息
	 */
	public JSONObject getGameGradeInfoList(Long id) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gamegrade/gamegrade/info/"+id;
		String result = HttpRequest.post(url).timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 游戏场次等级保存
	 */
	public JSONObject saveGameGrade(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamegrade/gamegrade/save";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 游戏场次等级修改
	 */
	public JSONObject updateGameGrade(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamegrade/gamegrade/update";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 *  游戏场次等级删除
	 */
	public JSONObject deleteGameGrade(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gamegrade/gamegrade/delete";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
}
