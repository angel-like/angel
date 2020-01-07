package com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.dao;

import com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.entity.UserGameNumberEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户每日游戏次数统计表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-06-18 17:20:02
 */
@Mapper
public interface UserGameNumberDao extends BaseMapper<UserGameNumberEntity> {
	
}
