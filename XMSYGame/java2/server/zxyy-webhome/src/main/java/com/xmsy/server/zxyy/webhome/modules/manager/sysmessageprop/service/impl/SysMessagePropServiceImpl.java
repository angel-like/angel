package com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.dao.SysMessagePropDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.entity.SysMessagePropEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.service.SysMessagePropService;


@Service("sysMessagePropService")
public class SysMessagePropServiceImpl extends ServiceImpl<SysMessagePropDao, SysMessagePropEntity> implements SysMessagePropService {

	@Override
	public List<Map<String, Object>> findMessagePropListByMessageId(Long messageId) {
		return this.baseMapper.findMessagePropListByMessageId(messageId);
	}


}
