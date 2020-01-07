package com.xmsy.network.pay.paybase.utils;

import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.network.pay.paybase.config.PayConfigBase;
import com.xmsy.network.pay.paybase.define.PayConfigConstant;
import com.xmsy.network.pay.paybase.define.PayPlatform;

/**
 * .通用支付配置
 *
 * @author aleng
 *
 */
public class PayConfigUtils {

	/**
	 * .设置支付配置信息
	 *
	 * @param commonConfig
	 * @param payConfig
	 */
	public static final void setPayConfig(SysConfigMessage commonConfig, PayConfigBase payConfig,String callbackUrl) {
		if (null == commonConfig || null == commonConfig.getChildren()) {
			return;
		}
		for (SysConfigMessage config : commonConfig.getChildren()) {
			switch (config.getName()) {
			case PayConfigConstant.CALLBACK_URL:
				setCallBackUrl(config, payConfig);
				payConfig.setCallbackUrl(callbackUrl+commonConfig.getValue());
				continue;
			case PayConfigConstant.ORDER_URL:
				setOrderUrl(config, payConfig);
				continue;
			case PayConfigConstant.KEY:
				setUidAndSecret(config, payConfig);
				continue;
			case PayConfigConstant.PRODUCT:
				setProduct(config, payConfig);
				continue;
			default:
				continue;
			}

		}
	}

	/**
	 * .设置回调地址
	 *
	 * @param commonConfig
	 * @param payConfig
	 */
	private static final void setCallBackUrl(SysConfigMessage commonConfig, PayConfigBase payConfig) {

		if (!StringUtils.isEmpty(commonConfig.getChildren())) {
			payConfig.setCallbackUrl(StringUtil.replaceBlank(commonConfig.getChildren().get(0).getValue()));
		}

	}

	/**
	 * .设置商户id和密钥
	 *
	 * @param commonConfig
	 * @param payConfig
	 */
	private static final void setUidAndSecret(SysConfigMessage commonConfig, PayConfigBase payConfig) {
		if (StringUtils.isEmpty(commonConfig.getChildren())) {
			return;
		}
		for (SysConfigMessage config : commonConfig.getChildren()) {
			if (PayConfigConstant.UID.equals(config.getName())) {
				payConfig.setUid(StringUtil.replaceBlank(config.getValue()));
			}
			if (PayConfigConstant.SECRET.equals(config.getName())) {
				payConfig.setSecret(StringUtil.replaceBlank(config.getValue()));
			}
			if (PayConfigConstant.PUBLIC_KEY.equals(config.getName())) {
				payConfig.setPublicKey(StringUtil.replaceBlank(config.getValue()));
			}
			if (PayConfigConstant.PRIVATE_KEY.equals(config.getName())) {
				payConfig.setPrivateKey(StringUtil.replaceBlank(config.getValue()));
			}
		}

	}

	/**
	 * .设置支付地址
	 *
	 * @param commonConfig
	 * @param payConfig
	 */
	private static final void setOrderUrl(SysConfigMessage commonConfig, PayConfigBase payConfig) {
		if (StringUtils.isEmpty(commonConfig.getChildren())) {
			return;
		}


		for (SysConfigMessage config : commonConfig.getChildren()) {
			if (PayConfigConstant.WX_URL.equals(config.getName())) {
				payConfig.setWxUrl(StringUtil.replaceBlank(config.getValue()));
			}else if (PayConfigConstant.ALI_URL.equals(config.getName())) {
				payConfig.setAliUrl(StringUtil.replaceBlank(config.getValue()));
			}else{
				payConfig.setOrderUrl(StringUtil.replaceBlank(config.getValue()));
			}
		}

		if (payConfig.getWxUrl()==null || (StringUtils.isEmpty(payConfig.getWxUrl().toString()))){
			payConfig.setWxUrl(payConfig.getOrderUrl());
		}

		if (payConfig.getAliUrl()==null || (StringUtils.isEmpty(payConfig.getAliUrl().toString()))){
			payConfig.setAliUrl(payConfig.getOrderUrl());
		}
	}

	/**
	 * .设置支付产品
	 *
	 * @param commonConfig
	 * @param payConfig
	 */
	private static final void setProduct(SysConfigMessage commonConfig, PayConfigBase payConfig) {
		if (CollectionUtils.isEmpty(commonConfig.getChildren())) {
			return;
		}
		Map<String, String> h5Product = Maps.newConcurrentMap();
		Map<String, String> pcProduct = Maps.newConcurrentMap();
		for (SysConfigMessage config : commonConfig.getChildren()) {
			if (CollectionUtils.isEmpty(config.getChildren())) {
				continue;
			}
			if (PayConfigConstant.H5.equals(config.getName())) {
				for (SysConfigMessage h5Config : config.getChildren()) {
					h5Product.put(StringUtil.replaceBlank(h5Config.getName()),
							StringUtil.replaceBlank(h5Config.getValue()));
				}
			}
			if (PayConfigConstant.PC.equals(config.getName())) {
				for (SysConfigMessage pcConfig : config.getChildren()) {
					pcProduct.put(StringUtil.replaceBlank(pcConfig.getName()),
							StringUtil.replaceBlank(pcConfig.getValue()));
				}
			}
		}
		Map<PayPlatform, Map<String, String>> product = Maps.newConcurrentMap();
		product.put(PayPlatform.H5, h5Product);
		product.put(PayPlatform.PC, pcProduct);
		payConfig.setProduct(product);
	}

}
