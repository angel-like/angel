package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.feiqinzoushou.FeiqinzoushouGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.entity.GameRecordFeiqinzoushouEntity;


/**
 * 游戏记录-飞禽走兽
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-06 10:49:17
 */
public interface GameRecordFeiqinzoushouService extends IService<GameRecordFeiqinzoushouEntity> {
	
	/**
	 * 保存飞禽走兽游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(FeiqinzoushouGameRecordParams feiqinzoushouGameRecordParams);

}

