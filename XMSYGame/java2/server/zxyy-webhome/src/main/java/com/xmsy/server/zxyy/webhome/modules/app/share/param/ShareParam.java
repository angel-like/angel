package com.xmsy.server.zxyy.webhome.modules.app.share.param;

import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class ShareParam {
	
	@NotNull(message = "客户端类型不能为空")
	private String clientType;//客户端类型
	@NotNull(message = "分享到哪里不能为空")
	private String shareTo;//分享到

}
