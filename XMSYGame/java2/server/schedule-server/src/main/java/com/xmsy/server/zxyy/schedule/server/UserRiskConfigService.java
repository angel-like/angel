package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author adu
 *
 */
public interface UserRiskConfigService {
	/**
	 * 取得默认层级
	 * @return
	 */
	JSONObject getDefHierarchy();
	/**
	 * 根据类型查询风控配置列表
	 * @param riskType
	 * @return
	 */
	JSONArray findRiskConfigList(String riskType);
}

