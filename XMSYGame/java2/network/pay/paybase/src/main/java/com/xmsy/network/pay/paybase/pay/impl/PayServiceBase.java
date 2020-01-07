package com.xmsy.network.pay.paybase.pay.impl;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.Payment;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付模板类
 *
 * @author aleng
 *
 */
@Slf4j
public abstract class PayServiceBase implements Payment {

	/**
	 * .key:第三方支付公司别名 .value:具体的第三方支付公司
	 */
	private static final Map<String, PayServiceBase> PAY_SERVICE_MAP = Maps.newConcurrentMap();

	/**
	 * .key:第三方支付公司别名 .value:具体的第三方支付公司
	 */
	public static final void payServiceInit(List<SysConfigMessage> commonConfigs,String callbackUrl) {
		log.info("支付公司配置初始化：commonConfigs {}", commonConfigs);
		for (SysConfigMessage commonConfig : commonConfigs) {
			if (StringUtils.isEmpty(commonConfig.getName())) {
				continue;
			}
			PayServiceBase payService = PAY_SERVICE_MAP.get(commonConfig.getValue());
			if (null == payService) {
				log.error("支付公司 ：{} -找不到对应的服务 value {} ", commonConfig.getName(), commonConfig.getValue());
				continue;
			}
			payService.init(commonConfig,callbackUrl);
		}
	}

	@Override
	public GlobalResult<ResultData> pay(PayParam param) {
		log.info("[pay] param {}", param);
		if (null == param || StringUtils.isEmpty(param.getOrderNo())) {
			log.error("[pay] param {}", param);
			return ResultUtils.getErrorResult(CodeDef.PARAM_INVALID);
		}
		if (param.getAmount() == 0) {
			log.error("[pay] param {}", param);
			return ResultUtils.getErrorResult(CodeDef.AMOUNT_INVALID);
		}
		return doPay(param);
	}

	/**
	 * .获取支付公司别名
	 */
	protected abstract String getName();

	/**
	 * .支付逻辑
	 */
	protected abstract GlobalResult<ResultData> doPay(PayParam param);

	/**
	 * .初始化
	 */
	protected abstract void init(SysConfigMessage commonConfig,String callbackUrl);

	/**
	 * .获取校验支付渠道是否存在
	 */
	public abstract boolean payChannelVerify(String payChannel, PayPlatform payPlatform);

	/**
	 * .获取支付公司集合
	 */
	public static Map<String, PayServiceBase> getPayServiceMap() {
		return PAY_SERVICE_MAP;
	}

}
