package com.xmsy.server.zxyy.schedule.schedule;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.UserBalanceService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户余额利息计算
 * 
 * @author adu
 *
 */
@Slf4j
@Component
public class UserBalanceScheduleTask {
	@Autowired
	private UserBalanceService userBalanceService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	/**
	 * 计算用户余额宝收益
	 */
	@Scheduled(cron = "0 0 0/1 * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60)
	public void userBalanceYesterdayProfit() {
		Date yesterday = DateUtils.getStartOfYesterday();
//		JSONObject balanceRate = userBalanceService.getBalanceRate(yesterday);
//		if(balanceRate==null || balanceRate.get("rate") ==null) {
//			log.error("余额宝利率未设置");
//			return;
//		}
//		BigDecimal rate =MathUtil.getBigDecimal(balanceRate.get("rate"));

		userBalanceService.saveRateYesterday(yesterday);
		JSONArray userBalaceList = userBalanceService.getUserBalanceList();
		if(userBalaceList ==null || userBalaceList.isEmpty()) {
			log.info("用户余额宝今日已经处理完成");
			return;
		}
		try {	
			
			for(int i=0;i<userBalaceList.size();i++) {
				BigDecimal rate =MathUtil.getBigDecimal(userBalaceList.getJSONObject(i).get("rate"));
				updateUserYesterdayProfit(userBalaceList.getJSONObject(i),
						rate,
						yesterday);
//				userBalanceService.saveUserYesterdayProfi (yesterday, rate, userBalaceList.getJSONObject(i));

			}
		} catch (Exception e) {
			log.error("[userBalanceYesterdayProfit] exception", e);
		}
	}
	public void updateUserYesterdayProfit(JSONObject user,BigDecimal rate, Date yesterday) {
		try {
			threadPoolTaskExecutor.execute(new Thread(() -> {
				log.info("rate :{},userBanlance {}",rate, user);
				userBalanceService.saveUserYesterdayProfi (yesterday, rate, user);
			}));
		} catch (Exception e) {
			log.error("updateUserYesterdayProfit error {}", e);
		}
	}
}
