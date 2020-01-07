package com.xmsy.server.zxyy.manager.modules.robot.service;


import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
@Component
public class GameInfoService {
	/**
	 * 所有游戏下拉  +每个游戏包含的几个大厅  下拉68个
	 */
	public  JSONObject gameSelectForRobot() {
		String url = HallUrlConstant.getROBOT_SERVICE_URL()+"/gameinfo/gameinfo/gameSelectForRobot";
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}
}
