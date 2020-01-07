package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.RankingListService;
import com.xmsy.server.zxyy.schedule.server.UserRebateService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.SysConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 财富榜 定时任务
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class WealthScheduleTask {

	@Autowired
	private RankingListService rankingListService;

	@Autowired
	private UserRebateService userRebateService;

	/**
	 * 每天统计余额，生成昨日排行榜记录
	 */
	@Scheduled(cron = "0 45 5 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void WealthByDay() {
		JSONObject rankingList = null;
		try {
			rankingList = rankingListService.getRankingListById(SysConstant.getRANKINGLIST_WEALTH());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rankingList == null || rankingList.isEmpty() || !rankingList.getBoolean("enable")) {
			log.warn("【WealthByDay】统计的余额日榜:榜单id有误或者未设置为可用");
			return;
		}
		// // 昨日(日期格式)
		Date yesterday = DateUtils.getStartOfYesterday();
		log.info("【WealthByDay】统计的余额日榜 yesterday：{}", yesterday);
		// // 获取统计结果
		JSONArray rankList = userRebateService.statisticsWealthByDay();
		rankingListService.saveBatchForTaskToDay(rankList, SysConstant.getRANKINGLIST_WEALTH(), yesterday);
	}

	@Scheduled(cron = "0 0 1 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void WealthByWeek() {
		if (DateUtils.dayOfWeek(new Date()) != 1) {// 不是星期一
			log.warn("【WealthByWeek】统计的余额周榜:不是星期一");
			return;
		}
		JSONObject rankingList = null;
		try {
			rankingList = rankingListService.getRankingListById(SysConstant.getRANKINGLIST_WEALTH());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rankingList == null || rankingList.isEmpty() || !rankingList.getBoolean("enable")) {
			log.warn("【WealthByWeek】统计的余额周榜:榜单id有误或者未设置为可用");
			return;
		}
		Date lastWeekDate = DateUtils.addDateWeeks(new Date(), -1);
		int weekNum = DateUtils.getWeekOfYear(lastWeekDate);
		String[] weekRange = DateUtils.getWeekStartAndEnd(lastWeekDate);
		log.info("【WealthByWeek】统计的余额周榜: 开始时间 :{} - 结束时间: {}", weekRange[0], weekRange[1]);
		JSONArray rankingListWeekList = rankingListService
				.statisticsRankingListByDateRange(SysConstant.getRANKINGLIST_WEALTH(), weekRange[0], weekRange[1]);
		rankingListService.saveBatchForTaskToWeek(rankingListWeekList, SysConstant.getRANKINGLIST_WEALTH(), weekNum);
	}

}
