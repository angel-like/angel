package com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.dao;


import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.entity.UserBalanceRateEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户余额宝利率表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-17 15:56:57
 */
@Mapper
public interface UserBalanceRateDao extends BaseMapper<UserBalanceRateEntity> {
	/**
	 * 余额宝利率页面分页查询(根据最近)
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserPageForTime(@Param("user") UserBalanceRateEntity user, Pagination page);
}
