package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.twentyonespot.TwentyOneSpotGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.entity.GameRecordTwentyOneSpotEntity;


/**
 * 游戏记录-21点
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 17:05:40
 */
public interface GameRecordTwentyOneSpotService extends IService<GameRecordTwentyOneSpotEntity> {
	
	/**
	 * 保存21点游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(TwentyOneSpotGameRecordParams twentyOneSpotGameRecordParams);

}

