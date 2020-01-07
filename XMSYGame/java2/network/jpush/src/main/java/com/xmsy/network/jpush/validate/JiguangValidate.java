package com.xmsy.network.jpush.validate;

import com.xmsy.network.jpush.constant.ErrorDef;

import cn.jiguang.common.utils.StringUtils;

/**
 * .参数校验
 * 
 * @author Administrator
 *
 */
public class JiguangValidate {
	// 判空
	public static void validKey(String appKey, String masterSecret) throws RuntimeException {
		if (StringUtils.isEmpty(appKey)) {
			throw new RuntimeException(ErrorDef.APPKEY_ERROR.getMsg());
		}
		if (StringUtils.isEmpty(masterSecret)) {
			throw new RuntimeException(ErrorDef.MASTER_SECRET_ERROR.getMsg());
		}
	}
}
