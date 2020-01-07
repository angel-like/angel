package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaodekuaifangka.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.paodekuai.fangka.PaodekuaiFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaodekuaifangka.entity.GameRecordPaodekuaiFangkaEntity;


/**
 * 游戏记录-房卡跑得快
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-06 10:49:17
 */
public interface GameRecordPaodekuaiFangkaService extends IService<GameRecordPaodekuaiFangkaEntity> {
	
	/**
	 * 保存房卡跑得快游戏记录
	 * @param gameRecordParams
	 */
	void appSavePaodekuaiGameRecord(PaodekuaiFangkaGameRecordParams gameRecordParams);

}

