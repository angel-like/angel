package com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddezhoupuke.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.dezhoupuke.DezhoupukeGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddezhoupuke.entity.GameRecordDezhoupukeEntity;


/**
 * 游戏记录-德州扑克
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-11-22 17:08:35
 */
public interface GameRecordDezhoupukeService extends IService<GameRecordDezhoupukeEntity> {
	
	/**
	 * 保存德州扑克游戏记录
	 * @param gameRecordParams
	 */
	void appSaveDezhoupukeGameRecord(DezhoupukeGameRecordParams gameRecordParams);

}

