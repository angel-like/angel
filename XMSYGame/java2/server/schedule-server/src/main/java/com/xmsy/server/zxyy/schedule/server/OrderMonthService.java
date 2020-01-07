package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @author adu
 *
 */
public interface OrderMonthService {
	//
	/**
	 * 保存充值订单月统计
	 * @return
	 */
	void saveRechargeOrderMonth(JSONArray dataList);
	/**
	 * 保存提现订单月统计
	 * @return
	 */
	void saveTakeMoneyOrderMonth(JSONArray dataList);
}
