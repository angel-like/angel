package com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.dao.PayChannelConfigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.entity.PayChannelConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.service.PayChannelConfigService;


@Service("payChannelConfigService")
public class PayChannelConfigServiceImpl extends ServiceImpl<PayChannelConfigDao, PayChannelConfigEntity> implements PayChannelConfigService {

	@Override
	public Page<PayChannelConfigEntity> getPayChannelConfigs(PayChannelConfigEntity paychannelconfig,
			PageParam pageParam) {
		Page<PayChannelConfigEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getPayChannelConfigs(page, paychannelconfig));
	}


}
