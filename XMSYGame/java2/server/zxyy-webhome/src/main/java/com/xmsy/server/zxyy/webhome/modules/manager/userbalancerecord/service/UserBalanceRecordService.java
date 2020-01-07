package com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchOutParamNew;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParam;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParamNew;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;


/**
 * 用户账户金额存取记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
public interface UserBalanceRecordService extends IService<UserBalanceRecordEntity> {
	/**
	 * 根据会员用户转入的金额数量
	 * 		插入用户账户金额存取记录数据
	 * @param user
	 * @param param
	 */
	boolean insertIntoUserBalanceRecord(UserEntity user, SwitchParam param);
	/**
	 * 通过用户id查询 用户账户金额存取记录表
	 * 		条件是  1. 类型：存入
	 * 			 2.是否生效：未生效的  
	 * 			 3.money>takeMoney
	 * @param userId
	 */
	List<UserBalanceRecordEntity> getListUserBalanceRecord(Long userId);
	/**
	 * 通过用户id查询   所有的用户账户金额存取记录表
	 * 		
	 * @param userId
	 */
	List<UserBalanceRecordEntity> getAllListUserBalanceRecord(Long userId);
	//=========================================新版余额宝====================================
	/**
	 * 根据会员用户转入的金额数量
	 * 		插入用户账户金额存取记录数据   --新版本余额宝
	 * @param user
	 * @param param
	 */
	boolean insertIntoUserBalanceRecordNew(UserEntity user, SwitchParamNew param);
	/**
	 * 通过用户id+理财产品id  查询 用户账户金额存取记录表		
	 * 		条件是  1. 类型：存入				--新版本余额宝
	 * 			 2.是否生效：未生效的  
	 * 			 3.money>takeMoney
	 * @param userId
	 */
	List<UserBalanceRecordEntity> getListUserBalanceRecordNew(Long userId,SwitchOutParamNew param);
	
}

