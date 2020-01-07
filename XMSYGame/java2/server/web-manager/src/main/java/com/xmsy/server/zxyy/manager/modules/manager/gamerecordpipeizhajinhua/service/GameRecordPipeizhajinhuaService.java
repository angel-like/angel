package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.pipeizhajinhua.PipeizhajinhuaGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.entity.GameRecordPipeizhajinhuaEntity;


/**
 * 游戏记录-匹配炸金花
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-03-29 09:33:49
 */
public interface GameRecordPipeizhajinhuaService extends IService<GameRecordPipeizhajinhuaEntity> {
	/**
	 * 保存匹配炸金花游戏记录
	 * @param gameRecordParams
	 */
	void appSavePipeizhajinhuaGameRecord(PipeizhajinhuaGameRecordParams gameRecordParams);

}

