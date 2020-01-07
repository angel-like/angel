package com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.entity.SysRegisterNecessaryEntity;

/**
 * 注册必填控制表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-28 10:51:51
 */
public interface SysRegisterNecessaryService extends IService<SysRegisterNecessaryEntity> {

	public List<SysRegisterNecessaryEntity> getRegisterNecessary();
}
