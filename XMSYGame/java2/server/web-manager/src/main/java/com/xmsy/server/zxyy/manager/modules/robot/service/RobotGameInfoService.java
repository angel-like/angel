package com.xmsy.server.zxyy.manager.modules.robot.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;

@Component
public class RobotGameInfoService {
	/**
	 * 机器人游戏记录列表信息
	 */
	public JSONObject getRobotGameInfoList(JSONObject robotgameinfo) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/gameinfo/gameinfo/robotGameList";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(robotgameinfo.toJSONString(), Map.class))
				.timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 *  重置人数配置  ----关闭游戏机器人--将所有游戏中机器人数量设置为0
	 */
	public JSONObject closeAllRobot() {
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/gameinfo/gameinfo/close";
		String result = HttpRequest.post(url).timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 *  强制回收指定游戏机器人
	 */
	public JSONObject forceTaskRobot(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/gameinfo/gameinfo/forceTaskRobot";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 *  强制还原指定游戏机器人
	 */
	public JSONObject forceOnTaskRobot(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/gameinfo/gameinfo/forceOnTaskRobot";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 * 获取游戏机器人配置
	 */
	public JSONObject robotGameConfig(String gameId) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/gameinfo/gameinfo/robotGameConfig";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("gameId", gameId);
		String result = HttpRequest.post(url).form(map).timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 * 提交机器人配置
	 */
	public JSONObject saveConfig(JSONObject json) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/gameinfo/gameinfo/saveConfig";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(HallUrlConstant.getTIMEOUT()).execute().body();
		return JSON.parseObject(result);
	}
}
