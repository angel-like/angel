package com.xmsy.server.zxyy.payment.service.modules.config.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.payment.service.modules.config.service.SysConfigService;
import com.xmsy.server.zxyy.payment.service.modules.sysconfig.dao.SysConfigDao;
import com.xmsy.server.zxyy.payment.service.modules.sysconfig.entity.SysConfigEntity;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

	@Override
	public String selectByParamKey(String parentKey, String paramKey) {
		return baseMapper.findSysconfigValue(parentKey, paramKey);
	}

}
