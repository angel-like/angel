package com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.entity.UserBalanceRateEntity;


/**
 * 用户余额宝利率表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-17 15:56:57
 */
public interface UserBalanceRateService extends IService<UserBalanceRateEntity> {

	public PageUtils findUserRecordPageForTime(UserBalanceRateEntity user, PageParam pageParam);
}

