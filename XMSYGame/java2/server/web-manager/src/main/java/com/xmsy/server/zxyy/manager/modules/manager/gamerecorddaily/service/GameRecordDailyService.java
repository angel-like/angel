package com.xmsy.server.zxyy.manager.modules.manager.gamerecorddaily.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddaily.entity.GameRecordDailyEntity;


/**
 * 每日会员游戏总下注记录
 *
 * @author adu
 * @email xxxxx
 * @date 2019-04-11 20:30:48
 */
public interface GameRecordDailyService extends IService<GameRecordDailyEntity> {
	
	/**
	 * 获取会员抽水数值总数
	 * @param gamerecorddaily
	 * @return
	 */
	PageUtils selectuserPumpTotal(GameRecordDailyEntity gamerecorddaily, PageParam pageParam);
	
	/**
	 * 获取会员抽水数值总数
	 * @param gamerecorddaily
	 * @return
	 */
	List<GameRecordDailyEntity> selectuserPumpList(GameRecordDailyEntity gamerecorddaily);
	

}

