package com.xmsy.server.zxyy.payment.service.modules.pay.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.payment.service.common.utils.IPUtils;
import com.xmsy.server.zxyy.payment.service.constant.PayConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.callback.PayOrderHandle;

import lombok.extern.slf4j.Slf4j;

/**
 * .回调处理
 *
 * @author aleng
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class CallBackController {

	@Resource
	private PayOrderHandle payOrderHandle;

	/**
	 * 移动端充值返回一个支付链接
	 *
	 * @throws Exception
	 */
	@RequestMapping("/payment/{payServiceName}")
	public void callback(@PathVariable("payServiceName") String payServiceName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s = PayConstant.CALLBACK_IP.get(payServiceName);
		log.info("[PayConstant.CALLBACK_IP]->data {}",PayConstant.CALLBACK_IP);
		log.info("[callbackip]->data {}",s);
		String realIp = IPUtils.getIpAddr(request);
		log.info("[realIp]->data {}",realIp);
		if (StringUtils.isNotBlank(s)&&!s.contains(realIp)){
			throw new ParamInvalidException("请求IP不在回调IP白名单");
		}
		if (BaseCallbackHandler.verfiyPayServiceName(payServiceName)) {//校验回调服务是否存在
			BaseCallbackHandler.callback(request, response, payServiceName, payOrderHandle);
		} else {
			log.error("[CallBackController]->callback payServiceName is not exist!");
		}
	}

}
