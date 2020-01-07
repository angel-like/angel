package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author adu
 *
 */
public interface UserRebateService {
	/**
	 * 取得用户返利规则
	 * @param type
	 * @return
	 * @throws Exception
	 */
	JSONObject getUserReBateByType(int type)throws Exception;
	/**
	 * 统计用户当前的金币值
	 * @return
	 * @throws Exception
	 */
	JSONArray statisticsWealthByDay();
}

