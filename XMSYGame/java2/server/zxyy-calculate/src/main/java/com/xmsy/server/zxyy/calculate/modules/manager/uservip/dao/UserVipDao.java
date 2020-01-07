package com.xmsy.server.zxyy.calculate.modules.manager.uservip.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.entity.UserVipEntity;

/**
 * 用户vip等级表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 15:18:13
 */
@Mapper
public interface UserVipDao extends BaseMapper<UserVipEntity> {
	UserVipEntity getUserVip(@Param("rechargeAmount")Long rechargeAmount);
	
	/**
	 * 查询上一个vip
	 * @param sort
	 * @return
	 */
	UserVipEntity selectNextVip(@Param("sort") Integer sort);
}
