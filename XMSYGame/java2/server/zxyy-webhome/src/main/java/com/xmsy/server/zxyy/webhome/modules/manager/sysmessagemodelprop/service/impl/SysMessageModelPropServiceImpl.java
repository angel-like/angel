package com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodelprop.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodelprop.dao.SysMessageModelPropDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodelprop.entity.SysMessageModelPropEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodelprop.service.SysMessageModelPropService;


@Service("sysMessageModelPropService")
public class SysMessageModelPropServiceImpl extends ServiceImpl<SysMessageModelPropDao, SysMessageModelPropEntity> implements SysMessageModelPropService {


	
	/**
	 * 根据模板邮件id查询列表信息
	 * @return
	 */
	@Override
	public Page<SysMessageModelPropEntity> queryByMessageId(SysMessageModelPropEntity prop, PageParam pageParam) {
		Page<SysMessageModelPropEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.queryByMessageId(page, prop));
	}

	
	

}
