package com.xmsy.server.zxyy.manager.utils;

import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;

/**
 * .用户状态校验
 * 
 * @author Administrator
 *
 */
public class UserStatusVerificationUtil {

	/**
	 * 用户是否被停止下注
	 */
	public static void userNobetValidate(boolean nobetEnable) {
		if (nobetEnable) {
			throw new RRException(ErrorCode.UserErrorCode.USER_NOBET_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_NOBET_ERRO.getCode());
		}
	};

	/**
	 * 用户是否被冻结
	 */
	public static void userFrozenValidate(boolean frozenEnable) {
		if (frozenEnable) {
			throw new RRException(ErrorCode.UserErrorCode.USER_FROZEN_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_FROZEN_ERRO.getCode());
		}
	};

	/**
	 * 用户是否被禁用
	 */
	public static void userForbiddenValidate(boolean forbiddenEnable) {
		if (forbiddenEnable) {
			throw new RRException(ErrorCode.UserErrorCode.USER_FORBIDDEN_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_FORBIDDEN_ERRO.getCode());
		}
	};

}
