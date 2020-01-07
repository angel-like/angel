package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaodekuai.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.paodekuai.PaodekuaiGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaodekuai.entity.GameRecordPaodekuaiEntity;


/**
 * 游戏记录-跑得快
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 11:35:08
 */
public interface GameRecordPaodekuaiService extends IService<GameRecordPaodekuaiEntity> {
	
	/**
	 * 保存跑得快游戏记录
	 * @param gameRecordParams
	 */
	void appSavePaodekuaiGameRecord(PaodekuaiGameRecordParams gameRecordParams);

}

