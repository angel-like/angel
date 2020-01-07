package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaijiu.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.paijiu.BairenpaijiuGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaijiu.entity.GameRecordPaijiuEntity;


/**
 * 游戏记录-百人牌九
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-10 11:32:57
 */
public interface GameRecordPaijiuService extends IService<GameRecordPaijiuEntity> {
	
	/**
	 * 保存百人牌九游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(BairenpaijiuGameRecordParams bairenpaijiuGameRecordParams);

}

