package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface ReportPlayerGameDailyService {
	
	/**
	 * 根据时间范围查询玩家游戏的投入和产出
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	JSONArray findPlayerGameInvestmentAndOutputByDate(String startTime,String endTime);

	void saveDataTransaction(JSONObject playerGameData);
}
