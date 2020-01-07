package com.xmsy.server.zxyy.calculate.modules.manager.userlogin.dao;

import com.xmsy.server.zxyy.calculate.modules.manager.userlogin.entity.UserLoginEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户登陆记录表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Mapper
public interface UserLoginDao extends BaseMapper<UserLoginEntity> {
	
}
