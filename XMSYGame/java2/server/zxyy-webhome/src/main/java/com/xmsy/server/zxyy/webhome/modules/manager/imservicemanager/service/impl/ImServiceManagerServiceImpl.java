package com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.dao.ImServiceManagerDao;
import com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.entity.ImServiceManagerEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.service.ImServiceManagerService;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImServiceManagerResult;

@Service("imServiceManagerService")
public class ImServiceManagerServiceImpl extends ServiceImpl<ImServiceManagerDao, ImServiceManagerEntity>
		implements ImServiceManagerService {

	@Override
	public Page<ImServiceManagerResult> selectServiceList(PageParam pageParam) {
		Page<ImServiceManagerResult> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 // page 对象
		return page.setRecords(this.baseMapper.selectServiceList(page));
	}

}
