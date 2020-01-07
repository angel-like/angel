package com.xmsy.server.zxyy.calculate.modules.manager.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;

/**
 * 用户信息表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
	/**
	 * 根据ID修改用户余额，金币，佣金
	 * 
	 * @param userEntity
	 * @return
	 */
	Integer updateUserWalletByUserId(UserEntity userEntity);

	/**
	 * 修改用户的上级id
	 * 
	 * @param userEntity
	 * @return
	 */
	Integer updateUserSuperiorsId(@Param("userId") Long userId, @Param("superiorsId") Long superiorsId);
	
	/**
	 * 修改用户是否首冲状态
	 */
	void updateUserFirstRecharge(Long id);
	/**
	 * 修改用户VIP等级
	 * @param userId
	 * @param vipId
	 * @return
	 */
	Integer updateUserVip(@Param("userId") Long userId,@Param("vipId") Long vipId);

}
