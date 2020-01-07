package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Map;

public interface UserService {
	/**
	 * 用户下注返水 更新用户金币
	 * 
	 * @param userReturnWater
	 */
	void updateUserCoinTransaction(JSONObject userReturnWater, BigDecimal multiple);

	/**
	 * 代理商下注返佣
	 * 
	 * @param userCommission
	 */
	void updateUserCommission(JSONObject userCommission, Map<Long, BigDecimal> agentHierarchy);

	/**
	 * 代理商充值返佣
	 * 
	 * @param userCommission
	 */
	void updateUserRechargeCommission(JSONObject userCommission, Map<Long, BigDecimal> agentHierarchy);

	/**
	 * 代理商列表
	 */
	JSONArray getAgentList();

	/**
	 * 扫描未充值会员
	 */
	JSONArray scanUserNoRecharge();

	/**
	 * 用户代理层级设置
	 * 
	 */
	JSONArray getUserAgentHierarchy();

	/**
	 * 检查用户是否异常 通过充值金额
	 * 
	 * @param userMoney
	 * @param riskArray
	 */
	void checkUserAbnormal(JSONObject userMoney, JSONArray riskArray, Long defHierarchyId);

	/**
	 * 检查用户是否异常 通过当前余额
	 * 
	 * @param userMoney
	 * @param riskArray
	 */
	void checkUserAbnormalForMoney(JSONObject userMoney, JSONArray riskArray, Long defHierarchyId);
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	JSONObject getUserById(Long id);
	
	/**
	 * 获取破产用户列表
	 * @param startTime
	 * @param type
	 * @param num
	 * @param maxNum
	 * @return
	 */
	JSONArray findUserListByBankruptcy(String startTime,int type,int num ,int maxNum,Long vipId);

	/**
	 * 检查用户是否离线
	 */
	void updateUserGameServer(String checkTime);



	void updateUserEveryDateBet(Long userId, String userAccount, Long validBet, String date, boolean isGmUser);
}
