package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlqiangzhuang.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.niuniu.qiangzhuang.QiangzhuangNiuNiuGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlqiangzhuang.entity.GameRecordCattlQiangzhuangEntity;


/**
 * 游戏记录-抢庄牛牛
 *
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
public interface GameRecordCattlQiangzhuangService extends IService<GameRecordCattlQiangzhuangEntity> {
	/**
	 * 保存抢庄牛牛游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(QiangzhuangNiuNiuGameRecordParams qiangzhuangNiuNiuGameRecordParams);
}

