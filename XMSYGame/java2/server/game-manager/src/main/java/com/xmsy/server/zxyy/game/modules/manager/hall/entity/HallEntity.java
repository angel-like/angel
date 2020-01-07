package com.xmsy.server.zxyy.game.modules.manager.hall.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.google.common.base.Joiner;
import com.xmsy.server.zxyy.game.common.utils.SpringUtil;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.RoomEntity;
import com.xmsy.server.zxyy.game.modules.manager.room.service.RoomService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:03:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("hall")
public class HallEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	@TableField("id")
	private Long id;

	/**
	 * 大厅名称
	 */
	private String name;
	/**
	 * 房间id数组
	 */
	private String roomIds;

	/**
	 * 房间数组
	 */
	@TableField(exist = false)
	private String roomArray;

	public String getRoomArray() {
		String roomIds = this.roomIds;
		if (StringUtils.isEmpty(roomIds)) {
			return roomIds;
		}
		String[] roomIdArray = roomIds.split(",");
		String roomId = "";
		Map<Long, RoomEntity> roomMap = SpringUtil.getApplicationContext().getBean(RoomService.class).getRoomMap();
		for (int i = 0; i < roomIdArray.length; i++) {
			roomId = roomIdArray[i];
			if(roomMap.get(Long.valueOf(roomId)).getName()==null){
				continue;
			}
			roomIdArray[i] = roomMap.get(Long.valueOf(roomId)).getName();

		}
		return Joiner.on(",").join(roomIdArray);
	}

	// 数据版本（乐观锁实现）
	@Version
	@JSONField(serialize = false)
	@TableField(value = "version")
	private Integer version;

	// 删除状态
	@TableField(value = "delete_status", fill = FieldFill.INSERT)
	@TableLogic
	@JSONField(serialize = false)
	private Integer deleteStatus;
	// 添加时间
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	// 修改时间
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@JSONField(serialize = false)
	private Date updateTime;

}
