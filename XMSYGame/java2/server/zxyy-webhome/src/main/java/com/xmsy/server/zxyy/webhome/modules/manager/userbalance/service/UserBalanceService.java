package com.xmsy.server.zxyy.webhome.modules.manager.userbalance.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchOutParamNew;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParam;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParamNew;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.entity.UserBalanceEntity;


/**
 * 用户余额宝金额表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
public interface UserBalanceService extends IService<UserBalanceEntity> {
	/**
	 * 修改(不存在就增加)用户余额宝金额表里的未收益金额
	 * @param user
	 * @param param
	 * @return
	 */
	boolean updateUserBalance(UserEntity user,SwitchParam param);
	/**
	 * 通过用户获取用户余额宝金额表实体
	 * @param user
	 */
	public UserBalanceEntity getUserBalance(UserEntity user);
	
	//====================================新版本余额宝功能================================
	/**
	 * 修改(不存在就增加)用户余额宝金额表里的未收益金额
	 * @param user
	 * @param param
	 * @return
	 */
	boolean updateUserBalanceNew(UserEntity user,SwitchParamNew param);
	/**
	 * 通过用户id+理财产品id  获取用户余额宝金额表实体
	 * @param user
	 */
	public UserBalanceEntity getUserBalanceNew(UserEntity user,SwitchOutParamNew param);
}

