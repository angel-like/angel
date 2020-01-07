package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshui.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.shisanshui.ShiSanShuiGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshui.entity.GameRecordShisanshuiEntity;


/**
 * 游戏记录-十三水
 *
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
public interface GameRecordShisanshuiService extends IService<GameRecordShisanshuiEntity> {
	/**
	 * 保存十三水游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(ShiSanShuiGameRecordParams shiSanShuiGameRecordParams);
}

