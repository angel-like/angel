package com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.entity.UserProfitRecordEntity;


/**
 * 用户账户金额收益记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 17:48:37
 */
public interface UserProfitRecordService extends IService<UserProfitRecordEntity> {
	/**
	 * 通过用户id获取 该用户所有的  用户账户金额收益记录
	 * @param user
	 * @return
	 */
	List<UserProfitRecordEntity> getUserProfitRecordByUserId(UserEntity user);
	/**
	 * 通过用户 以及 日期  
	 * 		获取日期那天 的 用户账户金额收益记录
	 * @param user
	 * @param day
	 * @return
	 */
	UserProfitRecordEntity getUserProfitRecordByUserIdAndDate(UserEntity user,Date day);
}

