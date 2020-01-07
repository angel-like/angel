package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.shisanshuifangka.ShiSanShuiFangKaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.entity.GameRecordShisanshuiFangkaEntity;


/**
 * 游戏记录-房卡十三水
 *
 * @author adu
 * @email xxxxx
 * @date 2019-03-28 16:17:54
 */
public interface GameRecordShisanshuiFangkaService extends IService<GameRecordShisanshuiFangkaEntity> {
	
	/**
	 * 保存房卡十三水游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(ShiSanShuiFangKaGameRecordParams shiSanShuiFangKaGameRecordParams);
}

