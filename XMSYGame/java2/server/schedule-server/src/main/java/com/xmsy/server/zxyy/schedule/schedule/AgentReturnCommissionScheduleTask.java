package com.xmsy.server.zxyy.schedule.schedule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.GameRecordService;
import com.xmsy.server.zxyy.schedule.server.RankingListService;
import com.xmsy.server.zxyy.schedule.server.UserService;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;
import com.xmsy.server.zxyy.schedule.utils.SysConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 代理商返佣 定时任务
 * 
 * @author adu
 *
 */
@Slf4j
@Component
public class AgentReturnCommissionScheduleTask {
	@Autowired
	private RankingListService rankingListService;
	@Autowired
	private UserService userService;
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	/**
	 * 查询每天统计用户下注表，将返佣更新到代理商金币中
	 */
//	@Scheduled(cron = "0 0 0/1 * * ? ")
//	@Scheduled(fixedRate = 60000 * 2)
	public void AgentBetCommission() {
		try {

			JSONArray dataArray = gameRecordService.findUserCommissionList();
			if (CollectionUtils.isEmpty(dataArray)) {
				log.info("AgentBetCommission 查无记录");
				return;
			}
			JSONArray agentHierarchyList = userService.getUserAgentHierarchy();
			if (CollectionUtils.isEmpty(agentHierarchyList)) {
				log.info("用户代理层级设置 查无记录");
				return;
			}
			// proportion
			Map<Long, BigDecimal> agentHierarchy = new HashMap<>();
			for (int i = 0; i < agentHierarchyList.size(); i++) {
				JSONObject obj = agentHierarchyList.getJSONObject(i);
				agentHierarchy.put(obj.getLong("id"), MathUtil.getBigDecimal(obj.get("proportion")));
			}
			for (int i = 0; i < dataArray.size(); i++) {
				updateUserCommission(dataArray.getJSONObject(i), agentHierarchy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateUserCommission(JSONObject userCommission, Map<Long, BigDecimal> agentHierarchy) {
		try {
			threadPoolTaskExecutor.execute(new Thread(() -> {
				log.info("userCommission {}", userCommission);
				log.info("agentHierarchy {}", agentHierarchy);
				userService.updateUserCommission(userCommission, agentHierarchy);
			}));
		} catch (Exception e) {
			log.error("updateUserCoin error {}", e);
		}
	}

	/**
	 * 查询会员每天完成的充值，将返佣更新到代理商金币中
	 */
//	@Scheduled(cron = "0 0 0/1 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void AgentRechargeCommission() {
		try {
			// // 获取统计结果
			JSONArray dataArray = rankingListService.findRankingListByNeed(SysConstant.getRANKINGLIST_RECHARGE());
			if (CollectionUtils.isEmpty(dataArray)) {
				log.info("AgentRechargeCommission 查无记录");
				return;
			}
			JSONArray agentHierarchyList = userService.getUserAgentHierarchy();
			if (CollectionUtils.isEmpty(agentHierarchyList)) {
				log.info("用户代理层级设置 查无记录");
				return;
			}
			// proportion
			Map<Long, BigDecimal> agentHierarchy = new HashMap<>();
			for (int i = 0; i < agentHierarchyList.size(); i++) {
				JSONObject obj = agentHierarchyList.getJSONObject(i);
				agentHierarchy.put(obj.getLong("id"), MathUtil.getBigDecimal(obj.get("commission")));
			}
			for (int i = 0; i < dataArray.size(); i++) {
				updateUserRechargeCommission(dataArray.getJSONObject(i), agentHierarchy);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateUserRechargeCommission(JSONObject userCommission, Map<Long, BigDecimal> agentHierarchy) {
		try {
			threadPoolTaskExecutor.execute(new Thread(() -> {
				log.info("userCommission {}", userCommission);
				log.info("agentHierarchy {}", agentHierarchy);
				userService.updateUserRechargeCommission(userCommission, agentHierarchy);
			}));
		} catch (Exception e) {
			log.error("updateUserCoin error {}", e);
		}
	}

}
