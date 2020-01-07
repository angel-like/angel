package com.xmsy.server.zxyy.manager.modules.robot.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;

@Component
public class RobotProfitRecordService {

	public JSONObject getRobotProfitRecordList(JSONObject robotProfitRecord) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/robotprofitrecord/robotprofitrecord/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(robotProfitRecord.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

}
