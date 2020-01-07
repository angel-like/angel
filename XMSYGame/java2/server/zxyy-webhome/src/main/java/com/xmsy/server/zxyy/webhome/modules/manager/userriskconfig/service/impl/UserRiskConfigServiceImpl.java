package com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.dao.UserRiskConfigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.entity.UserRiskConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.service.UserRiskConfigService;


@Service("userRiskConfigService")
public class UserRiskConfigServiceImpl extends ServiceImpl<UserRiskConfigDao, UserRiskConfigEntity> implements UserRiskConfigService {

	@Override
	public Long physicalDelete(Long id) {
		return baseMapper.physicalDelete(id);
	}

}
