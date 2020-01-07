package com.xmsy.server.zxyy.schedule.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户充值金额风控 定时任务
 *
 * @author adu
 *
 */
@Slf4j
@Component
public class UserRiskForRechargeScheduleTask {
	@Autowired
	private UserService userService;
//	@Autowired
//	private RechargeOrderService rechargeOrderService;
//	@Autowired
//	private UserRiskConfigService userRiskConfigService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	/**
	 * 查询用户充值，检查是否达到风控线
	 */
//	@Scheduled(cron = "0 0 0/1 * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60)
//	public void userRiskForRecharge() {
//		try {
//			JSONArray dataArray = rechargeOrderService.findUserRechargeLast();
//			if (dataArray == null || dataArray.size() == 0) {
//				log.debug("userRiskForRecharge 查无记录");
//				return;
//			}
//			JSONArray riskArray=userRiskConfigService.findRiskConfigList(Constant.RECHARGERISK);
////			Long defHierarchyId=userRiskConfigService.getDefHierarchy().getLong("id");
//			Long defHierarchyId=0L;
//			log.debug("userRiskForRecharge riskArray：{}",riskArray);
//			log.debug("userRiskForRecharge defHierarchyId：{}",defHierarchyId);
//			for (int i = 0; i < dataArray.size(); i++) {
//				checkUserAbnormal(dataArray.getJSONObject(i),riskArray,defHierarchyId);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void checkUserAbnormal(JSONObject userMoney,JSONArray riskArray,Long defHierarchyId) {
//		try {
//			threadPoolTaskExecutor.execute(new Thread(() -> {
//				userService.checkUserAbnormal(userMoney,riskArray,defHierarchyId);
//			}));
//		} catch (Exception e) {
//			log.error("checkUserAbnormal error {}", e);
//		}
//	}
//	/**
//	 * 查询用户当前余额，检查是否达到风控线
//	 */
//	@Scheduled(fixedDelay = 1000 * 60)
//	public void userRiskForNoRecharge() {
//		try {
//			JSONArray dataArray = userService.scanUserNoRecharge();
//			if (dataArray == null || dataArray.size() == 0) {
//				log.debug("userRiskForNoRecharge 查无记录");
//				return;
//			}
//			JSONArray riskArray=userRiskConfigService.findRiskConfigList(Constant.MONEYRISK);
////			Long defHierarchyId=userRiskConfigService.getDefHierarchy().getLong("id");
//			Long defHierarchyId=0L;
//			log.debug("userRiskForRecharge riskArray：{}",riskArray);
//			log.debug("userRiskForRecharge defHierarchyId：{}",defHierarchyId);
//			for (int i = 0; i < dataArray.size(); i++) {
//				checkUserAbnormalForMoney(dataArray.getJSONObject(i),riskArray,defHierarchyId);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public void checkUserAbnormalForMoney(JSONObject userMoney,JSONArray riskArray,Long defHierarchyId) {
		try {
			threadPoolTaskExecutor.execute(new Thread(() -> {
				userService.checkUserAbnormalForMoney(userMoney,riskArray,defHierarchyId);
			}));
		} catch (Exception e) {
			log.error("checkUserAbnormalForMoney error {}", e);
		}
	}

}
