package com.xmsy.server.zxyy.game.modules.manager.room.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.game.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:05:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("room")
public class RoomEntity extends BaseEntity<RoomEntity> {

	private static final long serialVersionUID = 1L;
	/**
	 * 房间名称
	 */
	private String name;
	/**
	 * 是否可用
	 */
	private Boolean enable;
	/**
	 * 是否显示
	 */
	private Boolean display;
	/**
	 * 游戏id数组
	 */
	private String gameIds;

}
