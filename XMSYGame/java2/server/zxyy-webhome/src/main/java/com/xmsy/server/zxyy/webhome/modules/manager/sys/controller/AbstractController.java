package com.xmsy.server.zxyy.webhome.modules.manager.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysUserEntity;

/**
 * Controller公共组件
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
}
