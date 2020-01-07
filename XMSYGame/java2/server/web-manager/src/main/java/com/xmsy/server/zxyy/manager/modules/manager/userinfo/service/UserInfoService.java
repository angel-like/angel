package com.xmsy.server.zxyy.manager.modules.manager.userinfo.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.result.UserPushInfo;

/**
 * 用户基本信息表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-15 10:49:21
 */
public interface UserInfoService extends IService<UserInfoEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 更新用户基本信息 没有内容更新为空
	 * 
	 * @param entity
	 * @return
	 */
	Integer updateUserBaseInfo(UserInfoEntity entity);

	/**
	 * 更新用户基本信息 没有内容更新为空
	 * 
	 * @param entity
	 * @return
	 */
	Integer updateByUserId(UserInfoEntity entity);

	/**
	 * 逻辑删除用户基本信息
	 * 
	 * @param userId
	 * @return
	 */
	Integer deleteUserInfoByUserId(Long userId);

	/**
	 * 根据用户账号获取用户推送信息
	 * 
	 * @param accountList
	 * @return
	 */
	List<UserPushInfo> findUserInfoListByAccount(List<String> accountList);

	/**
	 * 根据用户层级获取用户集合
	 * 
	 * @param hierarchyId
	 * @return
	 */
	List<String> findUserInfoListByhierarchyId(List<String> hierarchyIds);
}
