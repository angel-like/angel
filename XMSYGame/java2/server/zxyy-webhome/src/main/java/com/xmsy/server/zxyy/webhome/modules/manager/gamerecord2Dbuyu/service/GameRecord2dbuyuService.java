package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.buyu.Buyu2DGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.entity.GameRecord2dbuyuEntity;


/**
 * 游戏记录-2D捕鱼
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-30 11:21:16
 */
public interface GameRecord2dbuyuService extends IService<GameRecord2dbuyuEntity> {
	/**
	 * 保存匹配炸金花游戏记录
	 * @param gameRecordParams
	 */
	void appSave2dbuyuGameRecord(Buyu2DGameRecordParams gameRecordParams);

}

