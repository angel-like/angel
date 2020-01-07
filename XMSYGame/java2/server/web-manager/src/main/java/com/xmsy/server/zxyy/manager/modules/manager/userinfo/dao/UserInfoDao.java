package com.xmsy.server.zxyy.manager.modules.manager.userinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.result.UserPushInfo;

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
	 * 
	 * @param entity
	 * @return
	 */
	Integer updateUserBaseInfo(UserInfoEntity entity);

	/**
	 * 逻辑删除用户基本信息
	 * 
	 * @param userId
	 * @return
	 */
	Integer deleteUserInfoByUserId(Long userId);

	/**
	 * 根据userId更新用户详细信息
	 * 
	 * @param userId
	 * @return
	 */
	Integer updateByUserId(UserInfoEntity entity);

	/**
	 * 根据用户账号获取用户推送信息
	 * 
	 * @param accountList
	 * @return
	 */
	List<UserPushInfo> findUserInfoListByAccount(@Param("accountList") List<String> accountList);

	/**
	 * 根据用户层级获取用户集合
	 * 
	 * @param hierarchyId
	 * @return
	 */
	List<String> findUserInfoListByhierarchyId(@Param("hierarchyIds") List<String> hierarchyIds);
}
