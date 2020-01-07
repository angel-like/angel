package com.xmsy.server.zxyy.manager.modules.robot.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;

@Component
public class RobotGameRecordService {
	/**
	 * 机器人游戏记录列表信息
	 */
	public JSONObject getRobotGameRecordList(JSONObject robotgamerecord) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/gamerecord/gamerecord/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(robotgamerecord.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
}
