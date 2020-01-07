package com.xmsy.server.zxyy.manager.modules.game.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;

@Component
public class HallService {
	/**
	 * 游戏大厅列表
	 */
	public JSONObject getRobotHallList(JSONObject hall) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/hall/hall/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(hall.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);

	}

	/**
	 *  游戏大厅信息
	 */
	public JSONObject getHallInfoList(Long id) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/hall/hall/info/"+id;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 游戏大厅保存
	 */
	public JSONObject saveHall(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/hall/hall/save";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 游戏大厅修改
	 */
	public JSONObject updateHall(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/hall/hall/update";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 *  游戏大厅删除
	 */
	public JSONObject deleteHall(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/hall/hall/deleteByIds";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}


}
