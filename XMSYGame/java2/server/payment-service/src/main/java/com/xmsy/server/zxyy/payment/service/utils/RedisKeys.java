package com.xmsy.server.zxyy.payment.service.utils;

/**
 * Redis所有Keys
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getPayConfigKey(String key){
        return "pay:config:" + key;
    }
}
