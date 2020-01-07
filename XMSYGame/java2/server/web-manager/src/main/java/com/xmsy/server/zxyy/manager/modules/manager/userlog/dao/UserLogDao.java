package com.xmsy.server.zxyy.manager.modules.manager.userlog.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userlog.entity.UserLogEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-04 16:04:27
 */
@Mapper
public interface UserLogDao extends BaseMapper<UserLogEntity> {
	
}
