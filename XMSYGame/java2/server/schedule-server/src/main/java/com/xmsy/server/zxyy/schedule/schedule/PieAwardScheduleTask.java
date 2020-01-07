package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.PieAwardService;
import com.xmsy.server.zxyy.schedule.server.RankingListService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.SysConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 派奖榜 定时任务
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class PieAwardScheduleTask {

	@Autowired
	private RankingListService rankingListService;

	@Autowired
	private PieAwardService pieAwardService;

	/**
	 * 每天统计派奖，生成昨日排行榜记录
	 */
	@Scheduled(cron = "0 15 5 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void pieAwardByDay() {
		JSONObject rankingList = null;
		try {
			rankingList = rankingListService.getRankingListById(SysConstant.getRANKINGLIST_PIWAWARD());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rankingList == null || rankingList.isEmpty() || !rankingList.getBoolean("enable")) {
			log.warn("【pieAwardByDay】统计的派奖日榜:榜单id有误或者未设置为可用");
			return;
		}
		// // 昨日(日期格式)
		Date yesterday = DateUtils.getStartOfYesterday();
		// // 昨日凌晨（时间格式）
		String startOfYesterday = DateUtils.formatTime(DateUtils.getStartOfYesterday());
		// // 昨日最后一秒（时间格式）
		String endOfYesterday = DateUtils.formatTime(DateUtils.getEndOfYesterday());
		log.info("【pieAwardByDay】统计的派奖日榜时间段: 开始时间 :{} - 结束时间: {} yesterday：{}", startOfYesterday, endOfYesterday,
				yesterday);
		// // 获取统计结果
		JSONArray rankList = pieAwardService.findPieAwardByDay(startOfYesterday, endOfYesterday);
		rankingListService.saveBatchForTaskToDay(rankList, SysConstant.getRANKINGLIST_PIWAWARD(), yesterday);

	}

	@Scheduled(cron = "0 0 1 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void pieAwardByWeek() {
		if (DateUtils.dayOfWeek(new Date()) != 1) {// 不是星期一
			log.warn("【pieAwardByWeek】统计的派奖周榜:不是星期一");
			return;
		}
		JSONObject rankingList = null;
		try {
			rankingList = rankingListService.getRankingListById(SysConstant.getRANKINGLIST_PIWAWARD());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rankingList == null || rankingList.isEmpty() || !rankingList.getBoolean("enable")) {
			log.warn("【pieAwardByWeek】统计的派奖周榜:榜单id有误或者未设置为可用");
			return;
		}
		Date lastWeekDate = DateUtils.addDateWeeks(new Date(), -1);
		int weekNum = DateUtils.getWeekOfYear(lastWeekDate);
		String[] weekRange = DateUtils.getWeekStartAndEnd(lastWeekDate);
		log.info("【pieAwardByWeek】统计的派奖周榜: 开始时间 :{} - 结束时间: {}", weekRange[0], weekRange[1]);
		JSONArray rankingListWeekList = rankingListService
				.statisticsRankingListByDateRange(SysConstant.getRANKINGLIST_PIWAWARD(), weekRange[0], weekRange[1]);
		rankingListService.saveBatchForTaskToWeek(rankingListWeekList, SysConstant.getRANKINGLIST_PIWAWARD(), weekNum);
	}

}
