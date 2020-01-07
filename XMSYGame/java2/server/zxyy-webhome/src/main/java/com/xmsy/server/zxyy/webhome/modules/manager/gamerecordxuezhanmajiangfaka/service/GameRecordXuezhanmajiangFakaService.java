package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.xuezhanmajiang.fangka.XuezhanmajiangFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.entity.GameRecordXuezhanmajiangFakaEntity;


/**
 * 游戏记录-房卡血战麻将
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-06 14:50:44
 */
public interface GameRecordXuezhanmajiangFakaService extends IService<GameRecordXuezhanmajiangFakaEntity> {
	
	/**
	 * 保存房卡血战麻将游戏记录
	 * @param gameRecordParams
	 */
	void appSaveXuezhanmajianFangkaGameRecord(XuezhanmajiangFangkaGameRecordParams gameRecordParams);

}

