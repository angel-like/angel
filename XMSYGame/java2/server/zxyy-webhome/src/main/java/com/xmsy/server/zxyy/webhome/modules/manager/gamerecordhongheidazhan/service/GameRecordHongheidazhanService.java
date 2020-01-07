package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.hongheidazhan.HongheidazhanGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.entity.GameRecordHongheidazhanEntity;


/**
 * 游戏记录-红黑大战
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-08-29 09:49:39
 */
public interface GameRecordHongheidazhanService extends IService<GameRecordHongheidazhanEntity> {

	/**
	 * 保存红黑大战游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(HongheidazhanGameRecordParams hongheidazhanGameRecordParams);
}

