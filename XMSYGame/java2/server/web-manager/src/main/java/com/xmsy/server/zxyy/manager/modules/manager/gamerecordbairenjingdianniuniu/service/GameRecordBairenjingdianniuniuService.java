package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenjingdianniuniu.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.niuniu.bairenjingdain.BairenjingdianNiuNiuGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenjingdianniuniu.entity.GameRecordBairenjingdianniuniuEntity;


/**
 * 游戏记录-百人经典牛牛
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-29 17:19:22
 */
public interface GameRecordBairenjingdianniuniuService extends IService<GameRecordBairenjingdianniuniuEntity> {
	/**
	 * 保存百人經典牛牛游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(BairenjingdianNiuNiuGameRecordParams bairenJingdianNiuNiuGameRecordParams);

}

