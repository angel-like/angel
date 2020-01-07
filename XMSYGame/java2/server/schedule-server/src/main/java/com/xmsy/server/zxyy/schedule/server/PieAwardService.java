package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;

public interface PieAwardService {
	/**
	 * 查询某个时间范围的用户中奖列表
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	JSONArray findPieAwardByDay(String startDate,String endDate);
	
}
