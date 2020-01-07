package com.xmsy.server.zxyy.game.modules.manager.room.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.RoomEntity;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.param.RoomVo;
import com.xmsy.server.zxyy.game.modules.web.room.param.RoomResult;

/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:05:45
 */
public interface RoomService extends IService<RoomEntity> {

	Map<Long, RoomResult> selectAll() throws Exception;

	//房间信息
	Map<Long, RoomEntity> getRoomMap();

	/**
	 * 获取房间列表
	 * 
	 * @param room
	 * @param pageParam
	 * @return
	 */
	Page<RoomVo> getRooms(RoomEntity room, PageParam pageParam);

	// 修改房间
	Integer updateRoomById(RoomEntity entity);
	
	/**
	 * 删除房间
	 * @param id
	 */
	void deleteRoom(Long id);

}
