package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.GamePoolService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏奖池的日统计 定时任务
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class GamePoolsScheduleTask {
	@Autowired
	private GamePoolService gamePoolService;

	/**
	 * 游戏奖池的日统计
	 */
	@Scheduled(cron = "0 0 0/3 * * ? ")
	public void gamePoolStatisticsByDay() {
		JSONArray poolGameList = null;
		try {
			poolGameList = gamePoolService.findGamePoolList();
		} catch (Exception e) {
			log.error("find poolGameList errWor:{}", e);
			e.printStackTrace();
		}
		if (poolGameList == null || poolGameList.size() == 0) {
			log.warn("【gamePoolStatisticsByDay】游戏奖池的日统计:游戏奖池没有设置或者状态没有设置为启动");
			return;
		}
		// 昨日(日期格式)
		Date yesterday = DateUtils.getStartOfYesterday();
		// 昨日凌晨（时间格式）
		String startOfYesterday = DateUtils.formatTime(DateUtils.getStartOfYesterday());
		// 昨日最后一秒（时间格式）
		String endOfYesterday = DateUtils.formatTime(DateUtils.getEndOfYesterday());
		log.debug("【gamePoolStatisticsByDay】游戏奖池的日统计时间段: 开始时间 :{} - 结束时间: {} yesterday：{}", startOfYesterday,
				endOfYesterday, yesterday);
		for (int i = 0; i < poolGameList.size(); i++) {
			JSONObject poolGame = poolGameList.getJSONObject(i);
			try {
				gamePoolService.updatePoolByGameIdForStatistics(poolGame, startOfYesterday, endOfYesterday);
			} catch (Exception e) {
				log.info("poolGame :{}", poolGame);
				log.error("updatePoolByGameIdForStatistics error:{}", e);
				e.printStackTrace();
			}
		}

	}

}
