package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.RankingListService;
import com.xmsy.server.zxyy.schedule.server.RechargeOrderService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.SysConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 充值榜 定时任务
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class RechargeScheduleTask {

	@Autowired
	private RankingListService rankingListService;

	@Autowired
	private RechargeOrderService rechargeOrderService;

	/**
	 * 每天统计充值，生成昨日排行榜记录
	 */
	@Scheduled(cron = "0 30 5 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void RechargeByDay() {
		JSONObject rankingList = null;
		try {
			rankingList = rankingListService.getRankingListById(SysConstant.getRANKINGLIST_RECHARGE());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rankingList == null || rankingList.isEmpty() || !rankingList.getBoolean("enable")) {
			log.warn("【RechargeByDay】统计的充值日榜:榜单id有误或者未设置为可用");
			return;
		}
		// // 昨日(日期格式)
		Date yesterday = DateUtils.getStartOfYesterday();
		// // 昨日凌晨（时间格式）
		String startOfYesterday = DateUtils.formatTime(DateUtils.getStartOfYesterday());
		// // 昨日最后一秒（时间格式）
		String endOfYesterday = DateUtils.formatTime(DateUtils.getEndOfYesterday());
		log.info("【RechargeByDay】统计的充值日榜时间段: 开始时间 :{} - 结束时间: {} yesterday：{}", startOfYesterday, endOfYesterday,
				yesterday);
		// // 获取统计结果
		JSONArray rankList = rechargeOrderService.statisticsRechargeByDay(startOfYesterday, endOfYesterday);
		rankingListService.saveBatchForTaskToDay(rankList, SysConstant.getRANKINGLIST_RECHARGE(), yesterday);

	}

	@Scheduled(cron = "0 0 1 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void RechargeByWeek() {
		if (DateUtils.dayOfWeek(new Date()) != 1) {// 不是星期一
			log.warn("【RechargeByWeek】统计的充值周榜:不是星期一");
			return;
		}
		JSONObject rankingList = null;
		try {
			rankingList = rankingListService.getRankingListById(SysConstant.getRANKINGLIST_RECHARGE());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rankingList == null || rankingList.isEmpty() || !rankingList.getBoolean("enable")) {
			log.warn("【RechargeByWeek】统计的充值周榜:榜单id有误或者未设置为可用");
			return;
		}
		Date lastWeekDate = DateUtils.addDateWeeks(new Date(), -1);
		int weekNum = DateUtils.getWeekOfYear(lastWeekDate);
		String[] weekRange = DateUtils.getWeekStartAndEnd(lastWeekDate);
		log.info("【RechargeByWeek】统计的充值周榜: 开始时间 :{} - 结束时间: {}", weekRange[0], weekRange[1]);
		JSONArray rankingListWeekList = rankingListService
				.statisticsRankingListByDateRange(SysConstant.getRANKINGLIST_RECHARGE(), weekRange[0], weekRange[1]);
		rankingListService.saveBatchForTaskToWeek(rankingListWeekList, SysConstant.getRANKINGLIST_RECHARGE(), weekNum);
	}

}
