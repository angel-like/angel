package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author adu
 *
 */
public interface GiftCoinConfigService {
	/**
	 * 根据类型查询金币奖励配置
	 * @param type
	 * @return
	 */
	JSONArray findGiftCoinConfigList(int type);
	
	void saveUserGiftCoin(JSONObject user,JSONObject giftCoinConfig);
	/**
	 * 救济金
	 * @param user
	 * @param giftCoinConfig
	 */
	void saveUserReliftCoin(JSONObject user,JSONObject giftCoinConfig);
}

