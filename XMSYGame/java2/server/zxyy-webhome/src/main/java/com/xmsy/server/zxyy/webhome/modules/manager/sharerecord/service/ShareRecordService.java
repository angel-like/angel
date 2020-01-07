package com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.share.param.ShareParam;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.entity.ShareRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;


/**
 * 分享记录
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-22 12:14:28
 */
public interface ShareRecordService extends IService<ShareRecordEntity> {

	/**
	 * 分享app返金币
	 * @param param
	 */
	void shareAppGetCoin(ShareParam param,UserEntity user);
	/**
	 * 福缘任务
	 * 通过会员分享的次数 +已领取次数   =》 获取是否完成（通过已领取次数*条件判断），完成次数
	 * @param user
	 * @param playerTask
	 * @param cycle
	 * @return
	 */
	PlayerTasksEntity otherTask(UserEntity user, PlayerTasksEntity playerTask, int cycle);
}

