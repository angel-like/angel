package com.xmsy.server.zxyy.manager.modules.robot.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;

@Component
public class RobotService {

	public static void main(String[] args) {

	}
	
	/**
	 * 机器人列表信息
	 */
	public  JSONObject getRobotList(JSONObject robot) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL()+"/robot/robot/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).
				form(JSONObject.parseObject(robot.toJSONString(), Map.class)).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 *  结算
	 */
	public  JSONObject disable(Long id) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL()+"/robot/robot/disable/"+id;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
	/**
	 *  机器人回收
	 */
	public  JSONObject taskRecyclingRobot(JSONObject jsonObj) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL()+"/robot/robot/taskRecyclingRobot";
		String result = HttpRequest.post(url).
				body(jsonObj.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
}
