package com.xmsy.server.zxyy.calculate.common.utils;

/**
 * 常量 错误码
 * 
 * @author xiaoliu
 * @email xxxxxx
 * @date 2016年11月15日 下午1:23:52
 */
public class ErrorCode {
	// 用户模块错误码
	public enum UserErrorCode {
		/**
		 * 额度不足：账号余额小于提款金额
		 */
		MONEY_NOT_ENOUGH(5023, "额度不足"),
		/**
		 * 用户金币不足
		 */
		USER_COIN_NOT_ENOUGH(5014, "用户金币不足"),
		/**
		 * 用户获取失败
		 */
		USER_IS_NULL(5002, "用户获取失败");
		private Integer code;
		private String errMsg;

		UserErrorCode(int code, String errMsg) {
			this.code = code;
			this.errMsg = errMsg;
		}

		public Integer getCode() {
			return code;
		}

		public String getErrMsg() {
			return errMsg;
		}
	}

	// Token模块错误码
	public enum TokenCode {
		/**
		 * token验证失败 (拦截器)
		 */
		TOKEN_INVALID_LOSE(5100, "token已失效"),
		/**
		 * token为空 (拦截器)
		 */
		TOKEN_IS_NULL(5101, "token为空");

		private Integer code;
		private String errMsg;

		TokenCode(int code, String errMsg) {
			this.code = code;
			this.errMsg = errMsg;
		}

		public Integer getCode() {
			return code;
		}

		public String getErrMsg() {
			return errMsg;
		}

	}

	// 用户模块错误码
	public enum ExchangeAccountCode {
		/**
		 * 登陆密码为空
		 */
		LOGIN_PASSWORD_ISNULL(6000, "登陆密码为空"),
		/**
		 * 登陆密码格式有误
		 */
		LOGIN_PASSWORD_FORMAT_ERRO(6001, "登陆密码格式有误"),
		/**
		 * 登陆密码前后不一致
		 */
		LOGIN_PASSWORD_VERIFY_ERRO(6002, "登陆密码前后不一致"),
		/**
		 * 交易密码为空
		 */
		TRANSACTION_PASSWORD_ISNULL(6003, "交易密码为空"),
		/**
		 * 交易密码前后不一致
		 */
		TRANSACTION_PASSWORD_VERIFY_ERRO(6004, "交易密码前后不一致"),
		/**
		 * 交易密码格式有误
		 */
		TRANSACTION_PASSWORD_FORMAT_ERRO(6005, "交易密码格式有误"),
		/**
		 * 密码有误
		 */
		PASSWORD_VERIFY_ERRO(6006, "密码有误"),
		/**
		 * 账户ID为空
		 */
		ACCOUNT_ID_ISNULL(6007, "账户ID为空"),
		/**
		 * 账户获取失败
		 */
		ACCOUNT_FREEZE(6008, "账户已被冻结"),
		/**
		 * 账户获取失败
		 */
		ACCOUNT_ISNULL(6009, "账户获取失败");

		private Integer code;
		private String errMsg;

		ExchangeAccountCode(int code, String errMsg) {
			this.code = code;
			this.errMsg = errMsg;
		}

		public Integer getCode() {
			return code;
		}

		public String getErrMsg() {
			return errMsg;
		}

	}

	// 货币错误码
	public enum ExchangeMoneyCode {
		/**
		 * 货币获取失败
		 */
		MONEY_ISNULL(7000, "货币获取失败"),
		/**
		 * 货币已被兑换
		 */
		MONEY_EXCHANGE_ERRO(7001, "货币已被兑换"),
		/**
		 * 货币不足
		 */
		MONEY_INSUFFICIENT_ERRO(7002, "货币不足");

		private Integer code;
		private String errMsg;

		ExchangeMoneyCode(int code, String errMsg) {
			this.code = code;
			this.errMsg = errMsg;
		}

		public Integer getCode() {
			return code;
		}

		public String getErrMsg() {
			return errMsg;
		}

	}

	// 汇率错误码
	public enum rateCode {
		/**
		 * 无初始汇率
		 */
		RATE_ISNULL(8000, "无初始汇率");

		private Integer code;
		private String errMsg;

		rateCode(int code, String errMsg) {
			this.code = code;
			this.errMsg = errMsg;
		}

		public Integer getCode() {
			return code;
		}

		public String getErrMsg() {
			return errMsg;
		}

	}

	// 消息模块
	public enum messageCode {
		/**
		 * 消息id为空
		 */
		MESSAGE_ID_ERRO(30001, "消息Id为空"),
		/**
		 * 道具已被领取
		 */
		MESSAGE_IS_RECEIVE(30002, "道具已被领取");

		private Integer code;
		private String errMsg;

		messageCode(int code, String errMsg) {
			this.code = code;
			this.errMsg = errMsg;
		}

		public Integer getCode() {
			return code;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}

	}

}
