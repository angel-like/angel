package com.xmsy.server.zxyy.webhome.modules.manager.appalertcofig.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.AppAlertConfigParam;
import com.xmsy.server.zxyy.webhome.modules.manager.appalertcofig.entity.AppAlertCofigEntity;


/**
 * app弹框配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-07 14:27:10
 */
public interface AppAlertCofigService extends IService<AppAlertCofigEntity> {

	List<AppAlertConfigParam> selectListForWeb();
}

