package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.ReportPlayerGameDailyService;
import com.xmsy.server.zxyy.schedule.server.ScheduleService;
import com.xmsy.server.zxyy.schedule.utils.Constant;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 每日玩家游戏投入产出报表
 * 定时任务
 * 
 * @author adu
 *
 */
@Slf4j
@Component
public class ReportPlayerGameDailyScheduleTask {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private ReportPlayerGameDailyService reportPlayerGameDailyService;

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	/**
	 * 每小时统计，生成每日平台数据报表记录
	 */
	@Scheduled(cron = "0 0 0/1 * * ? ")
//	@Scheduled(fixedRate = 60000 * 5)
	public void WealthByDay() {
		JSONObject schedule = scheduleService.getScheduleByName(Constant.REPORT_PLAYER_GAME_DAILY_SCHEDULE);
		Date lastUpdateTime = null;
		if(schedule!=null && schedule.getDate("updateTime")!=null) {
			lastUpdateTime=schedule.getDate("updateTime");
		}
		Date nowDate = new Date();
		JSONArray dataList = new JSONArray();
		if(lastUpdateTime==null) {
			lastUpdateTime=DateUtils.addDayZeroPoint(nowDate,0);
			JSONArray dataList1 = reportPlayerGameDailyService.findPlayerGameInvestmentAndOutputByDate(DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(nowDate));
			if(dataList1!=null && !dataList1.isEmpty()) {
				dataList.addAll(dataList1);
			}
		}else {
			int day = DateUtils.differenceDay(lastUpdateTime, nowDate);
			if(day==0) {
				lastUpdateTime=DateUtils.addDayZeroPoint(nowDate,0);
				JSONArray dataList2 = reportPlayerGameDailyService.findPlayerGameInvestmentAndOutputByDate(DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(nowDate));
				if(dataList2!=null && !dataList2.isEmpty()) {
					dataList.addAll(dataList2);
				}
			}else if(day==1 && DateUtils.isZero(nowDate)){
				lastUpdateTime=DateUtils.addDayZeroPoint(nowDate,0);
				JSONArray dataList3 = reportPlayerGameDailyService.findPlayerGameInvestmentAndOutputByDate(DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(nowDate));
				if(dataList3!=null && !dataList3.isEmpty()) {
					dataList.addAll(dataList3);
				}
			}else {
				 Date endDate=null;
				 JSONArray dataList4 =null;
				for(int i=0;i<=day;i++) {
					if(i==day) {
						dataList4 = reportPlayerGameDailyService.findPlayerGameInvestmentAndOutputByDate(DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(nowDate));
					}else {
						if(i==0) {
							lastUpdateTime=DateUtils.addDayZeroPoint(lastUpdateTime,0);
						}
						endDate=DateUtils.addDayZeroPoint(lastUpdateTime, 1);
						dataList4 = reportPlayerGameDailyService.findPlayerGameInvestmentAndOutputByDate(DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(endDate));
						lastUpdateTime=endDate;
					}
					if(dataList4!=null && !dataList4.isEmpty()) {
						dataList.addAll(dataList4);
					}
				}
			}
		}
		if(dataList!=null && !dataList.isEmpty()) {
			for(int i=0;i<dataList.size();i++) {
				saveDataTransaction(dataList.getJSONObject(i));
			}
		}
		scheduleService.updateLastTime(nowDate, Constant.REPORT_PLAYER_GAME_DAILY_SCHEDULE);
	}
	
	public void saveDataTransaction(JSONObject playerGame) {
		try {
			threadPoolTaskExecutor.execute(new Thread(() -> {
				reportPlayerGameDailyService.saveDataTransaction(playerGame);
			}));
		} catch (Exception e) {
			log.error("checkUserAbnormal error {}", e);
		}
	}
	

}
