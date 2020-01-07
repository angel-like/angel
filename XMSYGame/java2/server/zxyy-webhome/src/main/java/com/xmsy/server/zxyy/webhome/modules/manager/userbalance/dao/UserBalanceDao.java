package com.xmsy.server.zxyy.webhome.modules.manager.userbalance.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.entity.UserBalanceEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户余额宝金额表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
@Mapper
public interface UserBalanceDao extends BaseMapper<UserBalanceEntity> {
	/**
	 * 增加或者减去 传入实体类用户里的金额
	 * @param entity
	 * @return
	 */
	Integer updateUserBalanceByUserId(UserBalanceEntity entity);
	/**
	 * 增加或者减去 传入实体类用户里的金额	---新版本余额宝
	 * @param entity
	 * @return
	 */
	Integer updateUserBalanceByUserIdNew(UserBalanceEntity entity);
}
