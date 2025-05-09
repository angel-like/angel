package com.xmsy.server.zxyy.webhome.modules.manager.payconfig.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigResultEntity;

import java.util.List;

/**
 * 支付渠道配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-25 10:09:06
 */
public interface PayConfigService extends IService<PayConfigEntity> {

	void firstPush(Long id);

	List<PayConfigResultEntity> appRechargeNavigation();
	
	List<PayConfigResultEntity> webRechargeNavigation();

	//获取支付公司
	public PayConfigEntity selectByAliasName(String aliasName);

	//获取支付公司列表
	List<PayConfigEntity> getPayConfig();

	// 校验支付公司和支付渠道
	void valitePayConfig(Object paymentParam) throws Exception;

	
}
