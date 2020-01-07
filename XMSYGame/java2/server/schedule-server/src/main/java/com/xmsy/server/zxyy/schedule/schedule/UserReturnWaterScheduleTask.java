package com.xmsy.server.zxyy.schedule.schedule;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.GameRecordService;
import com.xmsy.server.zxyy.schedule.server.UserRebateService;
import com.xmsy.server.zxyy.schedule.server.UserService;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;
import com.xmsy.server.zxyy.schedule.utils.SysConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户返水 定时任务
 * 
 * @author adu
 *
 */
@Slf4j
@Component
public class UserReturnWaterScheduleTask {
	@Autowired
	private UserService userService;
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private UserRebateService userRebateService;

	/**
	 * 查询每天统计用户下注表，将会员返水更新到会员金币中
	 */
	@Scheduled(cron = "0 0 0/1 * * ? ")
	public void UserReturnWaterByDay() {
		try {
			JSONArray dataArray = gameRecordService.findUserReturnWaterByDay();
			if (dataArray == null || dataArray.size() == 0) {
				log.debug("UserReturnWaterByDay 查无记录");
				return;
			}
			JSONObject userRebate = userRebateService.getUserReBateByType(SysConstant.RewardType2);
			BigDecimal multiple = userRebate!=null&&!userRebate.isEmpty()?
					MathUtil.getBigDecimal(userRebate.get("codeMultiple")):BigDecimal.ZERO;
			for (int i = 0; i < dataArray.size(); i++) {
				updateUserCoin(dataArray.getJSONObject(i),multiple);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUserCoin(JSONObject userReturnWater,BigDecimal multiple ) {
		try {
			threadPoolTaskExecutor.execute(new Thread(() -> {
				log.debug("multiple :{},userReturnWater {}",multiple, userReturnWater);
				userService.updateUserCoinTransaction(userReturnWater,multiple);
			}));
		} catch (Exception e) {
			log.error("updateUserCoin error {}", e);
		}
	}

}
