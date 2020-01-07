package com.xmsy.server.zxyy.schedule.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xmsy.server.zxyy.schedule.server.ReportDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * 每日平台数据报表
 * 定时任务
 * 
 * @author adu
 *
 */
@Slf4j
@Component
public class ReportDataScheduleTask {
	
	@Autowired
	private ReportDataService reportDataService;
	
	/**
	 * 每小时统计，生成每日平台数据报表记录
	 */
	@Scheduled(cron = "0 0 0/1 * * ? ")
//	@Scheduled(fixedRate = 60000 * 5)
	public void WealthByDay() {
		try {
			reportDataService.statisticsDataReport();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	

}
