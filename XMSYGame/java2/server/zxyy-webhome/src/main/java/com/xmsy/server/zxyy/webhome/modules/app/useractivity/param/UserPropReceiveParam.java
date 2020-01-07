package com.xmsy.server.zxyy.webhome.modules.app.useractivity.param;

import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class UserPropReceiveParam {
	
	@NotNull(message = "消息id不能为空")
	private Long messageId;//消息id

}
