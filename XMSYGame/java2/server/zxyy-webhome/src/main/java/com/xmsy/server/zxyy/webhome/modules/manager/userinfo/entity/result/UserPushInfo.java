package com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.result;

import lombok.Data;

/**
 * .用户推送信息
 * 
 * @author Administrator
 *
 */
@Data
public class UserPushInfo {

	// 用户账号
	private String account;
	// 推送id
	private String jpushRegId;

}
