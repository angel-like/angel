package com.xmsy.server.zxyy.payment.service.modules.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmsy.server.zxyy.payment.service.modules.sysconfig.entity.SysConfigEntity;

/**
 * 系统配置表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-21 15:26:43
 */
public interface SysConfigService extends IService<SysConfigEntity> {

	/**
	 * .根据上级key和参数key查询对应的值
	 * 
	 * @param parentKey
	 * @param paramKey
	 * @return
	 */
	public String selectByParamKey(String parentKey, String paramKey);

}
