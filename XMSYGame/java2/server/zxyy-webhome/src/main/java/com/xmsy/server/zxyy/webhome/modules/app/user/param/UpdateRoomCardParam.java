package com.xmsy.server.zxyy.webhome.modules.app.user.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateRoomCardParam {
	
	
	@NotNull(message = "用户id不能为空")
	private Long userId;//用户id
	
	@NotNull(message = "房卡数量不能为空")
	private Long roomCard;//房卡数量
}
