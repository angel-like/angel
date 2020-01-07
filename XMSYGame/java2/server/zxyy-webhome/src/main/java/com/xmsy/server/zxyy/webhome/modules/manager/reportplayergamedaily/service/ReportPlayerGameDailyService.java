package com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.entity.ReportPlayerGameDailyEntity;


/**
 * 每日玩家游戏投入产出报表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
public interface ReportPlayerGameDailyService extends IService<ReportPlayerGameDailyEntity> {
	
	/**
	 * 查询同个用户同个场次的总投入、总产出、总输赢
	 * @return
	 */
	Map<String, Object> querySum(Long userId, Long gradeId,Long gameId);
	
	

}

