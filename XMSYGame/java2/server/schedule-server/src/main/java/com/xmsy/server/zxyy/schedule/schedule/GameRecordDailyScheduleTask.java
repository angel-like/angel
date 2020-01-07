package com.xmsy.server.zxyy.schedule.schedule;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.GameRecordService;
import com.xmsy.server.zxyy.schedule.server.UserRebateService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.SysConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 每天统计用户下注 定时任务
 * 
 * @author adu
 *
 */
@Slf4j
@Component
public class GameRecordDailyScheduleTask {
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private UserRebateService userRebateService;

	/**
	 * 每天统计用户下注
	 */
	@Scheduled(cron = "0 0 5 * * ? ")
	// @Scheduled(fixedRate = 60000 * 50)
	public void GameRecordDailyByDay() {
		// 昨日(日期格式)
		Date yesterday = DateUtils.getStartOfYesterday();
		// 昨日凌晨（时间格式）
		String startOfYesterday = DateUtils.formatTime(DateUtils.getStartOfYesterday());
		// 昨日最后一秒（时间格式）
		String endOfYesterday = DateUtils.formatTime(DateUtils.getEndOfYesterday());
		log.debug("【GameRecordDailyByDay】统计的会员下注时间段: 开始时间 :{} - 结束时间: {} yesterday：{}", startOfYesterday, endOfYesterday,
				yesterday);
		try {
			BigDecimal userWaterRate = BigDecimal.ZERO;
			JSONObject userRebate = userRebateService.getUserReBateByType(SysConstant.RewardType2);
			if (userRebate != null && userRebate.getBigDecimal("waterRate") != null
					&& userRebate.getBigDecimal("waterRate").compareTo(BigDecimal.ZERO) > 0
					&& userRebate.getBigDecimal("waterRate").compareTo(BigDecimal.ONE) < 0) {
				userWaterRate = userRebate.getBigDecimal("waterRate");
			}
			JSONArray dataList = gameRecordService.getUserGameRecordByDay(startOfYesterday, endOfYesterday);
			gameRecordService.batchInsertGameRecordDaily(dataList, yesterday, userWaterRate);
		} catch (Exception e) {
			log.error("GameRecordDailyByDay error :{}", e);
			e.printStackTrace();
		}

	}

}
