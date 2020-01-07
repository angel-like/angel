package com.xmsy.server.zxyy.webhome.modules.manager.userpassword.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.entity.UserPasswordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户密码表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Mapper
public interface UserPasswordDao extends BaseMapper<UserPasswordEntity> {

    String     selectByUserId(Long userId);
}
