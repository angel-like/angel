package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenniuniu.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.niuniu.bairen.BairenNiuNiuGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenniuniu.entity.GameRecordBairenniuniuEntity;

/**
 * 游戏记录-百人牛牛
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-28 16:28:26
 */
public interface GameRecordBairenniuniuService extends IService<GameRecordBairenniuniuEntity> {

	/**
	 * 保存百人牛牛游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(BairenNiuNiuGameRecordParams bairenNiuNiuGameRecordParams);

}
