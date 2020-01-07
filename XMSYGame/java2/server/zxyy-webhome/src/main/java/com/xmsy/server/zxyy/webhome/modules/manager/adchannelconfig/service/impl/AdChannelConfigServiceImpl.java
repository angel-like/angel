package com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.dao.AdChannelConfigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.service.AdChannelConfigService;


@Service("adChannelConfigService")
public class AdChannelConfigServiceImpl extends ServiceImpl<AdChannelConfigDao, AdChannelConfigEntity> implements AdChannelConfigService {

	@Override
	public AdChannelConfigEntity findAdChannelByChannelCode(String channelCode) {
		return this.baseMapper.findAdChannelByChannelCode(channelCode);
	}


}
