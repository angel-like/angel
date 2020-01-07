package com.xmsy.server.zxyy.payment.service.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.server.zxyy.payment.service.modules.sysconfig.entity.SysConfigEntity;

/**
 * .系统配置类和通用配置类转换
 * 
 * @author aleng
 *
 */
public class BeanConversionTools {

	/**
	 * .系统配置列表转换成通用配置列表
	 * 
	 * @param sysConfigs
	 * @return
	 */
	public static final List<SysConfigMessage> commonConfigConversion(List<SysConfigEntity> sysConfigs) {
		List<SysConfigMessage> commonConfigs = Lists.newArrayList();
		SysConfigMessage commonConfig = null;
		for (SysConfigEntity sysConfig : sysConfigs) {
			commonConfig = new SysConfigMessage();
			commonConfig.setId(sysConfig.getId());
			commonConfig.setName(sysConfig.getParamKey());
			commonConfig.setValue(sysConfig.getParamValue());
			commonConfig.setParent(sysConfig.getParentId());
			commonConfigs.add(commonConfig);
		}
		return commonConfigs;
	}

	/**
	 * .系统配置树形结构转换成通用配置树形结构
	 * 
	 * @param sysConfigs
	 * @return
	 */
	public static final List<SysConfigMessage> commonConfigConversion(SysConfigEntity sysConfig,
			List<SysConfigMessage> commonConfigs) {
		SysConfigMessage commonConfig = new SysConfigMessage();
		commonConfig.setId(sysConfig.getId());
		commonConfig.setName(sysConfig.getParamKey());
		commonConfig.setValue(sysConfig.getParamValue());
		commonConfig.setParent(sysConfig.getParentId());
		commonConfigs.add(commonConfig);
		List<SysConfigEntity> children = sysConfig.getChildren();
		if (CollectionUtils.isEmpty(children)) {
			return commonConfigs;
		}
		for (SysConfigEntity config : children) {
			commonConfig = new SysConfigMessage();
			commonConfig.setId(config.getId());
			commonConfig.setName(config.getParamKey());
			commonConfig.setValue(config.getParamValue());
			commonConfig.setParent(config.getParentId());
			commonConfigs.add(commonConfig);
			commonConfigConversion(config, commonConfigs);
		}
		return commonConfigs;
	}

}
