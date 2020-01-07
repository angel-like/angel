package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwoeightbar.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.twoeightbar.TwoEightBarGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwoeightbar.entity.GameRecordTwoeightbarEntity;


/**
 * 游戏记录-二八杠
 *
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
public interface GameRecordTwoeightbarService extends IService<GameRecordTwoeightbarEntity> {

	/**
	 * 保存二八杠游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(TwoEightBarGameRecordParams twoEightBarGameRecordParams);
}

