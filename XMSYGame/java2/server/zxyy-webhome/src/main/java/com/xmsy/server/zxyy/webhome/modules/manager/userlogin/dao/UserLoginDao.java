package com.xmsy.server.zxyy.webhome.modules.manager.userlogin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.entity.UserLoginEntity;

/**
 * 用户登陆记录表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Mapper
public interface UserLoginDao extends BaseMapper<UserLoginEntity> {

	List<UserLoginEntity> pageList(Pagination page, @Param("entity")UserLoginEntity userLogin);
	
	/**
	 * 根据登陆设备类型计算当日登陆人数
	 * @return
	 */
	List<Map<String, Object>> countLoginNumByDeviceType();
	/**
	 * 根据登陆设备类型计算当日登陆人数
	 * @return
	 */
	List<Map<String, Object>> countOnlineNumByDeviceType();
}
