package com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.entity.UserGameNumberEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户每日游戏次数统计表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-06-18 10:46:57
 */
@Mapper
public interface UserGameNumberDao extends BaseMapper<UserGameNumberEntity> {
	Integer updateUserGameNumber(UserGameNumberEntity userGameNumberEntity);
	
	
	/**
	 * 查询输赢，对局次数
	 * @param userGameNumberEntity
	 * @return
	 */
	UserGameNumberEntity queryNum(@Param("userId") Long userId,@Param("roomId") Long roomId,
			@Param("gameId") Long gameId,@Param("countDate") Date countDate);
}
