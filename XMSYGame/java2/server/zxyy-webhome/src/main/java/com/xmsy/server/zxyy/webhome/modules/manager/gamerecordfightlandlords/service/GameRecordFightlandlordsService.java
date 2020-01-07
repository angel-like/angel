package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlords.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.fightlandlords.FightLandlordsGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlords.entity.GameRecordFightlandlordsEntity;


/**
 * 游戏记录-斗地主
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-21 15:08:39
 */
public interface GameRecordFightlandlordsService extends IService<GameRecordFightlandlordsEntity> {
	/**
	 * 保存斗地主app游戏记录
	 * @param gameRecordParams
	 */
	public void appFightLandlordsSaveGameRecord(FightLandlordsGameRecordParams gameRecordParams);
	
	/**
	 * 获取同一局数的玩家信息
	 * @param gameRoundNo
	 * @return
	 */
	List<GameRecordFightlandlordsEntity> getGameList(@Param("gameRoundNo") String gameRoundNo);
	
}