package com.xmsy.network.pay.zhaoxinpay.config;

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
public class ZhaoXinPayConfig extends PayConfigBase {
    //  type  101 支付宝扫码；102 支付宝h5；103 微信扫码；104 银联扫码；105 银联h5；106 微信h5
    private String type;
}
