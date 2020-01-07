package com.xmsy.server.zxyy.game.modules.web.hall.result;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.xmsy.server.zxyy.game.modules.web.room.param.RoomResult;

import lombok.Data;

/**
 * 大厅返回对象，包含房间列表
 * @author 小旸
 *
 */
@Data
public class HallResult implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 大厅名称
	 */
	private String name;
	/**
	 * 房间id数组
	 */
	private List<RoomResult> rooms=Lists.newArrayList();

}
