package com.xmsy.server.zxyy.payment.service.modules.payconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xmsy.server.zxyy.payment.service.modules.payconfig.dao.PayConfigDao;
import com.xmsy.server.zxyy.payment.service.modules.payconfig.entity.PayConfigEntity;

import com.xmsy.server.zxyy.payment.service.modules.payconfig.service.PayConfigService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;



/**
 * .支付公司配置
 *
 * @author Administrator
 *
 */
@Slf4j
@Service("payConfigService")
public class PayConfigServiceImpl extends ServiceImpl<PayConfigDao, PayConfigEntity> implements PayConfigService {


}
