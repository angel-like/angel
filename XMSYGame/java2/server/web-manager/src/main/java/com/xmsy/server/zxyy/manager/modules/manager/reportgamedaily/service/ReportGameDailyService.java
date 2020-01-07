package com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.entity.ReportGameDailyEntity;


/**
 * 每日游戏投入产出报表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
public interface ReportGameDailyService extends IService<ReportGameDailyEntity> {
	/**
	 * 获取指定日期 天数的 游戏盈利：   玩家人数（playerNum）  游戏名称（gameName） 游戏ID(gameId)   游戏盈利（profit）
	 * @return
	 */
	List<List<Map<String, Object>>> sumPlayerProfit(@Param("startDate") String startDate, @Param("endDate") String endDate);
}

