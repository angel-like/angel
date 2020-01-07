package com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.entity.ImServiceManagerEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImServiceManagerResult;

/**
 * 33娱乐服务器管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-20 16:35:33
 */
public interface ImServiceManagerService extends IService<ImServiceManagerEntity> {
	
	Page<ImServiceManagerResult> selectServiceList(PageParam pageParam);

}
