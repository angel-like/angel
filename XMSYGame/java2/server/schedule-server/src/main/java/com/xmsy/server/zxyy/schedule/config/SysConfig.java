package com.xmsy.server.zxyy.schedule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * .系统配置
 *
 * @author aleng
 * @date 2018年1月12日 上午12:56:34
 * @Description: TODO
 */
@Configuration
public class SysConfig {

	@Value("${sysconfig.userGameServerCheckHour}")
	private int userGameServerCheckHour;

	public int getUserGameServerCheckHour() {
		return userGameServerCheckHour;
	}

}
