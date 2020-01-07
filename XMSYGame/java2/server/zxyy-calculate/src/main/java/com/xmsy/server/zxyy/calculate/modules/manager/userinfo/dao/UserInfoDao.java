package com.xmsy.server.zxyy.calculate.modules.manager.userinfo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.userinfo.entity.UserInfoEntity;

/**
 * 用户基本信息表
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-15 10:49:21
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {
	/**
	 * 更新用户基本信息 没有内容更新为空
	 * @param entity
	 * @return
	 */
	Integer updateUserBaseInfo(UserInfoEntity entity);
	/**
	 * 更新用户地址
	 * 根据用户id
	 * @param entity
	 * @return
	 */
	Integer updateUserAddressByUserId(UserInfoEntity entity);
}
