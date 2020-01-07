package com.xmsy.server.zxyy.manager.modules.manager.userold.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.userold.entity.UserOldEntity;

/**
 * 用户信息表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-22 15:56:39
 */
@Mapper
public interface UserOldDao extends BaseMapper<UserOldEntity> {
	/**
	 * 会员管理页面分页查询
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserPage(@Param("user") UserOldEntity user, Pagination page);
	/**
	 * 修改备注
	 * 
	 * @param userEntity
	 * @return
	 */
	Integer updateUserRemark(UserOldEntity userOldEntity);
}
