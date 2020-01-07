package com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.zhajinhua.BairenZhajinhuaGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.entity.GameRecordZhajinhuaEntity;


/**
 * 游戏记录-诈金花
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-10 11:13:28
 */
public interface GameRecordZhajinhuaService extends IService<GameRecordZhajinhuaEntity> {
	
	/**
	 * 保存百人诈金花游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(BairenZhajinhuaGameRecordParams bairenZhajinhuaGameRecordParams);

}

