package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.cattlfangka.CattlFangkaGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.entity.GameRecordCattlFangkaEntity;


/**
 * 游戏记录-房卡牛牛
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-03-29 14:11:25
 */
public interface GameRecordCattlFangkaService extends IService<GameRecordCattlFangkaEntity> {
	/**
	 * 保存房卡牛牛游戏记录
	 * @param gameRecordParams
	 */
	void appSaveCattlFangkaGameRecord(CattlFangkaGameRecordParams gameRecordParams);

}

