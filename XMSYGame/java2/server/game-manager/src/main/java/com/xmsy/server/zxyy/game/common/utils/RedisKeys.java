package com.xmsy.server.zxyy.game.common.utils;

/**
 * Redis所有Keys
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
