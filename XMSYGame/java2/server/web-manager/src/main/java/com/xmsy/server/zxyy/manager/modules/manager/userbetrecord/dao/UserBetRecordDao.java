package com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.entity.UserBetRecordEntity;

/**
 * 用户每日有效下注
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-08 10:51:52
 */
@Mapper
public interface UserBetRecordDao extends BaseMapper<UserBetRecordEntity> {

	Long sumUserBet(@Param("userId") Long userId);

}
