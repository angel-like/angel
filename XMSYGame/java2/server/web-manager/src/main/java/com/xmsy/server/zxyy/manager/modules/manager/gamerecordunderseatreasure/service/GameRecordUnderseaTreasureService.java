package com.xmsy.server.zxyy.manager.modules.manager.gamerecordunderseatreasure.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.laba.UnderseaTreasureGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordunderseatreasure.entity.GameRecordUnderseaTreasureEntity;


/**
 * 游戏记录-海底宝藏
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-19 11:41:54
 */
public interface GameRecordUnderseaTreasureService extends IService<GameRecordUnderseaTreasureEntity> {
	
	/**
	 * 保存海底宝藏游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(UnderseaTreasureGameRecordParams underseaTreasureGameRecordParams);

}

