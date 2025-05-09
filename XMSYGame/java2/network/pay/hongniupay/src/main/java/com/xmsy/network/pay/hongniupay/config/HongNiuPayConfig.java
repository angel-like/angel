package com.xmsy.network.pay.hongniupay.config;

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
public class HongNiuPayConfig extends PayConfigBase {
    //  type
    private String type;

    private String orderIp;
    private String username;
}
