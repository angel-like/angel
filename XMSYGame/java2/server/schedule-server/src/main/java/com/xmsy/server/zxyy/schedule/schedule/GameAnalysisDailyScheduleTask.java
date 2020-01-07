package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.xmsy.server.zxyy.schedule.server.GameRecordService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 每天统计每款游戏的下注，盈利等信息
 * 
 *
 */
@Slf4j
@Component
public class GameAnalysisDailyScheduleTask {
	@Autowired
	private GameRecordService gameRecordService;

	/**
	 * 每天凌晨4点统计每款游戏的报表，
	 */
	@Scheduled(cron = "0 0 4 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void GameRecordDailyByDay() {
		// 昨日(日期格式)
		Date yesterday = DateUtils.getStartOfYesterday();
		// 昨日凌晨（时间格式）
		String startOfYesterday = DateUtils.formatTime(DateUtils.getStartOfYesterday());
		// 昨日最后一秒（时间格式）
		String endOfYesterday = DateUtils.formatTime(DateUtils.getEndOfYesterday());
		DateUtils.formatTime(DateUtils.getEndOfYesterday());
		log.info("【GameAnalysisDailyScheduleTask】 统计每天游戏盈利情况时间段: 开始时间 :{} - 结束时间: {} yesterday：{}", startOfYesterday,
				endOfYesterday, yesterday);
		try {
			JSONArray dataList = gameRecordService.getGameAnalysisByDay(startOfYesterday, endOfYesterday, false);
			JSONArray robotDataList = gameRecordService.getGameAnalysisByDay(startOfYesterday, endOfYesterday, true);
			gameRecordService.batchInsertGameAnalysisDaily(dataList, yesterday, false);
			gameRecordService.batchInsertGameAnalysisDaily(robotDataList, yesterday, true);
		} catch (Exception e) {
			log.error("GameAnalysisDailyByDay error :{}", e);
			e.printStackTrace();
		}

	}

}
