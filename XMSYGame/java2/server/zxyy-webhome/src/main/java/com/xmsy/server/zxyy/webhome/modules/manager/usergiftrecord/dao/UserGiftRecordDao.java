package com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户道具发放记录
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-24 09:34:18
 */
@Mapper
public interface UserGiftRecordDao extends BaseMapper<UserGiftRecordEntity> {
	int insertGetId(UserGiftRecordEntity entity);
	
	
	/**
	 * 通过userId和activity查询
	 * @return
	 */
	UserGiftRecordEntity queryGiftRecord(@Param("userId") Long userId,@Param("activityId") Long activityId);
}
