package com.xmsy.server.zxyy.game.common.utils;

/**
 * 常量 错误码
 * 
 * @author xiaoliu
 * @email xxxxxx
 * @date 2016年11月15日 下午1:23:52
 */
public class ErrorCode {
	/** 无效用户 */
	public static final Integer INVALID_ACCOUNT_ERRO = 6001;
	public static final String INVALID_ACCOUNT_ERRO_MSG = "无效用户";

	/** 保存失败 */
	public static final Integer SAVE_ERRO = 6002;
	public static final String SAVE_ERRO_MSG = "保存失败";

	/** 保存失败 */
	public static final Integer OTP_FORMAT_ERRO = 6003;
	public static final String OTP_FORMAT_ERRO_MSG = "otp格式错误";

	/** 未选择用户 */
	public static final Integer NOCHOICE_ACCOUNT_ERRO = 6004;
	public static final String NOCHOICE_ACCOUNT_ERRO_MSG = "未选择用户";

	/** 用户未登陆 */
	public static final Integer ACCOUNT_LOGIN_ERRO = 6005;
	public static final String ACCOUNT_LOGIN_MSG = "用户未登陆";

	/** 用户账户获取失败 */
	public static final Integer USER_WALLET_ERRO = 6006;
	public static final String USER_WALLET_MSG = "用户账户获取失败";

	/** 用户登陆记录获取失败 */
	public static final Integer USER_LOGIN_ERRO = 6007;
	public static final String USER_LOGIN_MSG = "用户登陆记录获取失败";

	/** 第三方用户信息获取失败 */
	public static final Integer USER_ERRO = 6008;
	public static final String USER_MSG = "第三方用户信息获取失败";

	/** 第三方用户未绑定账号 */
	public static final Integer USER_ACCOUNT_ERRO = 6009;
	public static final String USER_ACCOUNT_MSG = "第三方用户未绑定账号，请先绑定账号";

	/** 密码重复 */
	public static final Integer USER_PASS_WORD_ERRO = 6010;
	public static final String USER_PASS_WORD_MSG = "新旧密码不可重复";

	/** 密码不可为空 */
	public static final Integer USER_PASS_WORD_ISNULL = 6012;
	public static final String USER_PASS_WORD_ISNULL_MSG = "密码不可为空";

	/** 无效验证码 */
	public static final Integer RECOMMENDATION_CODE_ISNULL = 6013;
	public static final String RECOMMENDATION_CODE_ISNULL_MSG = "无效验证码";

	/** 已存在待充值记录 */
	public static final Integer RECHARGE_BANK_ERRO = 6014;
	public static final String RECHARGE_BANK_MSG = "已存在待充值记录";

	/** 试玩账号不允许进行该操作 */
	public static final Integer USER_TRIAL_ERRO = 6015;
	public static final String USER_TRIAL_MSG = "试玩账号不允许进行该操作";

	/** 未绑定OTP密匙 */
	public static final Integer UNBOUND_OTP_KEYS_ERRO = 6020;
	public static final String UNBOUND_OTP_KEYS_MSG = "未绑定OTP密匙";
	/** Ip未在绑定OTP密匙Ip之列 */
	public static final Integer IP_NOT_BOUND_OTP_KEYS_ERRO = 6021;
	public static final String IP_NOT_BOUND_OTP_KEYS_MSG = "Ip未在绑定OTP密匙Ip之列";
	/** OTP验证失败 */
	public static final Integer OTP_VALIDATION_FAILURE_ERRO = 6022;
	public static final String OTP_VALIDATION_FAILURE_MSG = "OTP验证失败";

	/** OTP账号已经存在，一个用户只允许一个OTP账号 */
	public static final Integer USER_OTP_EXIST = 7001;
	public static final String USER_OTP_EXIST_MSG = "试玩账号不允许进行该操作";
	/** token验证失败返回code */
	public static final Integer lose = 50014;

	/** 无效token */
	public static final Integer invalid = 50008;

	/** 账号格式有误 */
	public static final Integer ACCOUNT_ERRO = 5001;
	public static final String ACCOUNT_ERRO_MSG = "账号格式有误";

	/** 登陆密码格式有误 */
	public static final Integer PASSWORD_ERRO = 5002;
	public static final String PASSWORD_ERRO_MSG = "登陆密码格式有误";

	/** 用户已存在 */
	public static final Integer ACCOUNT_ISUSED = 5003;
	public static final String ACCOUNT_ISUSED_MSG = "用户已存在";

	/** 前后密码不一致 */
	public static final Integer PASSWORD_ISUSED = 5004;
	public static final String PASSWORD_ISUSED_MSG = "前后密码不一致";

	/** 取款密码格式有误 */
	public static final Integer TAKE_PASSWORD_ISUSED = 5005;
	public static final String TAKE_PASSWORD_ISUSED_MSG = "取款密码格式有误";

	/** 账号不存在 */
	public static final Integer ACCOUNT_INVALID_ERRO = 5006;
	public static final String ACCOUNT_INVALID_ERRO_MSG = "账号不存在，请重新输入";

	/** 密码有误 */
	public static final Integer PASSWORD_INVALID_ERRO = 5007;
	public static final String PASSWORD_INVALID_ERRO_MSG = "密码有误,请重新输入";

	/** 用户密码获取失败 */
	public static final Integer USER_PASSWORD_ERRO = 5008;
	public static final String USER_PASSWORD_ERRO_MSG = "用户密码获取失败";

	/** IP为空 */
	public static final Integer IP_ISNULL_ERRO = 5009;
	public static final String IP_ISNULL_ERRO_MSG = "IP为空";

	/** 大厅注册失败 */
	public static final Integer HALL_LOGIN_ERRO = 5101;
	public static final String HALL_LOGIN_ERRO_MSG = "大厅注册失败";

	/** 密码错误超出限制 */
	public static final Integer ACCOUNT_INVALID_NUM = 5102;
	public static final String ACCOUNT_INVALID_NUM_MSG = "密码错误超出限制，请一小时后再试";

	/** 找不到该充值记录 */
	public static final Integer RECHARGE_RECORD_ISNULL = 5103;
	public static final String RECHARGE_RECORD_ISNULL_MSG = "充值记录获取";
	/** 该记录已充值 */
	public static final Integer RECHARGE_RECORD_REPEAT = 5104;
	public static final String RECHARGE_RECORD_REPEAT_MSG = "该记录已充值";

	/** 审核记录获取失败 */
	public static final Integer TAKE_EXAMINE_ISNULL = 5105;
	public static final String TAKE_EXAMINE_ISNULL_MSG = "审核记录获取失败";
	/** 交易记录获取失败 */
	public static final Integer TRANSACTION_RECORD_ISNULL = 5106;
	public static final String TRANSACTION_RECORD_ISNULL_MSG = "交易记录获取失败";
	
	/** 已有待审核取款记录 */
	public static final Integer TAKE_RECORD_ISNOTNULL = 5107;
	public static final String TAKE_RECORD_ISNOTNULL_MSG = "已有待审核取款记录";

}
