package com.xmsy.server.zxyy.webhome.modules.manager.uservip.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.uservip.entity.UserVipEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户vip等级表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 16:46:36
 */
@Mapper
public interface UserVipDao extends BaseMapper<UserVipEntity> {
	/**
	 * 查询上一个vip
	 * @param sort
	 * @return
	 */
	UserVipEntity selectNextVip(@Param("sort") Integer sort);
	
}
