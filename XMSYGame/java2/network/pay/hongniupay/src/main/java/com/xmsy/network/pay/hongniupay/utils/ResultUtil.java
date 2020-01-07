package com.xmsy.network.pay.hongniupay.utils;


import com.xmsy.network.pay.hongniupay.def.Config;

/**
 * .支付结果工具类
 *
 * @author aleng
 * @version 1.0
 * @date 2018年11月22日
 */
public class ResultUtil {
    /**
     * 支付是否成功
     *
     * @param result
     * @return
     */
    public static boolean success(String code) {
        return Config.ORDER_SUCCESS.equals(code);
    }
}
