package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordzhajinhuafangka.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.zhajinhuafangka.ZhajinhuaFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordzhajinhuafangka.entity.GameRecordZhajinhuaFangkaEntity;


/**
 * 游戏记录-房卡炸金花
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-07-31 10:57:08
 */
public interface GameRecordZhajinhuaFangkaService extends IService<GameRecordZhajinhuaFangkaEntity> {
	/**
	 * 保存房卡炸金花游戏记录
	 * @param gameRecordParams
	 */
	void appSaveZhajinhuaFangkaGameRecord(ZhajinhuaFangkaGameRecordParams gameRecordParams);

}

