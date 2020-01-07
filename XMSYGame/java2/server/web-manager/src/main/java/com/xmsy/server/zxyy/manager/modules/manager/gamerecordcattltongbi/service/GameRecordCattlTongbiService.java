package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.niuniu.tongbi.TongBiNiuNiuGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.entity.GameRecordCattlTongbiEntity;


/**
 * 游戏记录-通比牛牛
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-28 15:32:44
 */
public interface GameRecordCattlTongbiService extends IService<GameRecordCattlTongbiEntity> {
	/**
	 * 保存通比牛牛游戏记录
	 * @param gameRecordParams
	 */
	void appSaveTongBiNiuNiuGameRecord(TongBiNiuNiuGameRecordParams gameRecordParams);

}

