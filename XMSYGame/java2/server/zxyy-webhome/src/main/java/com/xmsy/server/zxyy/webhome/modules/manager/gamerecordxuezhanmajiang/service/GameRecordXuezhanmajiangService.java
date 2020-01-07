package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.xuezhanmajiang.XuezhanmajiangGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.entity.GameRecordXuezhanmajiangEntity;


/**
 * 游戏记录-血战麻将
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-11-29 10:38:57
 */
public interface GameRecordXuezhanmajiangService extends IService<GameRecordXuezhanmajiangEntity> {
	
	/**
	 * 保存血战麻将游戏记录
	 * @param gameRecordParams
	 */
	void appSaveXuezhanmajianGameRecord(XuezhanmajiangGameRecordParams gameRecordParams);

}

