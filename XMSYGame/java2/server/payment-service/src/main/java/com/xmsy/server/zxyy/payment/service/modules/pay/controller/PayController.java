package com.xmsy.server.zxyy.payment.service.modules.pay.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.common.bean.payment.PaymentParam;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * .
 *
 * @author 支付接口
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class PayController {

	/**
	 * 移动端充值返回一个支付链接
	 *
	 * @throws Exception
	 */
	@PostMapping("/h5/payment")
	public GlobalResult<ResultData> h5Pay(@RequestBody @Valid PaymentParam payParam) throws Exception {
		log.info("[PayController-h5pay] payParam {}", payParam);
		PayServiceBase payService = PayServiceBase.getPayServiceMap().get(payParam.getPayServiceName());
		if (null == PayServiceBase.getPayServiceMap().get(payParam.getPayServiceName())) {
			throw new ParamInvalidException("支付公司不存在");
		}
		if (!payService.payChannelVerify(payParam.getPayChannel(), PayPlatform.H5)) {
			throw new ParamInvalidException("支付公司对应的支付渠道不存在");
		}
		PayParam param = new PayParam().setOrderNo(payParam.getOrderNo()).setAmount(payParam.getAmount())
				.setOrderIp(payParam.getOrderIp()).setPayChannel(payParam.getPayChannel()).setPlatform(PayPlatform.H5).setUserName(payParam.getUserName());
		GlobalResult<ResultData> result = payService.pay(param);
		return result;
	}

	/**
	 * pc端充值返回一个支付二维码
	 *
	 * @throws Exception
	 */
	@PostMapping("/pc/payment")
	public GlobalResult<ResultData> pcPay(@RequestBody @Valid PaymentParam payParam) throws Exception {
		log.info("[PayController-pcpay] payParam {}", payParam);
		PayServiceBase payService = PayServiceBase.getPayServiceMap().get(payParam.getPayServiceName());
		if (null == PayServiceBase.getPayServiceMap().get(payParam.getPayServiceName())) {
			throw new ParamInvalidException("支付公司不存在");
		}
		if (!payService.payChannelVerify(payParam.getPayChannel(), PayPlatform.PC)) {
			throw new ParamInvalidException("支付公司对应的支付渠道不存在");
		}
		PayParam param = new PayParam().setOrderNo(payParam.getOrderNo()).setAmount(payParam.getAmount())
				.setOrderIp(payParam.getOrderIp()).setPayChannel(payParam.getPayChannel()).setPlatform(PayPlatform.PC).setUserName(payParam.getUserName());
		return payService.pay(param);
	}
}
