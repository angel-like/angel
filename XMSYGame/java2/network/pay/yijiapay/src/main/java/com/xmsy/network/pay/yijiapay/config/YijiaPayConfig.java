package com.xmsy.network.pay.yijiapay.config;

import org.springframework.stereotype.Component;

import com.xmsy.network.pay.paybase.config.PayConfigBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * .支付配置
 * 
 * @author aleng
 *
 */
@Data
@Component
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class YijiaPayConfig extends PayConfigBase {

	// 公钥
	private String publicKey;
	// 私钥
	private String privateKey;

}
