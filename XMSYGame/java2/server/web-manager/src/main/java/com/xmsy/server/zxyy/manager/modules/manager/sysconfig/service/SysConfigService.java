package com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigPayParam;

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
	/**
	 * 通过  4个数据(产品编码,密钥,回调地址,支付地址)  获取 支付服务SysConfig配置信息类
	 * @param sysConfigList
	 * @return
	 */
	public SysConfigPayParam gainSysConfigPayParam(List<SysConfigEntity> sysConfigList,SysConfigPayParam sysConfigPayParam);
	/**
	 * 批量修改或新增支付配置的参数
	 * @param sysConfigPayParam
	 */
	public void updateOrSaveSysConfigPayParam(SysConfigPayParam sysConfigPayParam) ;
}
