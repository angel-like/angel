package com.xmsy.server.zxyy.game.modules.web.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.constant.SysConstant;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.RoomEntity;
import com.xmsy.server.zxyy.game.modules.manager.room.service.RoomService;

/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:05:45
 */
@RestController
@RequestMapping("web/room")
public class RoomHandler {

	@Autowired
	private RoomService roomService;

	/**
	 * 列表 @throws Exception @throws
	 */
	@GetMapping("/list")
	public R list(RoomEntity room) throws Exception {
		if (null != room.getId()) {
			return R.ok().put("rooms", Lists.newArrayList(roomService.selectAll().get(room.getId())));
		}
		return R.ok().put("rooms", roomService.selectAll().values());
	}

	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		RoomEntity room = roomService.selectById(id);
		return R.ok().put("room", room);
	}
	/**
	 * 信息
	 */
	@GetMapping("/roomSelect")
	public R info() {
		RoomEntity entity=new RoomEntity();
		entity.setDisplay(SysConstant.ENABLE_TRUE);
		entity.setEnable(SysConstant.ENABLE_TRUE);
		List<RoomEntity> list = roomService.selectList(new EntityWrapper<RoomEntity>(entity));
		return R.ok().put("data", list);
	}
}
