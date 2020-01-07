package com.xmsy.server.zxyy.webhome.common.utils;

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
		 * .用户真实姓名重复
		 *
		 */
		NICK_NAME_ERRO(5062, "昵称格式错误,请修改"),

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
		 * .用户手机验证码错误
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
		USER_VALIDATION_CODE_LIMIT_ERRO(5040, "两分钟内只能获取一个手机验证码"),
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
		USER_OAUTH_CODE_ERRO(5044, "第三方认证码错误"),
		/**
		 * 用户房卡数量不足
		 */
		USER_ROOMCARD_NOT_ENOUGH(5045, "用户房卡数量不足"),
		/**
		 * 购买金币请联系客服购买
		 */
		USER_BUY_COIN(5046, "购买金币请联系客服购买"),
		/**
		 * 购买未知道具异常
		 */
		USER_BUY_ERROR(5047, "购买未知道具异常"),
		/**
		 * 用户余额宝金额更新错误，请联系客服
		 */
		USER_BALANCE_ERROR(5048, "用户余额宝金额更新错误，请联系客服"),
		/**
		 * 修改用户余额金币异常
		 */
		USER_UPDATE_COIN_ERROR(5049, "更新用户余额金币异常"),
		/**
		 * 更新用户账户金额存取记录表异常
		 */
		USER_BALANCE_RECORD_ERROR(5050, "更新用户账户金额存取记录表异常"),
		/**
		 * 查询不到用户余额宝金额数据
		 */
		USER_BALANCE_NULL_ERROR(5051, "查询不到用户余额宝金额数据"),
		/**
		 * 用户未解锁该头像框
		 */
		USER_NOT_HEADFRAME_ERROR(5052, "用户未解锁该头像框"),
		/**
		 * 未查到用户账户金额收益记录数据
		 */
		USER_PROFIT_RECORD_NULL_ERROR(5053, "未查到用户账户金额收益记录数据"),
		/**
		 * 插入用户账户金额存取记录表异常
		 */
		USER_INSERT_BALANCE_RECORD_ERROR(5054, "插入用户账户金额存取记录表异常"),
		/**
		 * 未查到用户账户金额存取记录
		 */
		USER_SELECT_BALANCE_RECORD_ERROR(5055, "未查到用户账户金额存取记录"),
		/**
		 * 该头像框不存在
		 */
		HEADFRAME_ERROR(5056, "该头像框不存在"),

		/**
		 * 低于1元无法转入余额宝
		 */
		USER_BALANCE_NUMBER_ERROR(5057, "低于1元无法转入余额宝"),
		/**
		 *用户未绑定手机号码
		 */
		USER_UNBIND_PHONE_ERROR(5058, "用户未绑定手机号码"),
		/**
		 *用户已绑定第三方
		 */
		USER_BING_THIRD_ERROR(5059, "用户已绑定第三方账号"),
		/**
		 *第三方账号已绑定其他用户
		 */
		OTHER_USER_BING_THIRD_ERROR(5059, "第三方账号已绑定其他用户"),
		/**
		 * 用户未绑定银行卡，无法查看
		 */
		BANKCARD_NOT_BOUND(5060, "用户未绑定银行卡，无法查看"),

		/**
		 * 用户密保问题验证失败
		 */
		USER_ANSWER_ERROR(5061, "密保问题验证失败,请检查是否回答正确"),

		/**
		 * 请先升级为正式会员，再绑定真实姓名
		 */
		USER_TYPE_TOURIST_ERROR(5061, "请先升级为正式会员，再绑定真实姓名"),
		/**
		 * 低于1元无法转出余额宝
		 */
		USER_BALANCE_NUMBER_ERROROUT(5062, "低于1元无法转出余额宝"),
		/**
		 * 已经升级正式会员，请使用账号密码登录！
		 */
		USER_TYPE_LOGIN_ERROR(5063, "已经升级正式会员，请使用账号密码登录！");
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
		NOCHOICE_ACCOUNT_ERRO(6002, "未选择用户,或账号错误"),
		/**
		 * 消息id有误
		 */
		MESSAGE_ID_ERROR(6003, "消息id有误"),
		/**
		 * 没有可领取的邮件
		 */
		RECEIVE_MESSAGE_IS_NULL(6004, "没有可领取的邮件");

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
		MONEY_NOT_ENOUGH(7005, "您的金币余额不足或者打码量不够，目前无法申请提现!"),
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
		/**
		 * 查询取款类型有误
		 */
		TAKE_MONEY_TYPE_IS_ERRO(7016, "查询取款类型有误"),
		/**
		 * 查询充值类型有误
		 */
		RECHARGE_TYPE_IS_ERRO(7017, "查询充值类型有误");

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
		PASSWORD_NUM_ERRO(8006, "由于您输入多次错误密码导致会员账号被暂停使用，请联系24小时在线客服进行处理。"),
		/**
		 * 密码为空
		 */
		PASSWORD_ISNULL_ERRO(8007, "密码为空"),
		/**
		 * 取款密码为空
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
		SHARE_TO_ERRO(20002, "分享到哪为空"),
		/**
		 * 分享图片数据不存在，请先添加数据
		 */
		SHARE_PICTURE_ERROR(20003, "分享图片数据不存在，请先添加数据");

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
		USER_RECOMMENDATION_ISNULL(12003, "用户邀请信息为空"),
		/**
		 * 修改用户推荐码表异常
		 *
		 */
		UPDATE_CODE_ERROR(12004, "修改用户推荐码表异常"),
		/**
		 * 推荐码不存在
		 *
		 */
		RECOMMENDATION_CODE_ERROR(12005, "推荐码不存在"),
		/**
		 * 无法绑定自身
		 *
		 */
		RECOMMENDATION_USER_THEMSELVES_ERROR(12006, "无法绑定自身"),
		/**
		 * 推荐人代理关闭
		 *
		 */
		RECOMMENDATION_AGENT_CLOSE_ERROR(12007, "推荐人代理关闭"),
		/**
		 * 推荐人绑定失败
		 *
		 */
		RECOMMENDATION_USER_ERROR(12008, "绑定推荐人不能是自己的下级");

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
		AGENT_COIN_ERRO(15000, "代理金币奖励为空");

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
		USER_BLACK_ERRO(16002, "您的账号异常，请联系客服人员");

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
		GAMEHALL_IS_NULL(17003, "游戏大厅获取失败"),
		/**
		 * 服务器路径为空
		 */
		SERVICE_PATH_IS_NULL(17004, "服务器路径为空"),
		/**
		 *
		 * 服务器路径获取失败
		 */
		SERVICE_PATH_ERRO(17005, "服务器路径获取失败");

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

	// 开元游戏模块错误码
	public enum KyGameErrorCode {
		/**
		 * 游戏id不正确
		 */
		SERVER_IS_ERRO(17100, "服务器连接失败"),
		/**
		 * 游戏跳转路径获取失败
		 */
		GAMEURL_IS_NULL(17101, "游戏跳转路径获取失败"),
		/**
		 * 用户没有游戏权限
		 */
		GAMEROLE_IS_NULL(17102, "游客不能进入"),
		/**
		 * 游戏大厅获取失败
		 */
		GAMEHALL_IS_NULL(17103, "游戏大厅获取失败"),
		/**
		 * 服务器路径为空
		 */
		SERVICE_PATH_IS_NULL(17104, "服务器路径为空"),
		/**
		 *
		 * 服务器路径获取失败
		 */
		SERVICE_PATH_ERRO(17005, "服务器路径获取失败");

		private Integer code;
		private String errMsg;

		KyGameErrorCode(int code, String errMsg) {
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
		 * 已签到
		 */
		SIGN_ED(18000, "今日已签到");

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


	// 签到模块
	public enum LuckyErrorCode {


		/**
		 * 转盘出错
		 */
		LUCKY_INTEGRAL_ERRO(20001, "积分不足!"),
		/**
		 * 转盘出错
		 */
		LUCKY_CONFIG_ERRO(20000, "该转盘正在维护请选择其他转盘");

		private Integer code;
		private String errMsg;

		LuckyErrorCode(int code, String errMsg) {
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

	public enum UserTaskCode {
		/**
		 * 任务Id为空
		 *
		 */
		TASK_ERRO(18001, "任务Id为空"),

		/**
		 * 任务奖励已领取完
		 *
		 */
		ALREADY_RECIVE(18002, "任务奖励已领取完");

		private Integer code;
		private String errMsg;

		UserTaskCode(int code, String errMsg) {
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

	public enum WimHomePageCode {
		/**
		 * 推荐游戏获取失败
		 *
		 */
		GAME_OBTAIN_ERRO(60001, "推荐游戏获取失败");

		private Integer code;
		private String errMsg;

		WimHomePageCode(int code, String errMsg) {
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
	public enum ShopErrorCode {
		/**
		 * 兑换数量要大于0
		 */
		SHOP_EXCHANGE_ROOMCARD_ERROR(20101, "兑换数量要大于0"),
		/**
		 * 金币兑换房卡比例不存在
		 */
		SHOP_EXCHANGE_PROPORTION(20102, "金币兑换房卡比例不存在"),
		/**
		 * 礼包兑换码不存在
		 */
		GIFT_CONFIT_ERROR(20103, "兑换码不存在"),
		/**
		 * 兑换码已失效
		 */
		GIFT_EXCHANGE_CODE_LOSE_INVALID(20104, "兑换码已失效"),
		/**
		 * 兑换码未生效
		 */
		GIFT_EXCHANGE_CODE_EFFECT_INVALID(20105, "兑换码未生效"),
		/**
		 * 礼包兑换次数不足
		 */
		GIFT_EXCHANGE_NUM_ERROR(20106, "兑换次数不足"),
		/**
		 * 礼包兑换账户不匹配
		 */
		GIFT_EXCHANGE_ACCOUNT_ERROR(20107, "兑换账户不匹配"),
		/**
		 * 兑换密码错误
		 */
		GIFT_EXCHANGE_PASSWORD_ERROR(20108, "兑换密码错误"),
		/**
		 * 已兑换过该礼包
		 */
		GIFT_EXCHANGE_RECORD(20109, "已兑换过该礼包"),
		/**
		 * 该礼包兑换次数已满
		 */
		GIFT_EXCHANGE_NUM_RECORD(20110, "该礼包兑换次数已满"),
		/**
		 * 修改礼包配置失败
		 */
		UPDATE_GIFT_CONFIG_ERROR(20111, "修改礼包配置失败"),
		/**
		 * 添加礼包兑换记录失败
		 */
		INSERT_GIFT_EXCHANGE_RECORD(20112, "添加礼包兑换记录失败"),
		/**
		 * 已超出兑换时间
		 */
		GIFT_EXCHANGE_TIME_OUT_ERROR(20113, "已超出兑换时间");

		private Integer code;
		private String errMsg;

		ShopErrorCode(int code, String errMsg) {
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
	public enum SinaCode {
		/**
		 * 未开启转换链接功能
		 */
		EXCHANGE_ENABLE_ERROR(21000, "未开启转换链接功能"),
		/**
		 * 转换失败
		 */
		SINA_EXCHANGE_ERROR(21001, "转换失败");

		private Integer code;
		private String errMsg;

		SinaCode(int code, String errMsg) {
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
	public enum UserBalance {
		/**
		 * 该理财产品被禁用
		 */
		USER_BALANCE_PRODUCT_ERROR(21080, "该理财产品被禁用"),
		/**
		 * 请存入合理的金币范围
		 */
		USER_BALANCE_SWITH_COIN_ERROR(21081,"请存入合理的金币范围"),
		/**
		 * 剩余可买金额不足--已超过该理财产品限额
		 */
		USER_BALANCE_REMAIN_BUY_NUM_ERRO(21082,"已超过该理财产品限额"),
		/**
		 * 会员当天可购买次数不足
		 */
		USER_BALANCE_USER_TODAY_BUY_NUM_ERRO(21083,"会员当天可购买次数不足");

		private Integer code;
		private String errMsg;

		UserBalance(int code, String errMsg) {
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
	/**
	 * 用户红包模块
	 * @author Administrator
	 *
	 */
	public enum UserEnvelope {
		/**
		 * 超过活动时间，红包无法开启
		 */
		USER_FORTUNE_ACTIVITY_TIMEOUT_ERROR(21090, "超过活动时间，红包无法开启"),
		/**
		 * 该红包已失效，请打开其他红包
		 */
		USER_ENVELOPE_TASK_ERROR(21090, "该红包已失效，请打开其他红包"),
		/**
		 * 活动已结束
		 */
		USER_FORTUNE_ACTIVITY_ERROR(21091, "活动已结束");

		private Integer code;
		private String errMsg;

		UserEnvelope(int code, String errMsg) {
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
