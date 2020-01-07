package com.xmsy.server.zxyy.manager.modules.manager.userblacklist.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userblacklist.entity.UserBlacklistEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户白名单表
 * 
 * @author lzy
 * @email xxxxx
 * @date 2019-01-15 10:50:25
 */
@Mapper
public interface UserBlacklistDao extends BaseMapper<UserBlacklistEntity> {
	
}
