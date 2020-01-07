package com.xmsy.server.zxyy.schedule.server;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author adu
 *
 */
public interface UserBalanceService {
	/**
	 * 用户余额宝昨日利率
	 * @param yesterday
	 * @return
	 */
	JSONObject getBalanceRate(Date yesterday);
	/**
	 * 用户余额宝昨日利率
	 * @param yesterday
	 * @return
	 */

	JSONArray getUserBalance();
	/**
	 * 
	 * @param page
	 * @return
	 */
	JSONArray getUserBalanceList();
	/**
	 * 保存昨日利率
	 * @param yesterday
	 * @param rate
	 */
	void saveBalanceRateYesterday(Date yesterday, BigDecimal rate);
	void saveRateYesterday(Date yesterday);
	/**
	 * 保存昨日收益
	 * @param yesterday
	 * @param rate
	 * @param user
	 */
	void     saveUserYesterdayProfi(Date yesterday, BigDecimal rate, JSONObject user);

	/**
	 * 重置昨日收益为0
	 */
	void resetUserYesterdayProfit();
}

