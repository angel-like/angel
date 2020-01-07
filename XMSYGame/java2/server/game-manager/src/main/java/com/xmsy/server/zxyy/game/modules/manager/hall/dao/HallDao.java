package com.xmsy.server.zxyy.game.modules.manager.hall.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.game.modules.manager.hall.entity.HallEntity;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:03:04
 */
@Mapper
public interface HallDao extends BaseMapper<HallEntity> {
	
	
	Integer updateHallById(HallEntity entity);
	
}
