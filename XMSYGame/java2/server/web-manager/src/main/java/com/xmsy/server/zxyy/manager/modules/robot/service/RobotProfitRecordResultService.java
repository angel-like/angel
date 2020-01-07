package com.xmsy.server.zxyy.manager.modules.robot.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;

@Component
public class RobotProfitRecordResultService {

	public JSONObject getRobotProfitRecordResultList(JSONObject robotProfitRecordResult) {
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/robotprofitrecordresult/robotprofitrecordresult/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(robotProfitRecordResult.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	public JSONObject sumrobotRecord() {
		String url = HallUrlConstant.getROBOT_SERVICE_URL() + "/robotprofitrecordresult/robotprofitrecordresult/sumrobotRecord";
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

}
