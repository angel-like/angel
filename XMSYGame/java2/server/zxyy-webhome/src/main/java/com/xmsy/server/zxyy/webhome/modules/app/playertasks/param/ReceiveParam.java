package com.xmsy.server.zxyy.webhome.modules.app.playertasks.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReceiveParam {
	
	@NotNull(message = "任务Id不能为空")
	private Long taskId;//任务Id

}
