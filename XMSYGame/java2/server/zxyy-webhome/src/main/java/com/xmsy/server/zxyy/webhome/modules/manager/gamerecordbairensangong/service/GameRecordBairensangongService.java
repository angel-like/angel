package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairensangong.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.sangong.BairenSanGongGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairensangong.entity.GameRecordBairensangongEntity;


/**
 * 游戏记录-百人三公
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 16:58:30
 */
public interface GameRecordBairensangongService extends IService<GameRecordBairensangongEntity> {
	
	/**
	 * 保存百人三公游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(BairenSanGongGameRecordParams bairenSanGongGameRecordParams);

}

