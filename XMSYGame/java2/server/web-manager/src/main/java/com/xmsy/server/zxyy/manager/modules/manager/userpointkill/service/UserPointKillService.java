package com.xmsy.server.zxyy.manager.modules.manager.userpointkill.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.entity.UserPointKillEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.param.UserPointKillManagerParam;


/**
 * 点杀名单
 *
 * @author aye
 * @email xxxxx
 * @date 2019-11-22 11:27:03
 */
public interface UserPointKillService extends IService<UserPointKillEntity> {
	/**
	 * 查找会员点杀管理的  其他相关数据
	 * @param userMapList
	 */
	void findOtherData(List<Map<String, Object>> userMapList,UserEntity user);
	/**
	 * 通过会员id获取总资产
	 * @param userId
	 * @return
	 */
	BigDecimal gainTotalCoin(Long userId);
	/**
	 * 点杀监管分页查询--游戏记录为主
	 */
	List<Map<String, Object>> findUserPointKillManagerPage(UserPointKillManagerParam userPointKillManagerParam, Page<Map<String, Object>> page);
}

