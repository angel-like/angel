package com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodelprop.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodelprop.entity.SysMessageModelPropEntity;


/**
 * 站内信
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-22 16:15:53
 */
public interface SysMessageModelPropService extends IService<SysMessageModelPropEntity> {
	
	/**
	 * 根据模板邮件id查询列表信息
	 * @return
	 */
	Page<SysMessageModelPropEntity> queryByMessageId(SysMessageModelPropEntity prop, PageParam pageParam);
	

}

