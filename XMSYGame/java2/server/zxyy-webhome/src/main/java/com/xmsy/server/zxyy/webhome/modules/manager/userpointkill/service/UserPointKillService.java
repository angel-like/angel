package com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.entity.UserPointKillEntity;


/**
 * 点杀名单
 *
 * @author aye
 * @email xxxxx
 * @date 2019-11-22 11:27:03
 */
public interface UserPointKillService extends IService<UserPointKillEntity> {
	/**
	 * 通过每一局游戏记录   计算剩余多少解除点杀名单  跟  退出点杀名单 
	 * @param gameRecord
	 */
	void countUserPointKill(GameRecordEntity gameRecord);
}

