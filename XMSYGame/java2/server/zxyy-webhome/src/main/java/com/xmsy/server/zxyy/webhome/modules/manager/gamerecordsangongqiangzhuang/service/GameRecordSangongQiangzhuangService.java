package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.sangong.qiangzhuang.SangongQiangzhuangGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.entity.GameRecordSangongQiangzhuangEntity;


/**
 * 游戏记录-抢庄三公
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 15:11:29
 */
public interface GameRecordSangongQiangzhuangService extends IService<GameRecordSangongQiangzhuangEntity> {
	
	/**
	 * 保存抢庄三公游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(SangongQiangzhuangGameRecordParams sangongQiangzhuangGameRecordParams);

}

