package com.xmsy.server.zxyy.manager.modules.manager.webhomealertcofig.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.webhomealertcofig.dao.WebhomeAlertCofigDao;
import com.xmsy.server.zxyy.manager.modules.manager.webhomealertcofig.entity.WebhomeAlertCofigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomealertcofig.service.WebhomeAlertCofigService;
import com.xmsy.server.zxyy.manager.modules.web.homepage.entity.WebhomeAlertEntity;


@Service("webhomeAlertCofigService")
public class WebhomeAlertCofigServiceImpl extends ServiceImpl<WebhomeAlertCofigDao, WebhomeAlertCofigEntity> implements WebhomeAlertCofigService {

	@Override
	public List<WebhomeAlertEntity> getAlertAd() {
		// TODO Auto-generated method stub
		return baseMapper.getAlertAd();
	}


}
