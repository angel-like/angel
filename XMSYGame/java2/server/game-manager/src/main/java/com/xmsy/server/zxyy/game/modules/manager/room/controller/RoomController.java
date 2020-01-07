package com.xmsy.server.zxyy.game.modules.manager.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.common.utils.PageUtils;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.RoomEntity;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.param.RoomVo;
import com.xmsy.server.zxyy.game.modules.manager.room.service.RoomService;

/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:05:45
 */
@RestController
@RequestMapping("room/room")
public class RoomController {
	@Autowired
	private RoomService roomService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("room:room:list")
	public R list(RoomEntity room, PageParam pageParam) {
		Page<RoomVo> result = roomService.getRooms(room, pageParam);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("room:room:info")
	public R info(@PathVariable("id") Long id) {
		RoomEntity room = roomService.selectById(id);
		return R.ok().put("room", room);
	}

	/**
	 * 查询房间列表
	 */
	@RequestMapping("/RoomList")
//	@RequiresPermissions("room:room:list")
	public R getRoomList() {
		RoomEntity room = new RoomEntity();
		room.setEnable(true);
		return R.ok().put("roomList", roomService.selectList(new EntityWrapper<RoomEntity>(room)));
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
//	@RequiresPermissions("room:room:save")
	public R save(@RequestBody RoomEntity room) {
		roomService.insert(room);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("room:room:update")
	public R update(@RequestBody RoomEntity room) {
		roomService.updateRoomById(room);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
//	@RequiresPermissions("room:room:delete")
	public R delete(@RequestBody Long id) {
		roomService.deleteRoom(id);
		return R.ok();
	}

}
