package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface ReportGameDailyService {
	
	/**
	 * 根据时间范围查询游戏的投入和产出
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	JSONArray findGameGradeInvestmentAndOutputByDate(String startTime,String endTime);
	JSONArray findProviderInvestmentAndOutputByDate(String startTime,String endTime);

	void saveGameGradeData(JSONObject gameGradeData);
	
	/**
	 * 根据时间范围查询游戏的投入和产出
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	JSONArray findGameInvestmentAndOutputByDate(String startTime,String endTime);

	void saveGameData(JSONObject gameData);
	void saveProviderData(JSONObject gameProviderData);
}
