package com.xmsy.server.zxyy.manager.modules.manager.signuserrecord.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.signuserrecord.entity.SignUserRecordEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户签到记录表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@Mapper
public interface SignUserRecordDao extends BaseMapper<SignUserRecordEntity> {
	
}
