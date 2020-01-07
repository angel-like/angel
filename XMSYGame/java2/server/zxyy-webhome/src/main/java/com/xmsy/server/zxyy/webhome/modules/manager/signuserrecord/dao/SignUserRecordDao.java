package com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.entity.SignUserRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户签到记录表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@Mapper
public interface SignUserRecordDao extends BaseMapper<SignUserRecordEntity> {
	
	/**
	 * 获取签到次数
	 * @param userId
	 * @param signDate
	 * @return
	 */
	Integer continuousNum(@Param("userId") Long userId,@Param("signDate") Date signDate);
	
	/**
	 * 今日是否签到
	 * @param userId
	 * @param signDate
	 * @return
	 */
	Integer todaySign(@Param("userId") Long userId,@Param("signDate") String signDate);
	
	
	
}
