package com.xmsy.server.zxyy.webhome.modules.manager.userlog.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.userlog.entity.UserLogEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
