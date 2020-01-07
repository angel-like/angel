package com.xmsy.server.zxyy.robot.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.robot.modules.manager.robot.service.RobotService;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.service.RobotProfitRecordService;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecordresult.entity.RobotProfitRecordResultEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecordresult.service.RobotProfitRecordResultService;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务
 * 
 * @author Administrator
 *
 */
@Slf4j
@Transactional
@Component
public class ScheduleTask {
	@Autowired
	private RobotService robotService;
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private RobotProfitRecordResultService robotProfitRecordResultService;
	@Autowired
	private RobotProfitRecordService robotProfitRecordService;

	/**
	 * 每5小时执行一次 回收未归还机器人
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
	public void gameRebateStatisticsByDay() {
		log.info("[gameRebateStatisticsByDay] 定时回收机器人开始 {}", "***********/" + DateUtil.now() + "/***********");
		System.out.println("回收机器人，定时" + DateUtil.now());
		robotService.taskRecyclingRobot();
	}

	/**
	 * 每天凌晨4点执行一次 删除多余游戏记录
	 */
	@Scheduled(cron = "0 0 4 * * ?")
	public void deleteGameRecord() {
		log.info("[deleteGameRecord//sumYesterdayProfit] 定时统计机器人盈利开始 {}",
				"***********/" + DateUtil.now() + "/***********");
		Boolean result = false;
		// 先统计昨日盈利金币
		try {
			result = robotProfitRecordService.sumYesterdayProfit();
		} catch (Exception e) {
			log.info("[sumYesterdayProfit] 是否统计成功 {}", result);
			if (!result) {
				robotProfitRecordResultService.insert(new RobotProfitRecordResultEntity().setResultEnable(result));
			}
			throw e;
		}
		log.info("[deleteGameRecord//deleteGameRecord] 定时清除机器人游戏记录开始 {}",
				"***********/" + DateUtil.now() + "/***********");
		//获取昨天开始时间
//		String date=DateUtils.formatTime(DateUtils.getStartOfYesterday());
		gameRecordService.deleteGameRecord(1);
	}

}
