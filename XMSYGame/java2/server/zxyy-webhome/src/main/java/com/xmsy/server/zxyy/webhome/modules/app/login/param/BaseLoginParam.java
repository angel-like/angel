package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseLoginParam {
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 设备码
	 */
	private String deviceCode;

	/**
	 * 版本号
	 */
	private String edition;
	/**
	 * 推送id
	 */
	private String jpushRegId;

	
	/**
	 * 广告渠道代码
	 */
	private String channelCode;
	/**
	 * 邀请码
	 */
	private String invitationCode;

}
