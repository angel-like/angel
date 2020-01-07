package com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.service;


import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;


/**
 * 用户账户金额存取记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
public interface UserBalanceRecordService extends IService<UserBalanceRecordEntity> {
	public List<UserBalanceRecordEntity> getPageList(UserBalanceRecordEntity userBalanceRecordEntity, Pagination page);
}

