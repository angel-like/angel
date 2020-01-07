package com.xmsy.server.zxyy.manager.common.utils;

/**
 * 限流类型
 * @author Administrator
 *
 */
public enum LimitType {
    /**
     * 自定义key
     */
    CUSTOMER,
    /**
     * 根据请求者IP
     */
    IP;
}
