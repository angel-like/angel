package com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.ConfigUrationParam;
import com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.entity.AppPayCofigEntity;


/**
 * app支付配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-04 10:32:45
 */
public interface AppPayCofigService extends IService<AppPayCofigEntity> {

	List<ConfigUrationParam> selectPayConfigList(Long userId);

}

