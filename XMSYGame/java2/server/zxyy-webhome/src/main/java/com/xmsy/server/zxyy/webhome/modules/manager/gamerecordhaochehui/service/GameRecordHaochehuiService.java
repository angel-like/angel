package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.haochehui.HaochehuiGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.entity.GameRecordHaochehuiEntity;


/**
 * 游戏记录-豪车会
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-08-29 09:49:38
 */
public interface GameRecordHaochehuiService extends IService<GameRecordHaochehuiEntity> {
	
	/**
	 * 保存豪车会游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(HaochehuiGameRecordParams haochehuiGameRecordParams);

}

