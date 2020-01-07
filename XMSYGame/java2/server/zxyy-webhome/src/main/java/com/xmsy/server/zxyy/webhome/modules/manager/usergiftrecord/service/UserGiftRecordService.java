package com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.service;


import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;


/**
 * 用户道具发放记录
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-24 09:34:18
 */
public interface UserGiftRecordService extends IService<UserGiftRecordEntity> {
	
	int insertGetId(UserGiftRecordEntity entity);
	
	/**
	 * 通过userId和activity查询
	 * @return
	 */
	UserGiftRecordEntity queryGiftRecord(Long userId,Long activityId);
	
	void UserPropReceived(Long messageId,Long userId);

}

