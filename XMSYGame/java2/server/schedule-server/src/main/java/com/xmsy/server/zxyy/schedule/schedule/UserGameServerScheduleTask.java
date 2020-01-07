package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xmsy.server.zxyy.schedule.config.SysConfig;
import com.xmsy.server.zxyy.schedule.server.UserService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户是否在游戏中扫描离线用户
 * 
 * @author aleng
 *
 */
@Slf4j
@Component
public class UserGameServerScheduleTask {
	@Autowired
	private UserService userService;
	@Autowired
	private SysConfig sysConfig;

	/**
	 * 用户是否在游戏中,扫描离线用户
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void userGameServerCheck() {
		try {
			// 获取前一个小时的时间
			String checkTime = DateUtils
					.formatTime(DateUtils.addDateHours(new Date(), sysConfig.getUserGameServerCheckHour()));
			userService.updateUserGameServer(checkTime);
		} catch (Exception e) {
			log.error("[userGameServerCheck] exception", e);
		}
	}
}
