package com.xmsy.server.zxyy.manager.modules.manager.sharerecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.share.param.ShareParam;
import com.xmsy.server.zxyy.manager.modules.manager.sharerecord.entity.ShareRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;


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

}

