package com.xmsy.server.zxyy.game.modules.manager.room.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.RoomEntity;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.param.RoomVo;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:05:45
 */
@Mapper
public interface RoomDao extends BaseMapper<RoomEntity> {

	List<RoomVo> getRooms(@Param("room") RoomEntity room, PageParam pageParam);
	
	Integer updateRoomById(RoomEntity entity);

}
