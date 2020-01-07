package com.xmsy.server.zxyy.schedule.server.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.entity.RecommendationGradeEntity;
import com.xmsy.server.zxyy.schedule.entity.UserBetEntity;
import com.xmsy.server.zxyy.schedule.entity.UserSumBetEntity;
import com.xmsy.server.zxyy.schedule.event.PushService;
import com.xmsy.server.zxyy.schedule.event.param.UserInfoMessage;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.UserRecommendationService;
import com.xmsy.server.zxyy.schedule.utils.Constant;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;
import com.xmsy.server.zxyy.schedule.utils.OrderNoUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userRecommendation")
public class UserRecommendationServiceImpl implements UserRecommendationService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Autowired
	private PushService pushService;
	// 数据转money比例/
	private Long COIN_RATE_MONEY = Constant.CLIENT_COIN_RATE * Constant.DB_COIN_RATE;

	/**
	 * 获取昨日开启团队权限的所有（有效投注>0)的会员
	 * 
	 * @return
	 */
	@Override
	public List<UserSumBetEntity> selectYestadayTeamEnableUserList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT	a.user_id AS userId, a.user_account AS userAccount , ");
		sql.append(
				" IFNULL(SUM(CASE WHEN DATE_FORMAT(c.create_time, '%Y-%m-%d') <= DATE_SUB(curdate(), INTERVAL 1 DAY)  THEN c.bet_coins ELSE 0 END),0) AS betCoins, ");
		sql.append(
				" IFNULL(SUM(CASE WHEN DATE_FORMAT(c.create_time, '%Y-%m-%d') = DATE_SUB(curdate(), INTERVAL 1 DAY) AND b.user_parant_distance = 0 THEN c.bet_coins ELSE 0 END),0) AS yesterDaybetCoins ");
		sql.append(" FROM user_recommendation a ");
		sql.append(" LEFT JOIN user_recommendation_tree b ON b.parant_user_id = a.user_id ");
		sql.append(" LEFT JOIN user_bet_record c ON c.user_id = b.user_id ");
		sql.append(" WHERE  a.delete_status=0 AND c.bet_coins > 0");
		sql.append(" GROUP BY a.user_id ORDER BY a.user_id");
		List<UserSumBetEntity> data = new ArrayList<UserSumBetEntity>();
		try {
			data = (List<UserSumBetEntity>) JSON.parseArray(
					jdbcUtil.selectByParamReturnJsonArray(sql.toString()).toString(), UserSumBetEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("scanUserNoRecharge error ", e);
		}
		return data;
	}

	/**
	 * 获取昨日所有（有效投注>0)的会员
	 * 
	 * @return
	 */
	@Override
	public List<UserBetEntity> selectYestadayUserList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.user_id AS userId,a.user_account AS userAccount,d.parant_user_id AS parantUserId, ");
		sql.append(
				" IFNULL(SUM(CASE WHEN DATE_FORMAT(c.create_time, '%Y-%m-%d') <= DATE_SUB(curdate(), INTERVAL 1 DAY)  THEN c.bet_coins ELSE 0 END),0) AS betCoins, ");
		sql.append(
				" IFNULL(SUM(CASE WHEN DATE_FORMAT(c.create_time, '%Y-%m-%d') = DATE_SUB(curdate(), INTERVAL 1 DAY)  THEN c.bet_coins ELSE 0 END),0) AS yesterDaybetCoins ");
		sql.append(" FROM user_recommendation a ");
		sql.append(" LEFT JOIN user_recommendation_tree b ON b.parant_user_id = a.user_id ");
		sql.append(" LEFT JOIN user_bet_record c ON c.user_id = b.user_id ");
		sql.append(" LEFT JOIN user_recommendation_tree d ON d.user_id= a.user_id AND d.user_parant_distance=1 ");
		sql.append(
				" WHERE a.delete_status=0 AND c.bet_coins > 0 GROUP BY a.user_id,d.parant_user_id ORDER BY a.user_id ");
		List<UserBetEntity> data = new ArrayList<UserBetEntity>();
		try {
			data = JSON.parseArray(jdbcUtil.selectByParamReturnJsonArray(sql.toString()).toString(),
					UserBetEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("scanUserNoRecharge error ", e);
		}
		return data;
	}

	// 为用户返佣
	@Override
	public void updatUserCommissionRebate(List<UserBetEntity> userList, List<UserSumBetEntity> teamList) {
		List<RecommendationGradeEntity> gradeList = selectRecommendationGradeList();
		for (UserSumBetEntity team : teamList) {// 所有代理的盈利情况
			BigDecimal commission = BigDecimal.ZERO;
			if ( team.getBetCoins() == null || team.getYesterDaybetCoins() == null ||team.getBetCoins() == 0 
					|| team.getYesterDaybetCoins() == 0) {
				continue;
			}
			for (RecommendationGradeEntity grade : gradeList) {// 遍历等级，看用户匹配哪个等级
				// 如果最大值为0,表示无上限，只需要大于等于最小值
				if (grade.getMaxValue() == 0 && team.getBetCoins() / COIN_RATE_MONEY >= grade.getMiniValue()) {
					team.setRewardRate(grade.getRewardRate());// 自己的级别返利
					break;
				} else if (grade.getMaxValue() > team.getBetCoins() / COIN_RATE_MONEY
						&& grade.getMiniValue() <= team.getBetCoins() / COIN_RATE_MONEY) {
					team.setRewardRate(grade.getRewardRate());// 自己的级别返利
					break;
				}
			}
			// 统计下级产生的佣金
			commission = calculationSubordinateCommission(team, userList, gradeList);
			if (commission.compareTo(BigDecimal.ZERO) == 1) {
				updateAgentInfo(team, commission);
			}

		}

	}

	/**
	 * 为代理统计下级列表
	 * 
	 * @param sumBetEntity
	 *            要计算的代理
	 * @param userList
	 *            带有上级Id的代理
	 * @param gradeList
	 *            等级列表
	 */
	private BigDecimal calculationSubordinateCommission(UserSumBetEntity sumBetEntity, List<UserBetEntity> userList,
			List<RecommendationGradeEntity> gradeList) {
		// 如果用户
		BigDecimal commission = BigDecimal.ZERO;// 下级总佣金
		for (UserBetEntity user : userList) {
			BigDecimal subordinateCommission = BigDecimal.ZERO;// 下级提供的佣金
			// 如果下级昨日打码为0,不需要统计
			if (!sumBetEntity.getUserId().equals(user.getParantUserId()) || user.getYesterDaybetCoins() == 0) {
				continue;
			}
			user.setParantUserAccount(sumBetEntity.getUserAccount());
			for (RecommendationGradeEntity grade : gradeList) {// 遍历等级，看用户匹配哪个等级
				if (grade.getMaxValue() == 0 && grade.getMiniValue() <= user.getBetCoins() / COIN_RATE_MONEY) {
					// 通过级差获取到每个用户给与上级的佣金金额
					subordinateCommission = new BigDecimal(user.getYesterDaybetCoins())
							.multiply(sumBetEntity.getRewardRate().subtract(grade.getRewardRate()))
							.divide(new BigDecimal(COIN_RATE_MONEY)).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;
				} else if (grade.getMaxValue() > user.getBetCoins() / COIN_RATE_MONEY
						&& grade.getMiniValue() <= user.getBetCoins() / COIN_RATE_MONEY) {
					// 通过级差获取到每个用户给与上级的佣金金额
					subordinateCommission = new BigDecimal(user.getYesterDaybetCoins())
							.multiply(sumBetEntity.getRewardRate().subtract(grade.getRewardRate()))
							.divide(new BigDecimal(COIN_RATE_MONEY)).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;
				}

			}
			// 如果下级提供的佣金大于0
			if (subordinateCommission.compareTo(BigDecimal.ZERO) == 1) {
				log.info("calculationSubordinateCommission===》》》》开始新增返佣记录] ");
				boolean result = insertCommissionRecord(user, subordinateCommission);// 新增返佣记录
				if (result) {
					commission = commission.add(subordinateCommission);
				}
			}

		}
		return commission;
	}

	/**
	 * 新增返佣记录
	 * 
	 * @param user
	 *            提供佣金的用户
	 * @param subordinateCommission
	 *            产生的佣金
	 */
	private boolean insertCommissionRecord(UserBetEntity user, BigDecimal subordinateCommission) {
		StringBuilder insertTransactionSql = new StringBuilder();
		Long user_id = user.getParantUserId();// 收益佣金人Id
		String user_account = user.getParantUserAccount();// 收益佣金人账号
		Long provide_user_id = user.getUserId();// 提供佣金人Id
		String provide_user_account = user.getUserAccount();// 提供佣金人账号
		BigDecimal commission = subordinateCommission;// 佣金
		insertTransactionSql.append(" INSERT INTO user_rebate_commission_record (");
		insertTransactionSql.append(" `user_id`, `user_account`, `provide_user_id`,");
		insertTransactionSql.append(" `provide_user_account`,  `commission`,`record_date`, ");
		insertTransactionSql.append(" create_time,update_time)");
		insertTransactionSql.append(" VALUES (?,?,?,?,?,now(),now(),now())");
		int i = jdbcUtil.insert(insertTransactionSql.toString(), user_id, user_account, provide_user_id,
				provide_user_account, commission);
		if (i == 0) {
			log.error("[insertCommissionRecord=>>>>>>>>新增返佣记录失败] id {}, account", user.getUserId(),
					user.getUserAccount(), subordinateCommission);
			return false;
		}
		return true;

	}

	/**
	 * 修改代理商的佣金详情
	 * 
	 * @param team
	 *            获得佣金的代理商
	 * @param commission
	 *            获得的佣金
	 */
	private void updateAgentInfo(UserSumBetEntity entity, BigDecimal commission) {
		// 修改邀请详情表
		StringBuffer updateRecommendationSql = new StringBuffer();
		updateRecommendationSql.append(" UPDATE user_recommendation");
		updateRecommendationSql.append("  SET update_time=now(),amount=amount+?");
		updateRecommendationSql.append(" where user_id=?");
		int recommendationSql = jdbcUtil.update(updateRecommendationSql.toString(), commission, entity.getUserId());
		if (recommendationSql == 0) {
			log.error("[updateAgentInfo===》》》》修改邀请详情表失败] id {}", entity.getUserId());
		}
		// 修改用户佣金余额
		StringBuffer updateUserSql = new StringBuffer();
		updateUserSql.append(" UPDATE user");
		updateUserSql.append("  SET update_time=now(),commission=commission+?");
		updateUserSql.append(" where id=?");
		int updateUser = jdbcUtil.update(updateUserSql.toString(), commission, entity.getUserId());
		if (updateUser == 0) {
			log.error("[updateAgentInfo===》》》》修改用户佣金余额失败] id {}, commission {}", entity.getUserId(), commission);
		}
		// 获取用户详情
		JSONObject obj = new JSONObject();
		StringBuffer selectUserSql = new StringBuffer();
		selectUserSql.append(" select * from user where id=?");
		try {
			obj = jdbcUtil.selectByParamReturnJsonObject(selectUserSql.toString(), entity.getUserId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (obj == null) {
			log.error("[updateAgentInfo===》》》》用户信息获取失败] id {}, account", entity.getUserId(), entity.getUserAccount());
		}
		// 新增交易记录
		StringBuffer insertUserTransactionSql = new StringBuffer();
		insertUserTransactionSql.append(" INSERT INTO user_transaction_record (");
		insertUserTransactionSql.append(" `user_id`, `user_account`, `type`, `order_no`,");
		insertUserTransactionSql.append(" `amount`,  `fristrecharge`, `money`, `coin`,");
		insertUserTransactionSql.append(" `commission`, detail_type,create_time,update_time)");
		insertUserTransactionSql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,now(),now())");
		int insertUserTransaction = jdbcUtil.insert(insertUserTransactionSql.toString(), entity.getUserId(),
				entity.getUserAccount(), Constant.TransactionType.REBATE.getValue(), OrderNoUtil.getOrderNo(),
				commission, 0, obj.getBigDecimal("money"), obj.get("coin"), obj.get("commission"),
				Constant.TransactionDetailType.AGENTSBETCOMMISSION.getValue());

		if (insertUserTransaction == 0) {
			log.error("[updateAgentInfo===》》》》新增交易记录失败] id {}, account", entity.getUserId(), entity.getUserAccount());
		}
		String title = "返佣通知";
		String content = "用户打码返佣; 金币 :%s, 请查收";
		// 邮件推送
		boolean result = sentMessage(entity.getUserId(), entity.getUserAccount(), title,
				String.format(content, commission));
		if (result) {
			UserInfoMessage userInfoMessage = new UserInfoMessage(entity.getUserId(), 0L, 1);
			pushService.pushUserInfoForClient(userInfoMessage);
		}

	}

	/**
	 * 新增邮件
	 * 
	 * @return
	 */
	private Boolean sentMessage(Long userId, String account, String title, String content) {
		Boolean isOk = false;
//		StringBuilder insertSql = new StringBuilder();
//		insertSql.append(" INSERT INTO sys_message_management (");
//		insertSql.append(" title, content,content_type,target_object,failure_time,effect_time,");
//		insertSql.append(" user_account,readonly,create_time,update_time)");
//		insertSql.append(" VALUES (?,?,?,?,?,?,?,?,now(),now())");
		Date EFFECT_TIME = new Date(); // 邮件生效时间
		Date FAIL_TIME = DateUtils.addDateDays(EFFECT_TIME, 7); // 邮件失效时间
//		Long messageId = jdbcUtil.insertReturnId(insertSql.toString(), title, content, 1, 1, FAIL_TIME, EFFECT_TIME,
//				account, 1);
//		if (messageId != null && messageId > 0) {
			StringBuilder insertMessageUserSql = new StringBuilder();
			insertMessageUserSql.append(" INSERT INTO sys_message_user (");
			insertMessageUserSql.append(" message_id, user_id,user_account,status,delete_message, ");
			insertMessageUserSql.append(" activity_type,activity_id,receive,create_time,update_time)");
			insertMessageUserSql.append(" title,content,content_type,failure_time,effect_time,readonly)");
			insertMessageUserSql.append(" VALUES (?,?,?,?,?,?,?,?,now(),now(),?,?,?,?,?,?)");
			int i = jdbcUtil.insert(insertMessageUserSql.toString(), 0, userId, account, 0, 0, 0, 0, 1,
					title,content,1,FAIL_TIME,EFFECT_TIME,1);
			isOk = i > 0;
//		} else {
//			log.info("保存邮件消息失败 ");
//		}
		return isOk;
	}

	/**
	 * 获取昨日所有（有效投注>0)的会员
	 * 
	 * @return
	 */
	private List<RecommendationGradeEntity> selectRecommendationGradeList() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT a.id,a.name,a.mini_value,a.max_value,a.reward_rate FROM user_recommendation_grade a WHERE a.delete_status=0 ");
		List<RecommendationGradeEntity> data = new ArrayList<RecommendationGradeEntity>();
		try {
			data = JSON.parseArray(jdbcUtil.selectByParamReturnJsonArray(sql.toString()).toString(),
					RecommendationGradeEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("scanUserNoRecharge error ", e);
		}
		return data;
	}

	@Override
	public List<UserBetEntity> selectYestadayUserListForValidBet() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT b.parant_user_id parantUserId,a.user_account userAccount,");
		sql.append(" a.user_id userId,a.bet_coins yesterDaybetCoins from user_bet_record a");
		sql.append(" LEFT JOIN user_recommendation_tree b ON b.user_id = a.user_id AND b.user_parant_distance=1");
		sql.append(" where  DATE_FORMAT(a.create_time, '%Y-%m-%d') =  DATE_SUB(curdate(), INTERVAL 1 DAY)  and b.parant_user_id is NOT null");
		List<UserBetEntity> data = new ArrayList<UserBetEntity>();
		try {
			data = JSON.parseArray(jdbcUtil.selectByParamReturnJsonArray(sql.toString()).toString(),
					UserBetEntity.class);
		} catch (Exception e) {
			log.error("selectYestadayUserListForValidBet error ", e);
		}
		return data;
	}

	@Override
	public void updatUserCommissionRebate(UserBetEntity userBetEntity,List<RecommendationGradeEntity> gradeList) {
		try {
			Long userBetCoin=sumTotalBetCoinByUserId(userBetEntity.getUserId()).longValue();
			Long parantUserBetCoin=sumTotalBetCoinByUserId(userBetEntity.getParantUserId()).longValue();
			BigDecimal userRewardRate = getUserRewardRate(gradeList, userBetCoin/COIN_RATE_MONEY); 
			BigDecimal parantUserRewardRate = getUserRewardRate(gradeList, parantUserBetCoin/COIN_RATE_MONEY); 
			// 下级产生的佣金
			BigDecimal commission = new BigDecimal(userBetEntity.getYesterDaybetCoins())
					.multiply(parantUserRewardRate.subtract(userRewardRate))
					.divide(new BigDecimal(COIN_RATE_MONEY))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			if (commission.compareTo(BigDecimal.ZERO) > 0) {
				updateAgentInfo(userBetEntity, commission);
			}
		} catch (Exception e) {
			
		}
		
	}
	/**
	 * 会员总打码量所处等级的返佣比例
	 * @param gradeList
	 * @param userBetCoin
	 * @return
	 */
	private BigDecimal getUserRewardRate(List<RecommendationGradeEntity> gradeList,Long userBetCoin) {
		BigDecimal userRewardRate=BigDecimal.ZERO;
		for (RecommendationGradeEntity grade : gradeList) {// 遍历等级，看用户匹配哪个等级
			// 如果最大值为0,表示无上限，只需要大于等于最小值
			if (grade.getMaxValue() == 0 && userBetCoin >= grade.getMiniValue()) {
				userRewardRate=grade.getRewardRate();// 自己的级别返利
				break;
			} else if (grade.getMaxValue() > userBetCoin 
					&& grade.getMiniValue() <= userBetCoin) {
				userRewardRate=grade.getRewardRate();// 自己的级别返利
				break;
			}
		}
		return userRewardRate;
	}
	
	private  BigDecimal sumTotalBetCoinByUserId(Long userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sum(b.bet_coins) betCoins from user_recommendation_tree a");
		sql.append(" LEFT JOIN user_bet_record b on a.user_id = b.user_id ");
		sql.append(" where a.parant_user_id=? ");
		sql.append(" and DATE_FORMAT(b.create_time, '%Y-%m-%d') <= DATE_SUB(curdate(), INTERVAL 1 DAY)   ");
		BigDecimal betCoins = BigDecimal.ZERO;
		try {
			JSONObject data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), userId);
			if(data != null) {
				betCoins=MathUtil.getBigDecimal(data.get("betCoins"));
			}
		} catch (Exception e) {
			log.info("sumTotalBetCoinByUserId user_id {}", userId);
			log.error("sumTotalBetCoinByUserId error ", e);
		}
		return betCoins;
	}

	@Override
	public List<RecommendationGradeEntity> getRecommendationGradeList() {
		return selectRecommendationGradeList();
	}
	
	/**
	 * 修改代理商的佣金详情
	 * 
	 * @param team
	 *            获得佣金的代理商
	 * @param commission
	 *            获得的佣金
	 */
	private void updateAgentInfo(UserBetEntity entity, BigDecimal commission) {
		// 获取用户详情
		JSONObject obj = new JSONObject();
		StringBuffer selectUserSql = new StringBuffer();
		selectUserSql.append(" select * from user where id=?");
		try {
			obj = jdbcUtil.selectByParamReturnJsonObject(selectUserSql.toString(), entity.getParantUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj == null || obj.isEmpty()) {
			log.error("[updateAgentInfo===》》》》用户信息获取失败] id {}", entity.getParantUserId());
			log.error("updateAgentInfo===》》》》代理商佣金返佣失败  {} 佣金：{}", entity,commission);
			return;
		}
		entity.setParantUserAccount(obj.getString("account"));
		
		String sqlArray[] = new String[5];
		
		//修改下级用户每日贡献佣金余额
		sqlArray[0] = getUserRebateCommissionRecordSql();
		Object param0[] = new Object[5];
		param0[0]=entity.getParantUserId();
		param0[1]=entity.getParantUserAccount();
		param0[2]=entity.getUserId();
		param0[3]=entity.getUserAccount();
		param0[4]=commission;
		
		// 修改邀请详情表
		StringBuffer updateRecommendationSql = new StringBuffer();
		updateRecommendationSql.append(" UPDATE user_recommendation");
		updateRecommendationSql.append("  SET update_time=now(),amount=amount+?");
		updateRecommendationSql.append(" where user_id=?");
		sqlArray[1] = updateRecommendationSql.toString();
		Object param1[] = new Object[2];
		param1[0]=commission;
		param1[1]=entity.getParantUserId();
		
		// 修改用户佣金余额
		StringBuffer updateUserSql = new StringBuffer();
		updateUserSql.append(" UPDATE user");
		updateUserSql.append("  SET update_time=now(),commission=commission+?");
		updateUserSql.append(" where id=?");
		sqlArray[2] = updateUserSql.toString();
		Object param2[] = new Object[2];
		param2[0]=commission;
		param2[1]=entity.getParantUserId();
		
		// 修改下级用户累计贡献佣金余额
		StringBuilder recommendationRecordSql = new StringBuilder();
		recommendationRecordSql.append(" update user_recommendation_record set  promoting_profit=promoting_profit+?,");
		recommendationRecordSql.append(" version=version+1,update_time=now()");
		recommendationRecordSql.append(" where  user_id=? ");
		sqlArray[3] = recommendationRecordSql.toString();
		Object param3[] = new Object[2];
		param3[0]=commission;
		param3[1]=entity.getUserId();
		
		// 新增交易记录
		StringBuffer insertUserTransactionSql = new StringBuffer();
		insertUserTransactionSql.append(" INSERT INTO user_transaction_record (");
		insertUserTransactionSql.append(" `user_id`, `user_account`, `type`, `order_no`,");
		insertUserTransactionSql.append(" `amount`,  `fristrecharge`, `money`, `coin`,");
		insertUserTransactionSql.append(" `commission`, detail_type,create_time,update_time)");
		insertUserTransactionSql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,now(),now())");
		sqlArray[4] = insertUserTransactionSql.toString();
		Object param4[] = new Object[10];
		param4[0]=entity.getParantUserId();
		param4[1]=entity.getParantUserAccount();
		param4[2]=Constant.TransactionType.REBATE.getValue(); 
		param4[3]=OrderNoUtil.getOrderNo();
		param4[4]=commission;
		param4[5]=0;
		param4[6]=obj.getBigDecimal("money");
		param4[7]=obj.get("coin");
		param4[8]=MathUtil.getBigDecimal(obj.get("commission")).add(commission);
		param4[9]=Constant.TransactionDetailType.AGENTSBETCOMMISSION.getValue();
		
		if(!jdbcUtil.transactionExecuteUpdate(sqlArray, param0, param1, param2,param3,param4)) {
			log.error("updateAgentInfo===》》》》代理商佣金返佣失败  {} 佣金：{}", entity,commission);
			return;
		}
		
		
		String title = "返佣通知";
		String content = "用户打码返佣:来自会员[%s]的返佣金币 :%s, 请查收";
		// 邮件推送
		boolean result = sentMessage(entity.getParantUserId(), entity.getParantUserAccount(), title,
				String.format(content,entity.getUserAccount(), commission));
		if (result) {
			UserInfoMessage userInfoMessage = new UserInfoMessage(entity.getUserId(), 0L, 1);
			pushService.pushUserInfoForClient(userInfoMessage);
		}

	}
	
	private String getUserRebateCommissionRecordSql() {
		StringBuilder insertTransactionSql = new StringBuilder();
		insertTransactionSql.append(" INSERT INTO user_rebate_commission_record (");
		insertTransactionSql.append(" `user_id`, `user_account`, `provide_user_id`,");
		insertTransactionSql.append(" `provide_user_account`,  `commission`,`record_date`, ");
		insertTransactionSql.append(" create_time,update_time)");
		insertTransactionSql.append(" VALUES (?,?,?,?,?,now(),now(),now())");
		return insertTransactionSql.toString();
	}
}
