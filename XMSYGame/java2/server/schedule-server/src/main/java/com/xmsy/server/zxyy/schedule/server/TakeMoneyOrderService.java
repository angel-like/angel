package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @author adu
 *
 */
public interface TakeMoneyOrderService {
	/**
	 * 提现订单月统计
	 * @return
	 */
	JSONArray statisticsTakeMoneyByMonth();
}
