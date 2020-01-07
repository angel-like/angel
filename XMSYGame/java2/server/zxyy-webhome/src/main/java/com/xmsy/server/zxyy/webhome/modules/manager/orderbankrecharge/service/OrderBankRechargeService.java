package com.xmsy.server.zxyy.webhome.modules.manager.orderbankrecharge.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.orderbankrecharge.entity.OrderBankRechargeEntity;

import java.util.Map;

/**
 * 银行卡充值
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-08 16:22:01
 */
public interface OrderBankRechargeService extends IService<OrderBankRechargeEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void confirmedById(Long id);
}

