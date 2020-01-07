package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlordsfangka.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.fightlandlordsfangka.FightlandlordsFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlordsfangka.entity.GameRecordFightlandlordsFangkaEntity;


/**
 * 游戏记录-房卡斗地主
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-07-31 10:57:08
 */
public interface GameRecordFightlandlordsFangkaService extends IService<GameRecordFightlandlordsFangkaEntity> {
	
	/**
	 * 保存房卡斗地主游戏记录
	 * @param gameRecordParams
	 */
	void appSaveFightlandlordsFangkaGameRecord(FightlandlordsFangkaGameRecordParams gameRecordParams);

}

