package com.xmsy.server.zxyy.webhome.modules.manager.appalertcofig.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.AppAlertConfigParam;
import com.xmsy.server.zxyy.webhome.modules.manager.appalertcofig.dao.AppAlertCofigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.appalertcofig.entity.AppAlertCofigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.appalertcofig.service.AppAlertCofigService;


@Service("appAlertCofigService")
public class AppAlertCofigServiceImpl extends ServiceImpl<AppAlertCofigDao, AppAlertCofigEntity> implements AppAlertCofigService {

	@Override
	public List<AppAlertConfigParam> selectListForWeb() {
		return baseMapper.selectListForWeb();
	}


}
