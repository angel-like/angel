package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbaijia.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.baijia.BaijiaLeGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbaijia.entity.GameRecordBaijiaEntity;

/**
 * 游戏记录-百家乐
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-09 15:37:22
 */
public interface GameRecordBaijiaService extends IService<GameRecordBaijiaEntity> {

	/**
	 * 保存百家乐游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(BaijiaLeGameRecordParams baijiaLeGameRecordParams);

}
