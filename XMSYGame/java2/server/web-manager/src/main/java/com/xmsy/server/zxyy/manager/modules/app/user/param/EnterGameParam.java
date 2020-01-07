package com.xmsy.server.zxyy.manager.modules.app.user.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EnterGameParam {
	
	@NotNull(message = "用户id不能为空")
	private Long userId;//用户id
	@NotNull(message = "游戏信息id不能为空")
	private Long gameInfoId;//游戏信息id
	@NotNull(message = "游戏服务id不能为空")
	private Long gameServerId;//游戏服务id

}
