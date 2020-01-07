package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @author adu
 *
 */
public interface RechargeOrderService {
	//
	/**
	 * 完成充值订单统计
	 * 按照日期范围
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	JSONArray statisticsRechargeByDay(String startDate , String endDate);
	/**
	 * 充值订单月统计
	 * @return
	 */
	JSONArray statisticsRechargeByMonth();
	/**
	 * 会员最后充值的记录
	 * @return
	 */
	JSONArray findUserRechargeLast();
}
