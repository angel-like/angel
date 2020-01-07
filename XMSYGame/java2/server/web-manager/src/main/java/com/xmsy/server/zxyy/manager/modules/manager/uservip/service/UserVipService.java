package com.xmsy.server.zxyy.manager.modules.manager.uservip.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.entity.UserVipEntity;


/**
 * 用户vip等级表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 15:18:13
 */
public interface UserVipService extends IService<UserVipEntity> {
	List<Map<String, Object>> findUserVipForSelect();
	Map<Long, String> getVipMap();

}

