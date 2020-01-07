package com.xmsy.server.zxyy.manager.modules.game.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;

@Component
public class RoomService {

	/**
	 * 房间list
	 * @param room
	 * @return
	 */
	public JSONObject getList(JSONObject room) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/room/room/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(room.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 房间list
	 * @param room
	 * @return
	 */
	public JSONObject getRoomList() {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/room/room/RoomList";
		String result = HttpRequest.post(url)
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 *  房间信息
	 */
	public JSONObject getRoomInfoList(Long id) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/room/room/info/"+id;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 房间保存
	 */
	public JSONObject saveRoom(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/room/room/save";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 * 房间修改
	 */
	public JSONObject updateRoom(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/room/room/update";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	
	/**
	 *  房间删除
	 */
	public JSONObject deleteRoom(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/room/room/delete";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	

}
