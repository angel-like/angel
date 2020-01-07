package com.xmsy.server.zxyy.webhome.modules.manager.uservip.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.entity.UserVipEntity;


/**
 * 用户vip等级表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 16:46:36
 */
public interface UserVipService extends IService<UserVipEntity> {
	/**
	 * 查询上一个vip
	 * @param sort
	 * @return
	 */
	UserVipEntity selectNextVip(Integer sort);

}

