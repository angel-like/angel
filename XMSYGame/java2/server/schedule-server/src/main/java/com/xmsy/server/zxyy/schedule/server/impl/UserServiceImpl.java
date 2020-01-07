package com.xmsy.server.zxyy.schedule.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.event.PushService;
import com.xmsy.server.zxyy.schedule.event.param.UserInfoMessage;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.OrderCashExamineService;
import com.xmsy.server.zxyy.schedule.server.UserService;
import com.xmsy.server.zxyy.schedule.utils.Constant;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;
import com.xmsy.server.zxyy.schedule.utils.OrderNoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Service("userService")
public     class UserServiceImpl implements UserService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Autowired
	private PushService pushService;
	@Autowired
	private OrderCashExamineService orderCashExamineService;

	// 用户下注返水
	@Override
	public void updateUserCoinTransaction(JSONObject userReturnWater, BigDecimal multiple) {
		Long userId = userReturnWater.getLong("userId");
		BigDecimal userWaterProfit = MathUtil.getBigDecimal(userReturnWater.get("userWaterProfit"));
		if (userId == null || userWaterProfit.compareTo(BigDecimal.ZERO) <= 0) {
			log.info("会员不存在 userid:{}", userId);
			updategameRecordDaily(userReturnWater.getLong("id"), false, "会员不存在;");
			return;
		}
		JSONObject user = getUser(userId);
		if (user == null || user.isEmpty()) {
			log.info("会员不存在 userid:{}", userId);
			updategameRecordDaily(userReturnWater.getLong("id"), false, "会员不存在;");
			return;
		}
		if (user.getBoolean("forbiddenEnable")) {
			log.info("会员被加入黑名单 userid:{}", userId);
			updategameRecordDaily(userReturnWater.getLong("id"), false, "会员被加入黑名单;");
			return;
		}
		String sqlArray[] = new String[3];

		sqlArray[0] = getAddUserCoinSql();
		Object param0[] = new Object[2];
		param0[0] = userWaterProfit.longValue();
		param0[1] = userId;

		BigDecimal amount = userWaterProfit.divide(MathUtil.getBigDecimal(Constant.COIN_RATE), 2,
				BigDecimal.ROUND_HALF_UP);

		sqlArray[1] = getInsertUserTransactionRecordSql();
		Object param1[] = new Object[10];
		param1[0] = user.get("id");
		param1[1] = user.get("account");
		param1[2] = Constant.TransactionType.REBATE.getValue();
		param1[3] = OrderNoUtil.getOrderNo();
		param1[4] = amount;
		param1[5] = 0;
		param1[6] = user.get("money");
		param1[7] = MathUtil.getBigDecimal(user.get("coin")).add(userWaterProfit).longValue();
		param1[8] = user.get("commission");
		param1[9] = Constant.TransactionDetailType.USERRETURNWATER.getValue();

		sqlArray[2] = getUpdategameRecordDailySql(false);
		Object param2[] = new Object[1];
		param2[0] = userReturnWater.getLong("id");

		jdbcUtil.transactionExecuteUpdate(sqlArray, param0, param1, param2);
		orderCashExamineService.bindUserinfoGiftCashExamine(user, amount, multiple);

	}

	// 用户下注代理商返佣
	@Override
	public void updateUserCommission(JSONObject userCommission, Map<Long, BigDecimal> agentHierarchy) {
		Long userId = userCommission.getLong("userId");
		BigDecimal validBet = MathUtil.getBigDecimal(userCommission.get("validBet"));
		if (userId == null || validBet.compareTo(BigDecimal.ZERO) <= 0) {
			log.info("会员不存在 userid:{}", userId);
			updategameRecordDaily(userCommission.getLong("id"), true, "会员不存在;");
			return;
		}
		JSONObject agent = getAgentByUserId(userId);
		if (agent == null || agent.isEmpty()) {
			log.info("代理商不存在 userid:{}", userId);
			updategameRecordDaily(userCommission.getLong("id"), true, "代理商不存在;");
			return;
		}
		// 代理商返佣的金币
		validBet = validBet.multiply(MathUtil.getBigDecimal(agentHierarchy.get(agent.getLong("agentHierarchyId"))));
		// 代理商返佣的金币转成金额
		BigDecimal money = validBet.divide(new BigDecimal(Constant.COIN_RATE), 2, BigDecimal.ROUND_HALF_UP);
		String sqlArray[] = new String[5];
		sqlArray[0] = getAddUserCoinSql();
		Object param0[] = new Object[2];
		param0[0] = validBet;
		param0[1] = agent.getLong("userId");

		sqlArray[1] = getAddUserRecommendationTotalCoinSql();
		Object param1[] = new Object[2];
		param1[0] = validBet;
		param1[1] = agent.getLong("id");

		sqlArray[2] = getAddUserRecommendationRecordTotalCoinSql();
		Object param2[] = new Object[2];
		param2[0] = validBet;
		param2[1] = userId;

		sqlArray[3] = getInsertUserTransactionRecordSql();
		Object param3[] = new Object[10];
		param3[0] = agent.get("userId");
		param3[1] = agent.get("account");
		param3[2] = Constant.TransactionType.REBATE.getValue();
		param3[3] = OrderNoUtil.getOrderNo();
		param3[4] = money;
		param3[5] = 0;
		param3[6] = agent.get("money");
		param3[7] = MathUtil.getBigDecimal(agent.get("coin")).add(validBet).longValue();
		param3[8] = agent.get("commission");
		param3[9] = Constant.TransactionDetailType.AGENTSBETCOMMISSION.getValue();

		sqlArray[4] = getUpdategameRecordDailySql(true);
		Object param4[] = new Object[1];
		param4[0] = userCommission.getLong("id");

		jdbcUtil.transactionExecuteUpdate(sqlArray, param0, param1, param2, param3, param4);

	}

	// 用户充值代理商返佣
	@Override
	public void updateUserRechargeCommission(JSONObject userCommission, Map<Long, BigDecimal> agentHierarchy) {
		Long userId = userCommission.getLong("userId");
		BigDecimal amount = MathUtil.getBigDecimal(userCommission.get("amount"));
		if (userId == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			log.info("会员不存在 userid:{}", userId);
			updateRanklingDaily(userCommission.getLong("id"));
			return;
		}
		JSONObject agent = getAgentByUserId(userId);
		if (agent == null || agent.isEmpty()) {
			log.info("代理商不存在 userid:{}", userId);
			updateRanklingDaily(userCommission.getLong("id"));
			return;
		}
		log.info("代理商：{}", agent);
		amount = amount.multiply(MathUtil.getBigDecimal(agentHierarchy.get(agent.getLong("agentHierarchyId"))))
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		String sqlArray[] = new String[4];
		sqlArray[0] = getAddUserCommissionSql();
		Object param0[] = new Object[2];
		param0[0] = amount;
		param0[1] = agent.getLong("userId");

		sqlArray[1] = getAddUserUserRecommendationTotalCommissionSql();
		Object param1[] = new Object[2];
		param1[0] = amount;
		param1[1] = agent.getLong("id");

		sqlArray[2] = getInsertUserTransactionRecordSql();
		Object param2[] = new Object[10];
		param2[0] = agent.get("userId");
		param2[1] = agent.get("account");
		param2[2] = Constant.TransactionType.REBATE.getValue();
		param2[3] = OrderNoUtil.getOrderNo();
		param2[4] = amount;
		param2[5] = 0;
		param2[6] = agent.get("money");
		param2[7] = agent.get("coin");
		param2[8] = MathUtil.getBigDecimal(agent.get("commission")).add(amount);
		param2[9] = Constant.TransactionDetailType.AGENTSRECHARGECOMMISSION.getValue();

		sqlArray[3] = getUpdateRanklingSql();
		Object param3[] = new Object[1];
		param3[0] = userCommission.getLong("id");
		jdbcUtil.transactionExecuteUpdate(sqlArray, param0, param1, param2, param3);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	private JSONObject getUser(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id ,account,user_type userType,forbidden_enable forbiddenEnable,");
		sql.append(" frozen_enable frozenEnable,coin,commission ,money,vip_id vipId");
		sql.append(" from `user`");
		sql.append(" where  id=? ");
		JSONObject data = null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 为用户增加佣金sql
	 * 
	 * @return
	 */
	private String getAddUserCommissionSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(" update `user` set  commission=commission+?,");
		sql.append(" version=version+1,update_time=now()");
		sql.append(" where  id=? ");
		return sql.toString();
	}

	/**
	 * 为代理商总佣金加上当前佣金sql
	 * 
	 * @return
	 */
	private String getAddUserUserRecommendationTotalCommissionSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(" update user_recommendation set  amount=amount+?,");
		sql.append(" version=version+1,update_time=now()");
		sql.append(" where  id=? ");
		return sql.toString();

	}

	/**
	 * 为代理商总金币加上当前金币sql
	 * 
	 * @return
	 */
	private String getAddUserRecommendationTotalCoinSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(" update user_recommendation set  coin=coin+?,");
		sql.append(" version=version+1,update_time=now()");
		sql.append(" where  id=? ");
		return sql.toString();

	}

	/**
	 * 为代理商的下线用户贡献总金币加上当前金币sql
	 * 
	 * @return
	 */
	private String getAddUserRecommendationRecordTotalCoinSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(" update user_recommendation_record set  coin=coin+?,");
		sql.append(" version=version+1,update_time=now()");
		sql.append(" where  user_id=? ");
		return sql.toString();

	}

	/*
	 * 为用户增加金币Sql
	 */
	private String getAddUserCoinSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(" update `user` set  coin=coin+?,");
		sql.append(" version=version+1,update_time=now()");
		sql.append(" where  id=? ");
		return sql.toString();
	}

	/**
	 * 增加用户资金交易记录sql
	 * 
	 * @return
	 */
	private String getInsertUserTransactionRecordSql() {
		StringBuilder insertTransactionSql = new StringBuilder();
		insertTransactionSql.append(" INSERT INTO user_transaction_record (");
		insertTransactionSql.append(" `user_id`, `user_account`, `type`, `order_no`,");
		insertTransactionSql.append(" `amount`,  `fristrecharge`, `money`, `coin`,");
		insertTransactionSql.append(" `commission`, detail_type,create_time,update_time)");
		insertTransactionSql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,now(),now())");

		return insertTransactionSql.toString();
	}

	/**
	 * 每日下注统计表标记代理商已返佣或者是用户已返水sql
	 * 
	 * @param isAgent
	 * @return
	 */
	private String getUpdategameRecordDailySql(boolean isAgent) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update game_record_daily set");
		if (isAgent) {
			sql.append(" agent_return = 1");
		} else {
			sql.append(" user_return = 1");
		}
		sql.append(" where  id=? ");
		return sql.toString();
	}

	private String getUpdateRanklingSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ranking_list_day set");
		sql.append(" type = 2");
		sql.append(" where  id=? ");
		return sql.toString();
	}

	private Integer updateRanklingDaily(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ranking_list_day set");
		sql.append(" type = 3");
		sql.append(" where  id=? ");
		return jdbcUtil.update(sql.toString(), id);

	}

	/**
	 * 每日下注统计表记录代理商返佣或者是用户返水异常sql
	 * 
	 * @param id
	 * @param isAgent
	 * @param abnormalContent
	 * @return
	 */
	private Integer updategameRecordDaily(Long id, boolean isAgent, String abnormalContent) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update game_record_daily set");
		if (isAgent) {
			sql.append(" agent_abnormal = 1,");
			sql.append(" agent_abnormal_content = ?");
		} else {
			sql.append(" abnormal = 1,");
			sql.append(" abnormal_content = ?");
		}
		sql.append(" where  id=? ");
		return jdbcUtil.update(sql.toString(), abnormalContent, id);

	}

	@Override
	public JSONArray getAgentList() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id ,account,user_type userType,forbidden_enable forbiddenEnable,");
		sql.append(" frozen_enable frozenEnable,coin,commission ,money");
		sql.append(" from `user`");
		sql.append(" where  agent_enable=1 and forbidden_enable=0");
		JSONArray dataArray = null;
		try {
			dataArray = jdbcUtil.selectByParamReturnJsonArray(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataArray;
	}

	@Override
	public JSONArray getUserAgentHierarchy() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id,name,type,coin,");
		sql.append(" commission ,proportion");
		sql.append(" from user_agent_hierarchy");
		sql.append(" where  delete_status=0");
		JSONArray dataArray = null;
		try {
			dataArray = jdbcUtil.selectByParamReturnJsonArray(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataArray;
	}

	private JSONObject getAgentByUserId(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id userId,a.account,a.coin,a.money,");
		sql.append(" a.commission,b.id,b.agent_hierarchy_id agentHierarchyId ");
		sql.append(" from user a");
		sql.append(" LEFT JOIN user_recommendation b on a.id= b.user_id");
		sql.append(" WHERE a.agent_enable = 1 and a.forbidden_enable =0 ");
		sql.append(" and a.id = (SELECT  superiors_id FROM user where id =?)");
		JSONObject data = null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	private void updateUserStatus(Long id, int abnormal, Long hierarchyId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE `user` SET ");
		sql.append(" abnormal_enable=?,");
		sql.append(" risk_hierarchy_id=?");
		sql.append(" Where id = ?");
		if (abnormal == 0) {
			sql.append(" and abnormal_enable = 1");
		}
		int i = jdbcUtil.update(sql.toString(), abnormal, hierarchyId, id);
		if (i > 0) {
			// 把用户的层级、是否异常 推送给游戏服
			pushService.pushUserInfo(new UserInfoMessage(id, hierarchyId));
		}
	}

	@Override
	public void checkUserAbnormal(JSONObject userMoney, JSONArray riskArray, Long defHierarchyId) {
		// Constant.COIN_RATE;
		if (userMoney == null || riskArray == null || riskArray.isEmpty()) {
			return;
		}
		Long oldRiskHierarchyId = userMoney.getLong("riskHierarchyId");
		// 金额和金币转换比例
		BigDecimal rate = MathUtil.getBigDecimal(Constant.COIN_RATE);
		// 充值金额
		BigDecimal rechargeAmount = MathUtil.getBigDecimal(userMoney.get("amount"));
		// 用户当前金额
		BigDecimal money = MathUtil.getBigDecimal(userMoney.get("money"));
		// 用户当前金币
		BigDecimal coin = MathUtil.getBigDecimal(userMoney.get("coin"));
		// 充值前金额
		BigDecimal preMoney = MathUtil.getBigDecimal(userMoney.get("preMoney"));
		coin = coin.add(money.multiply(rate));
		BigDecimal coinTotal= coin;
		rechargeAmount = rechargeAmount.multiply(rate);
		preMoney = preMoney.multiply(rate);
		// 减去充值前总金币值
		coin = coin.subtract(preMoney);
		Long riskHierarchyId = null;
		int abnormal = 0;
		JSONObject riskObject=null;
		if (coin.compareTo(rechargeAmount) > 0 && rechargeAmount.compareTo(BigDecimal.ZERO) > 0) {
			int multiple = coin.divide(rechargeAmount, 0, BigDecimal.ROUND_HALF_UP).intValue();
			log.info("checkUserAbnormal  multiple:{} userMoney:{}", multiple, userMoney);
			for (int i = 0; i < riskArray.size(); i++) {
				JSONObject risk = riskArray.getJSONObject(i);
				int riskVal = MathUtil.getBigDecimal(risk.get("riskVal")).intValue();
				if (multiple < riskVal) {
					if (i == 0) {
						riskHierarchyId = 0l;
					} else {
						riskObject=riskArray.getJSONObject(i - 1);
						riskHierarchyId = riskObject.getLong("hierarchyId");
					}
					break;
				} else if (multiple == riskVal) {
					riskHierarchyId = risk.getLong("hierarchyId");
					riskObject = risk;
					break;
				}
			}
			if (riskHierarchyId == null) {
				riskObject = riskArray.getJSONObject(riskArray.size() - 1);
				riskHierarchyId = riskObject.getLong("hierarchyId");
				abnormal = 1;
			} else if (riskHierarchyId == 0l) {
				riskHierarchyId = defHierarchyId;
				abnormal = 0;
			} else {
				abnormal = 1;
			}
		} else {
			riskHierarchyId = defHierarchyId;
			abnormal = 0;
		}
		// 层级变更
		if (oldRiskHierarchyId != riskHierarchyId) {
			if(riskObject==null) {
				riskObject=new JSONObject();
				riskObject.put("hierarchyId", riskHierarchyId);
				riskObject.put("riskType", riskArray.getJSONObject(0).get("riskType"));
				riskObject.put("riskVal", 0);
				
			}
			insertRiskRecord(userMoney.getLong("userId"), userMoney.getString("userAccount"),
					coinTotal.longValue(), rechargeAmount, riskObject);
			updateUserStatus(userMoney.getLong("userId"), abnormal, riskHierarchyId);
		}

	}
	
	private void insertRiskRecord(Long userId,String userAccount,Long coin,BigDecimal rechargeVal,JSONObject risk) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append(" INSERT INTO user_risk_record (");
		insertSql.append(" `user_id`, `user_account`, `risk_type`,");
		insertSql.append(" `hierarchy_id`,  `risk_val`, `coin`,recharge_val,");
		insertSql.append(" create_time,update_time)");
		insertSql.append(" VALUES (?,?,?,?,?,?,?,now(),now())");
		jdbcUtil.insert(insertSql.toString(), userId,userAccount,risk.get("riskType"),
					risk.get("hierarchyId"),risk.get("riskVal"),coin,rechargeVal);
		
	}

	@Override
	public void checkUserAbnormalForMoney(JSONObject userMoney, JSONArray riskArray, Long defHierarchyId) {
		// Constant.COIN_RATE;
		if (userMoney == null || riskArray == null || riskArray.isEmpty()) {
			return;
		}
		Long oldRiskHierarchyId = userMoney.getLong("riskHierarchyId");
		// 金额和金币转换比例
		BigDecimal rate = MathUtil.getBigDecimal(Constant.COIN_RATE);
		// 用户当前金额
		BigDecimal money = MathUtil.getBigDecimal(userMoney.get("money"));
		// 用户当前金币
		BigDecimal coin = MathUtil.getBigDecimal(userMoney.get("coin"));
		// 用户当前余额
		money = coin.divide(rate, 2, BigDecimal.ROUND_HALF_UP).add(money);
		Long riskHierarchyId = null;
		int abnormal = 0;
		JSONObject riskObject = null;
		if (money.compareTo(BigDecimal.ZERO) > 0) {
			log.info("checkUserAbnormal money :{}  userMoney:{}", money, userMoney);
			for (int i = 0; i < riskArray.size(); i++) {
				JSONObject risk = riskArray.getJSONObject(i);
				BigDecimal riskVal = MathUtil.getBigDecimal(risk.get("riskVal"));
				int m = money.compareTo(riskVal);
				if (m < 0) {
					if (i == 0) {
						riskHierarchyId = 0l;
					} else {
						riskObject = riskArray.getJSONObject(i - 1);
						riskHierarchyId = riskObject.getLong("hierarchyId");
						
					}
					break;
				} else if (m == 0) {
					riskObject = risk;
					riskHierarchyId = risk.getLong("hierarchyId");
					break;
				}
			}
			if (riskHierarchyId == null) {
				riskObject = riskArray.getJSONObject(riskArray.size() - 1);
				riskHierarchyId = riskObject.getLong("hierarchyId");
				abnormal = 1;
			} else if (riskHierarchyId == 0l) {
				riskHierarchyId = defHierarchyId;
				abnormal = 0;
			} else {
				abnormal = 1;
			}
		} else {
			riskHierarchyId = defHierarchyId;
			abnormal = 0;
		}
		// 层级变更
		if (oldRiskHierarchyId != riskHierarchyId) {
			if(riskObject==null) {
				riskObject=new JSONObject();
				riskObject.put("hierarchyId", riskHierarchyId);
				riskObject.put("riskType", riskArray.getJSONObject(0).get("riskType"));
				riskObject.put("riskVal", 0);
			}
			insertRiskRecord(userMoney.getLong("userId"), userMoney.getString("userAccount"),
					money.multiply(rate).longValue(), BigDecimal.ZERO, riskObject);
			updateUserStatus(userMoney.getLong("userId"), abnormal, riskHierarchyId);
		}

	}

	@Override
	public JSONArray scanUserNoRecharge() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT u.money, u.coin,u.id userId,u.account userAccount,u.risk_hierarchy_id riskHierarchyId from `user` u");
		sql.append(" where no_scan=0 and u.game_info_id>0 and  id NOT in ");
		sql.append(" (SELECT DISTINCT user_id from order_recharge WHERE `status` = 2  )");
		JSONArray data = null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("scanUserNoRecharge error ", e);
		}
		return data;
	}
	@Override
	public JSONObject getUserById(Long id) {
		return getUser(id);
	}
	@Override
	public JSONArray findUserListByBankruptcy(String startTime, int detailType, int num, int maxNum,Long vipId) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT a.id,a.account,a.money,a.coin,a.commission from `user` a ");
		sql.append(" LEFT JOIN user_balance b on a.id = b.user_id ");
		sql.append(" where  a.money = 0 and a.game_info_id>0 and a.coin+IFNULL(b.money,0)+IFNULL(b.unprofit_money,0)<=? ");
		sql.append(" and a.forbidden_enable=0 and a.vip_id >=? and  a.id not in (");
		sql.append(" SELECT user_id from user_gift_record ");
		sql.append(" where type =?  and detail_type = ? and create_time>=?");
		sql.append(" GROUP BY user_id HAVING count(1)>=? )");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(),num*Constant.DB_COIN_RATE,
					vipId,Constant.ACTIVITYTYPE_1,detailType,startTime,maxNum);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("scanUserNoRecharge error ",e);
		}
		return data;
	}

	@Override
	public void updateUserGameServer(String checkTime) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE `user` ");
		sql.append(" SET game_info_id = 0,");
		sql.append(" game_server_id = 0");
		sql.append(" WHERE update_time <= ? ");
		jdbcUtil.update(sql.toString(), checkTime);

	}


	@Override
	public void updateUserEveryDateBet(Long userId, String userAccount, Long validBet, String date , boolean isGmUser) {
		StringBuilder sb =new StringBuilder();
		sb.append("select * from user_bet_record where user_id= ? ORDER BY create_time DESC LIMIT 1");
		try {
			JSONObject jsonObject = jdbcUtil.selectByParamReturnJsonObject(sb.toString(), userId);
			// 如果有数据，并且是今天的数据，那么就修改
			if (jsonObject != null&&jsonObject.size()>0) {
				if (date.equals(DateUtils.format(jsonObject.getDate("create_time")))) {
				  StringBuilder sql  = new  StringBuilder();
				  sql.append("update user_bet_record set bet_coins = bet_coins+?,old_bet=old_bet+?,update_time=now()");
				  sql.append(" where user_id=? and DATE_FORMAT(create_time,'%Y-%m-%d') = ?");
				  jdbcUtil.update(sql.toString(),validBet,validBet,userId,date);
				} else {// 否则新增，并加上昨天的历史有效下注
					Long oldBet = jsonObject.getLong("old_bet");
					     oldBet+=validBet;
					StringBuilder        insertSql    = new StringBuilder();
					insertSql.append(" INSERT INTO user_bet_record (");
					insertSql.append(" `user_id`, `user_account`, `bet_coins`,");
					insertSql.append(" `old_bet`,  `gm_user`,");
					insertSql.append(" create_time,update_time)");
					insertSql.append(" VALUES (?,?,?,?,?,now(),now())");
					jdbcUtil.insert(insertSql.toString(), userId,userAccount,validBet, oldBet,isGmUser);
				}

			} else {
//     没有的话就新增
//				UserBetRecordEntity entity = new UserBetRecordEntity();
//				entity.setUserId(userId);
//				entity.setUserAccount(userAccount);
//				entity.setBetCoins(betCoins);
//				entity.setGmUser(isGmUser);
//				entity.setOldBet(betCoins);
//				baseMapper.insert(entity);
				StringBuilder         insertSql    = new StringBuilder();
				insertSql.append(" INSERT INTO user_bet_record (");
				insertSql.append(" `user_id`, `user_account`, `bet_coins`,");
				insertSql.append(" `old_bet`,  `gm_user`,");
				insertSql.append(" create_time,update_time)");
				insertSql.append(" VALUES (?,?,?,?,?,now(),now())");
				jdbcUtil.insert(insertSql.toString(), userId,userAccount,validBet,validBet,isGmUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}
