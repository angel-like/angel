package com.xmsy.server.zxyy.schedule.server;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author adu
 *
 */
public interface OrderCashExamineService {
	/**
	 * 取得未结算的取款稽查记录
	 * 根据usrid
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	JSONObject getUnsettledCashExamineByUserId(Long userId);
	
	void bindUserinfoGiftCashExamine(JSONObject userData,BigDecimal amount,BigDecimal multiple);
	
	void updateOrderCashExamineUserNeedBet(Long id,BigDecimal userNeedBet);
	/**
	 * 
	 * @param userId
	 * @param userAccount
	 * @param amount
	 * @param multiple
	 * @param userMoney
	 * @param userNeedBet
	 */
	void insertOrderCashExamine(Long userId,String userAccount,BigDecimal amount,BigDecimal multiple,BigDecimal userMoney,BigDecimal userNeedBet);
}

