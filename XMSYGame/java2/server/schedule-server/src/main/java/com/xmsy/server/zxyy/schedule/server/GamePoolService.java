package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author adu
 *
 */
public interface GamePoolService {
	/**
	 * 取得可用的游戏奖池列表
	 * @return
	 * @throws Exception
	 */
	JSONArray findGamePoolList()throws Exception;
	/**
	 * 查询游戏某个时间段的下注金额
	 * 并更新到游戏奖池中
	 * @param poolGame
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	void updatePoolByGameIdForStatistics(JSONObject poolGame, String startDate, String endDate)throws Exception;
}

