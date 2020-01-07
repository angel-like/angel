package com.xmsy.server.zxyy.manager.modules.manager.gamerecordlonghu.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.longhu.LongHuGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordlonghu.entity.GameRecordLonghuEntity;

/**
 * 游戏记录-龙虎斗
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-09 15:37:22
 */
public interface GameRecordLonghuService extends IService<GameRecordLonghuEntity> {

	/**
	 * 保存龙虎游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(LongHuGameRecordParams longHuGameRecordParams);

}
