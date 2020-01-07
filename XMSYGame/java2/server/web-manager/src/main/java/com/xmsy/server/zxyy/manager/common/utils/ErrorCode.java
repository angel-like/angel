package com.xmsy.server.zxyy.manager.common.utils;

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
		 * token验证失败 (拦截器)
		 */
		TOKEN_INVALID_LOSE(5100, "用户登入凭证失效，请重新登入"),
		/**
		 * token为空 (拦截器)
		 */
		TOKEN_IS_NULL(5101, "token为空"),
		/**
		 * 账号不存在
		 */
		USER_ACCOUNT_INVALID(5001, "账号不存在"),
		/**
		 * 用户获取失败
		 */
		USER_IS_NULL(5002, "用户获取失败"),
		/**
		 * 用户登陆记录获取失败 (获取用户登陆记录)
		 */
		USER_LOGIN_ERRO(5003, "用户登陆记录获取失败"),
		/**
		 * 第三方用户未绑定账号 (修改密码)
		 */
		USER_THIRD_ACCOUNT_ERRO(5004, "第三方用户未绑定账号"),
		/**
		 * 账号格式有误
		 * 
		 */
		USER_ACCOUNT_ERRO(5005, "账号格式有误"),
		/**
		 * 试玩账号不允许进行该操作
		 * 
		 */
		USER_TRIAL_ERRO(5006, "试玩账号不允许进行该操作"),
		/**
		 * 用户已存在
		 * 
		 */
		USER_ACCOUNT_ISNOTNULL_ERRO(5007, "账号已存在，请重新填写"),
		/**
		 * 试玩账号已达上限
		 * 
		 */
		TRIAL_ACCOUNT_ISNULL_ERRO(5008, "试玩账号已达上限"),
		/**
		 * 试玩配置为空
		 * 
		 */
		TRIAL_CONFIG_ISNULL_ERRO(5009, "试玩配置为空"),
		/**
		 * IP为空
		 * 
		 */
		USER_IP_ISNULL_ERRO(5010, "IP为空"),
		/**
		 * 用户信息不允许多次修改
		 */
		USER_INFO_ISNOTNULL_ERRO(5011, "用户信息不允许多次修改"),
		/**
		 * 额度不足：账号余额小于提款金额
		 */
		COMMISSION_NOT_ENOUGH(5012, "账户佣金额度不足"),
		/**
		 * 转换佣金为空
		 */
		COMMISSION_INVALID(5013, "转换佣金必须大于0"),
		/**
		 * 用户金币不足
		 */
		USER_COIN_NOT_ENOUGH(5014, "用户金币不足"),
		/**
		 * 用户真实姓名不能重复修改
		 */
		USERNAME_CANNOT_UPDATE(5015, "用户真实姓名不能修改，请联系客服"),
		/**
		 * 银行卡不能修改
		 */
		BANKCARD_CANNOT_UPDATE(5016, "用户银行卡不能修改，请联系客服"),
		/**
		 * 支付宝账号不能修改
		 */
		ALIPAY_CANNOT_UPDATE(5017, "支付宝账号不能修改，请联系客服"),
		/**
		 * 请先完善用户真实姓名
		 */
		USERNAME_IS_NULL(5018, "请先完善用户真实姓名"),
		/**
		 * 用户真实姓名格式有误
		 * 
		 */
		USER_NAME_ERRO(5019, "真实姓名：字母和汉字组合，2-20个字符"),
		/**
		 * 银行卡号格式错误
		 * 
		 */
		BANK_CARD_ERRO(5020, "银行卡号16-19位数字"),
		/**
		 * 支付宝账号格式有误
		 * 
		 */
		ALIPAY_ACCOUNT_ERRO(5021, "支付宝号只允许手机或邮箱"),
		/**
		 * 头像格式不正确
		 * 
		 */
		HEAD_ERRO(5022, "头像格式不正确"),
		/**
		 * 额度不足：账号余额小于提款金额
		 */
		MONEY_NOT_ENOUGH(5023, "额度不足"),
		/**
		 * 金币不足
		 */
		COIN_NOT_ENOUGH(5024, "金币不足"),
		/**
		 * 游戏中不能进行该操作
		 * 
		 */
		PLAYING_GAME_LIMIT_ERRO(5025, "游戏中不能进行该操作"),
		/**
		 * 修改取款密码请联系客服
		 * 
		 */
		USER_PASSWORD_UNMODIFY_ERRO(5026, "修改取款密码请联系客服"),
		/**
		 * 修改取款密码请联系客服
		 * 
		 */
		USER_CONFIRM_PASSWORD_ERRO(5027, "两次输入密码不一致"),
		/**
		 * 取款密码6-10个数字和字母组合
		 * 
		 */
		USER_WALLET_PASSWORD_ERRO(5028, "取款密码6-10个数字和字母组合"),

		/**
		 * 登录取得大厅ip失败
		 * 
		 */
		USER_LOGIN_GET_HALL_IP_ERRO(5029, "登录取得大厅ip失败"),
		/**
		 * 网络繁忙
		 * 
		 */
		HALL_REQUEST_ERRO(5030, "网络繁忙请重试"),
		/**
		 * .账户被冻结
		 * 
		 */
		USER_FROZEN_ERRO(5031, "账户被冻结"),
		/**
		 * .账户被禁止下注
		 * 
		 */
		USER_NOBET_ERRO(5032, "账户被禁止下注"),
		/**
		 * .账户被禁用
		 * 
		 */
		USER_FORBIDDEN_ERRO(5033, "账户被禁用"),

		/**
		 * .用户真实姓名重复
		 * 
		 */
		USER_NAME_REPEAT_ERRO(5034, "真实姓名重名，请重新填写或联系客服"),

		/**
		 * .用户同一个ip注册数量现在5个
		 * 
		 */
		USER_IP_LIMIT_ERRO(5035, "一个ip只能注册5个账号"),
		/**
		 * .一个设备只能注册2个账号
		 * 
		 */
		USER_DEVICE_CODE_LIMIT_ERRO(5036, "一个设备只能注册2个账号"),
		/**
		 * .一个设备只能注册2个账号
		 * 
		 */
		USER_VALIDATION_CODE_ERRO(5037, "用户手机验证码错误"),
		/**
		 * 银行卡已经被绑定
		 */
		BANKCARD_REPEAT_UPDATE(5038, "该银行卡已经被绑定"),
		/**
		 * 账号格式有误
		 * 
		 */
		USER_PHONE_ERRO(5039, "手机号格式错误"),
		/**
		 * 一分钟获取一个手机验证吗
		 * 
		 */
		USER_VALIDATION_CODE_LIMIT_ERRO(5040, "一分钟内只能获取一个手机验证码"),
		/**
		 * 用户注册类型错误
		 * 
		 */
		USER_REGISTER_TYPE_ERRO(5041, "用户注册类型错误"),
		/**
		 * 手机号已经被绑定
		 */
		PHONE_ALREADY_BIND_ERRO(5042, "该手机号已经被绑定"),
		/**
		 * 用户已经绑定手机号
		 * 
		 */
		USER_PHONE_ALREADY_BIND_ERRO(5043, "用户已经绑定手机，请联系客服解绑"),
		/**
		 * 用户注册类型错误
		 * 
		 */
		USER_OAUTH_CODE_ERRO(5044, "第三方认证码错误");

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

	// 消息模块错误码
	public enum MessagemanagementErrorCode {
		/**
		 * 保存失败
		 */
		SAVE_IS_ERRO(6001, "保存失败"),
		/**
		 * 未选择用户
		 */
		NOCHOICE_ACCOUNT_ERRO(6002, "未选择用户,或账号错误");

		private Integer code;
		private String errMsg;

		MessagemanagementErrorCode(int code, String errMsg) {
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

	// 充值取现模块错误码
	public enum OrderErrorCode {
		/**
		 * 充值记录获取失败
		 */
		RECHARGE_RECORD_ISNULL(7000, "充值记录获取失败"),
		/**
		 * 用户银行信息不完善
		 */
		USER_BANK_IS_NULL(7001, "用户银行信息不完善"),
		/**
		 * 已有待审核取款记录
		 */
		TAKE_RECORD_ISNOTNULL(7002, "已有待审核取款记录"),
		/**
		 * 待审核记录为空
		 */
		TAKE_EXAMINE_IS_NULL(7003, "稽查审核记录为空"),
		/**
		 * 额度不足：提款余额小于预扣的行政费
		 */
		TAKE_MONEY_NOT_ENOUGH(7004, "额度不足：提款余额小于预扣的行政费"),
		/**
		 * 额度不足：账号余额小于提款金额
		 */
		MONEY_NOT_ENOUGH(7005, "额度不足：查询金币是否归集，以及账户余额"),
		/**
		 * 取款稽查订单为空
		 */
		EXAMINE_IS_NULL(7006, "取款稽查订单为空"),
		/**
		 * 银行不正确
		 */
		BANK_ISNULL_ERRO(7007, "银行不正确"),
		/**
		 * 已存在待充值记录
		 */
		RECHARGE_RECORD_ISNOTNULL_ERRO(7008, "已存在待充值记录"),
		/**
		 * 稽查记录未审核
		 */
		TAKE_EXAMINE_UNCONFIRMED(7009, "稽查记录未审核"),
		/**
		 * 收款渠道不正确
		 */
		RECEIPT_CHANNEL_ERR(7010, "收款渠道不正确"),
		/**
		 * 用户支付宝信息不完善
		 */
		USER_ALIPAY_IS_NULL(7011, "用户支付宝信息不完善"),
		/**
		 * 支付公司错误
		 */
		PAY_COMPANY_ERR(7012, "该支付公司不存在"),
		/**
		 * 存款类型不正确
		 */
		RECHARGE_CHANNEL_ERR(7013, "存款类型不正确"),
		/**
		 * 1800游戏币盈利需充值10元以上成为正式会员方可提款
		 */
		RECHARGE_IS_NULL(7014, "1800游戏币盈利需充值10元以上成为正式会员方可提款"),
		/**
		 * 金币不足
		 */
		COIN_NOT_ENOUGH(7015, "金币不足"),
		RECHARGE_RECORD_ISNOTNULL_GM_ERRO(7008, "已存在充值记录,不能标记为测试账号")
				;

		private Integer code;
		private String errMsg;

		OrderErrorCode(int code, String errMsg) {
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

	// 密码模块错误码
	public enum PasswordErrorCode {
		/**
		 * 密码有误,请重新输入
		 */
		PASSWORD_INVALID_ERRO(8000, "密码有误,请重新输入"),
		/**
		 * 新旧密码不可重复 (修改密码)
		 */
		USER_PASSWORD_ERRO(8001, "新旧密码不可重复"),
		/**
		 * 前后密码不一致
		 */
		PASSWORD_ISUSED_ERRO(8002, "前后密码不一致"),
		/**
		 * 登陆密码格式有误
		 */
		LOGIN_PASSWORD_ERRO(8003, "登陆密码格式有误"),
		/**
		 * 取款密码格式有误
		 */
		TAKE_PASSWORD_ERRO(8004, "取款密码格式有误"),
		/**
		 * 用户密码获取失败
		 */
		USER_PASSWORD_ISNULL_ERRO(8005, "用户不存在，或登入方式错误"),
		/**
		 * 密码错误超过10次
		 */
		PASSWORD_NUM_ERRO(8006, "密码错误超过10次,请等待2小时或联系客服修改密码"),
		/**
		 * 密码为空
		 */
		PASSWORD_ISNULL_ERRO(8007, "密码为空"),
		/**
		 * 银行密码为空
		 */
		BANK_PASSWORD_ISNULL_ERRO(8008, "取款密码为空");

		private Integer code;
		private String errMsg;

		PasswordErrorCode(int code, String errMsg) {
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

	// 奖金模块错误码
	public enum PoolDispatchErrorCode {
		/**
		 * 派发任务不存在
		 */
		POOLDISPATCHTASKISNOTFIND_ERRO(9000, "派发任务不存在"),
		/**
		 * 奖金池总额不足
		 */
		INSUFFICIENT_AMOUNT_OF_BONUS_POOL_ERRO(9001, "奖金池总额不足"),
		/**
		 * 奖项设置未设置
		 */
		AWARD_SETTINGS_NOT_SET_ERRO(9002, "奖项设置未设置"),
		/**
		 * 设置的中奖人数超出可派发的人数
		 */
		SETTING_WIN_NUM_OVERFLOWT_ERRO(9003, "设置的中奖人数超出可派发的人数"),
		/**
		 * 派奖的用户id不存在
		 */
		USER_ID_NOT_EXIST_ERRO(9004, "派奖的用户id不存在"),
		/**
		 * 实际派发的奖金超出预设派发的金额
		 */
		ACTUAL_GOLD_GO_BEYOND_DEFAULT_AMOUNT_ERRO(9005, "实际派发的奖金超出预设派发的金额"),
		/**
		 * 实际派发的奖金小于预设派发的金额
		 */
		ACTUAL_GOLD_LESS_THAN_DEFAULT_AMOUNT_ERRO(9006, "实际派发的奖金小于预设派发的金额");

		private Integer code;
		private String errMsg;

		PoolDispatchErrorCode(int code, String errMsg) {
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

	// 层级模块错误码(用户层级,代理层级)
	public enum HierarchyErrorCode {
		/**
		 * 用户层级获取失败
		 * 
		 */
		HIERARCHY_ISNULL_ERRO(10000, "用户层级获取失败"),
		/**
		 * 默认代理等级为空
		 * 
		 */
		AGENTHIERARCHY_ISNULL_ERRO(10001, "默认代理等级为空");

		private Integer code;
		private String errMsg;

		HierarchyErrorCode(int code, String errMsg) {
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


	// 操作打码量
	public enum CashErrorCode {

		/**
		 * 默认代理等级为空
		 *
		 */
		CASH_REDUCE_ERRO(80999, "减少打码量不能大于用户打码量");

		private Integer code;
		private String errMsg;

		CashErrorCode(int code, String errMsg) {
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

	// 分享错误码
	public enum ShareErrorCode {
		/**
		 * 客户端类型为空
		 * 
		 */
		CLIENT_TYPE_ERRO(20001, "客户端类型为空"),
		/**
		 * 分享到哪为空
		 * 
		 */
		SHARE_TO_ERRO(20002, "分享到哪为空");

		private Integer code;
		private String errMsg;

		ShareErrorCode(int code, String errMsg) {
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

	// OTP模块错误码
	public enum OtpErrorCode {
		/**
		 * OTP账号已经存在，一个用户只允许一个OTP账号
		 * 
		 */
		OTP_EXIST_ERRO(11000, "OTP账号已经存在，一个用户只允许一个OTP账号"),
		/**
		 * OTP格式错误
		 * 
		 */
		OTP_FORMAT_ERRO(11001, "OTP格式错误"),
		/**
		 * 未绑定OTP密匙
		 * 
		 */
		OTP_KEYS_UNBOUND_ERRO(11002, "未绑定OTP"),
		/**
		 * Ip未在绑定OTP密匙Ip之列
		 * 
		 */
		IP_NOT_BOUND_OTP_KEYS_ERRO(11003, "Ip未在绑定OTP密匙Ip之列"),
		/**
		 * OTP验证失败
		 * 
		 */
		OTP_VALIDATION_FAILURE_ERRO(11004, "OTP验证码不正确"),
		/**
		 * OTPip未绑定
		 * 
		 */
		OTP_IP_UNBOUND_ERRO(11005, "用户OTP未绑定IP");

		private Integer code;
		private String errMsg;

		OtpErrorCode(int code, String errMsg) {
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

	// 邀请模块错误码(验证码)
	public enum InvitationCodeErrorCode {
		/**
		 * 验证码已失效
		 * 
		 */
		RECOMMENDATION_CODE_ISNULL(12000, "验证码已失效"),
		/**
		 * 邀请码有误
		 * 
		 */
		RECOMMENDATION_CODE_VERIFICATION_ERRO(12001, "邀请码有误"),
		/**
		 * 邀请码已经存在
		 * 
		 */
		INVITATION_CODE_ALREADY_EXISTS_ERRO(12002, "邀请码已经存在"),
		/**
		 * 用户邀请信息为空
		 * 
		 */
		USER_RECOMMENDATION_ISNULL(12003, "用户邀请信息为空");

		private Integer code;
		private String errMsg;

		InvitationCodeErrorCode(int code, String errMsg) {
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

	// 附件模块错误码
	public enum EnclosureErrorCode {
		/**
		 * 图片路径获取失败
		 * 
		 */
		ENCLOSURE_ISNULL_ERRO(13000, "图片路径获取失败");

		private Integer code;
		private String errMsg;

		EnclosureErrorCode(int code, String errMsg) {
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

	// 官网配置错误码
	public enum WebConfigErrorCode {
		/**
		 * 跳转类型为空
		 * 
		 */
		URLTYPE_ISNULL_ERRO(14000, "跳转类型为空"),
		/**
		 * url为空
		 * 
		 */
		URL_ISNULL_ERRO(14001, "url为空"),
		/**
		 * 跳转类型有误
		 * 
		 */
		URLTYPE_IS_ERRO(14002, "跳转类型有误");

		private Integer code;
		private String errMsg;

		WebConfigErrorCode(int code, String errMsg) {
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

	// 代理错误码
	public enum AgentErrorCode {
		/**
		 * 代理金币奖励为空
		 * 
		 */
		AGENT_COIN_ERRO(15000, "代理金币奖励为空"),
		/**
		 * 非代理商请勿进行此操作
		 * 
		 */
		AGENT_ID_ERRO(15001, "非代理商请勿进行此操作"),
		/**
		 * 余额不足，请先充值
		 * 
		 */
		AGENT_COIN_NOT_ENOUGH(15002, "余额不足，请先充值"),
		/**
		 * 权限不足，请联系系统管理员为你充值
		 * 
		 */
		AGENT_NO_AUTHOTITY_ERRO(15003, "权限不足，请联系系统管理员为你创建订单");

		private Integer code;
		private String errMsg;

		AgentErrorCode(int code, String errMsg) {
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

	// 黑名单错误码
	public enum BlackListErrorCode {
		/**
		 * 获取用户黑名单失败
		 * 
		 */
		BLACKLIST_ISNULL_ERRO(16000, "获取用户黑名单失败"),
		/**
		 * IP已被拉黑
		 * 
		 */
		IP_BLACK_ERRO(16001, "IP已被禁用,请联系客服人员"),
		/**
		 * 用户已被拉黑
		 * 
		 */
		USER_BLACK_ERRO(16002, "用户已被冻结，详情请联系客服人员");

		private Integer code;
		private String errMsg;

		BlackListErrorCode(int code, String errMsg) {
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

	// 游戏模块错误码
	public enum GameErrorCode {
		/**
		 * 游戏id不正确
		 */
		GAMEID_IS_ERRO(17000, "游戏id不正确"),
		/**
		 * 游戏跳转路径获取失败
		 */
		GAMEURL_IS_NULL(17001, "游戏跳转路径获取失败"),
		/**
		 * 用户没有游戏权限
		 */
		GAMEROLE_IS_NULL(17002, "用户没有游戏权限"),
		/**
		 * 游戏大厅获取失败
		 */
		GAMEHALL_IS_NULL(17003, "游戏大厅获取失败");

		private Integer code;
		private String errMsg;

		GameErrorCode(int code, String errMsg) {
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

	// 支付模块错误码
	public enum PayErrorCode {
		/**
		 * 支付公司不正确
		 */
		PAY_COMPANY_ERRO(18000, "没有该支付公司"),
		/**
		 * 支付渠道不正确
		 */
		PAY_CHANNEL_ERRO(18001, "支付公司没有该渠道"),

		/**
		 * 该公司未开放该支付渠道
		 */
		PAY_COMPANY_CHANNEL_ERRO(18002, "该公司未开放该支付渠道"),

		/**
		 * 该公司未开放该支付渠道
		 */
		PAY_CHANNEL_AMOUNT_ERROR(18003, "支付渠道金额不正确"),

		/**
		 * 支付渠道重复
		 */
		PAY_CHANNEL_REPEAT_ERROR(18004, "支付渠道重复");

		private Integer code;
		private String errMsg;

		PayErrorCode(int code, String errMsg) {
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

	// 签到模块
	public enum SignErrorCode {
		/**
		 * 签到配置出错
		 */
		SIGN_CONFIG_ERRO(19000, "签到配置出错"),
		/**
		 * 请输入查询时间
		 */
		TIME_ERRO(19001, "请输入查询时间"),
		/**
		 * 最多查询一个月的信息
		 */
		TIMEOUT_ERRO(19002, "最多查询一个月的信息");

		private Integer code;
		private String errMsg;

		SignErrorCode(int code, String errMsg) {
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

	// 用户活动模块
	public enum UserActivityCode {
		/**
		 * 消息id为空
		 */
		MESSAGE_ID_ERRO(30001, "消息Id为空");

		private Integer code;
		private String errMsg;

		UserActivityCode(int code, String errMsg) {
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

	public enum ThirdPartyErrorCode {
		/**
		 * 短信服务异常
		 * 
		 */
		SMS_NETWORK_ERRO(19001, "网络异常请重试");

		private Integer code;
		private String errMsg;

		ThirdPartyErrorCode(int code, String errMsg) {
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
	//文件上传模块异常
	public enum FileUploadErrorCode {
		/**
		 * p12、profile、密码全为空或者全不为空
		 * 
		 */
		PARAM_ERRO(20505, "p12、profile、密码全为空或者全不为空"),
		/**
		 * 打包错误，请检查或者联系后台管理员
		 * 
		 */
		DO_PACKAGE_ERRO(20505, "打包错误，请检查或者联系后台管理员"),
		/**
		 * 获取第三方接口数据列表异常
		 * 
		 */
		GET_PACKAGE_ERRO(20506, "获取第三方接口数据列表异常"),
		/**
		 * 微信分享下载错误，请检查或者联系后台管理员
		 * 
		 */
		SHARE_WEIXINURL_ERRO(20507, "微信分享下载错误，请检查重试或者联系后台管理员");

		private Integer code;
		private String errMsg;

		FileUploadErrorCode(int code, String errMsg) {
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
