package com.xmsy.server.zxyy.webhome.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 常量
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {

	// 配置文件
	public static final ResourceBundle RESOURCE = ResourceBundle.getBundle("definition");
	/** 中奖站内信标题 */
	public static final String WINNING_MAIL_TITLE = "中奖通知:恭喜您获得%s";
	/** 中奖站内信内容 */
	public static final String WINNING_MAIL_CONTENT = "恭喜您于%s获得%s,金币%d";
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	
	//对局要求confrontation
	public static final int CONFRONTATION_1 = 1;//胜局
	public static final int CONFRONTATION_2 = 2;//输局
	public static final int CONFRONTATION_3 = 3;//对局

	/** 超级管理员角色ID */
	public static final Long SUPER_ROLE = 1L;
	/** 财务角色ID */
	public static final Long FINANCE_ROLE = 2L;
	/** 代理商角色ID */
	public static final Long AGENCY_ROLE = 3L;
	/** 普通角色ID */
	public static final Long COMMON_ROLE = 4L;

	/** 客户端传过来的金比例 */
	public static final long CLIENT_COIN_RATE = Long.valueOf(RESOURCE.getString("client-coin-exchange"));
	/** 金币和钱的比例的10000：1 */
	public static final long COIN_RATE = Long.valueOf(RESOURCE.getString("sys-coin-exchange"));
	/** 数据库存取金币比例100：1 */
	public static final long DB_COIN_RATE = 100;

	/** 系统字段表：银行标识 */
	public static final String CODE = "001";
	/** 系统字段表：存款方式标识 */
	public static final String DEPOSITMETHODCODE = "DepositMethod";

	/** 系统字段表：收款二维码标识 */
	public static final String QR_TYPE = "qrcodeType";

	/** 数字默认值 */
	public static final int DEFAULT = 0;
	/** 7天 */
	public static final int QUERY_DAY = 7;
	/** 一个ip只能注册5个账号 */
	public static final int REGISTER_IP_LIMIT = 5;
	/** 一个设备只能注册2个账号 */
	public static final int USER_DEVICE_CODE_LIMIT = 2;
	/** 默认支付id，众享支付 */
	public static final Long DEFAULT_PAY = 1000L;
	/** 众享支付 */
	public static final int PAYTYPE1 = 1;
	/** 快捷支付 */
	public static final int PAYTYPE2 = 2;
	/** 在线充值 */
	public static final int PAYTYPE3 = 3;
	/** VIP充值 */
	public static final int PAYTYPE4 = 4;
	/** 微信充值 */
	public static final int PAYTYPE5 = 5;
	/** 支付宝充值 */
	public static final int PAYTYPE6 = 6;

	/** 活动类型 1：奖励金 */
	public static final int ACTIVITYTYPE_1 = 1;
	/** 发送模式 0：后台发放 1：客户领取 */
	public static final int SENDTYPE_0 = 0;
	/** 发送模式 0：后台发放 1：客户领取 */
	public static final int SENDTYPE_1 = 1;
	/** 金币道具id */
	public static final Long COIN_PROP_ID = 1l;
	/** 房卡道具id */
	public static final Long ROOMCART_PROP_ID = 2l;
	/**
	 * 商城道具交易记录-类型0：购买 1：使用 2赠送3:抽奖
	 */
	public static final int PROP_USE_TYPE_1 = 1;
	/**
	 * 商城道具交易记录-类型0：购买 1：使用 2赠送3:抽奖
	 */
	public static final int PROP_USE_TYPE_0 = 0;
	/**
	 * 商城道具交易记录-类型0：购买 1：使用 2赠送 3:抽奖
	 */
	public static final int PROP_USE_TYPE_2 = 2;
	public static final int PROP_USE_TYPE_3 = 3;
	/**
	 * 菜单类型
	 * 
	 * @author aleng
	 * @email xxxxxx
	 * @date 2016年11月15日 下午1:24:29
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(0),
		/**
		 * 菜单
		 */
		MENU(1),
		/**
		 * 按钮
		 */
		BUTTON(2);

		private int value;

		MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 定时任务状态
	 * 
	 * @author aleng
	 * @email xxxxxx
	 * @date 2016年12月3日 上午12:07:22
	 */
	public enum ScheduleStatus {
		/**
		 * 正常
		 */
		NORMAL(0),
		/**
		 * 暂停
		 */
		PAUSE(1);

		private int value;

		ScheduleStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 云服务商
	 */
	public enum CloudService {
		/**
		 * 七牛云
		 */
		QINIU(1),
		/**
		 * 阿里云
		 */
		ALIYUN(2),
		/**
		 * 腾讯云
		 */
		QCLOUD(3);

		private int value;

		CloudService(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 充值类型
	 */
	public enum RechargeType {
		/**
		 * 银行卡充值
		 */
		BANK(3, "银行卡充值"),
		/**
		 * 第三方充值
		 */
		THIRD(2, "第三方充值"),
		/**
		 * 人工充值
		 */
		ADMIN(1, "人工充值");

		private int value;
		private String name;

		RechargeType(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 奖励类型
	 */
	public enum RewardType {
		/**
		 * 实名返利
		 */
		REAL_NAME(1);

		private int value;

		RewardType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 人工充值对象
	 */
	public enum AdministratorRechargeType {
		/**
		 * 用户
		 */
		USER(1, "用户"),
		/**
		 * 层级
		 */
		HIERARCHY(2, "层级");

		AdministratorRechargeType(int value, String name) {
			this.value = value;
			this.name = name;
		}

		private String name;
		private int value;

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 订单状态
	 */
	public enum OrderStatus {
		/**
		 * 未确认
		 */
		UNCONFIRMED(0, "未确认"),
		/**
		 * 订单取消
		 */
		CANCEL(1, "订单取消"),
		/**
		 * 订单完成
		 */
		COMPLETE(2, "订单完成"),
		/**
		 * 订单撤销
		 */
		REVOKE(3, "订单撤销"),
		/**
		 * 订单锁定
		 */
		LOCKING(4, "订单锁定");

		private int value;
		private String name;

		private static final Map<Integer, String> orderStatusMap = Maps.newConcurrentMap();

		static {
			for (OrderStatus orderStatus : OrderStatus.values()) {
				orderStatusMap.put(orderStatus.getValue(), orderStatus.getName());
			}
		}

		OrderStatus(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static Map<Integer, String> getOrderstatusmap() {
			return orderStatusMap;
		}

	}

	/**
	 * 交易类型
	 */
	public enum TransactionType {
		/**
		 * 存款
		 */
		RECHARGE(0),
		/**
		 * 取款
		 */
		TAKE(1),
		/**
		 * 冲销
		 */
		WRITEOFF(2),
		/**
		 * 返利
		 */
		REBATE(3),
		/**
		 * 派奖
		 */
		AWARD(4),
		/**
		 * 额度转换
		 */
		CHANGER(5),
		/**
		 * 归集
		 */
		INTEGRATE(6),
		// /**
		// * 额度转换
		// */
		// EXCHANGE(7),
		/**
		 * 取款回退
		 */
		TAKE_RETURN(8),
		/**
		 * 撤销充值
		 */
		RECHARGE_RETURN(9),
		
		/**
		 * 任务领取
		 */
		TASK_RECEIVE(10),
		/**
		 * 余额宝
		 */
		YUEBAO(11),
		/**
		 * 划拨
		 */
		TRANSFER(12),
		/**
		 * 邮件领取
		 */
		MESSAGERECEIVE(13),
		/**
		 * 抽奖
		 */
		LUCKY(14),

		/**
		 * 开元游戏上分
		 */
		FREEZE(15),

		/**
		 * 开元游戏下分
		 */
		UNFREEZE(16),
		/**
		 * 红包领取
		 */
		ENVELOPETASK(17);


		private int value;

		TransactionType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 线下银行存款类型
	 */
	public enum RechargeBankType {
		/**
		 * 网银转账
		 */
		RECHARGE_BANK_UNION(0, "网银转账"),
		/**
		 * ATM转账
		 */
		RECHARGE_BANK_ATM(1, "ATM现金存款"),
		/**
		 * 中国银行
		 */
		ZHONGGUO_BANK_COUNTER(2, "中国银行"),
		/**
		 * 招商银行
		 */
		ZHAOSHANG_BANK_COUNTER(3, "招商银行"),
		/**
		 * 建设银行
		 */
		JIANSHE_BANK_COUNTER(4, "建设银行"),
		/**
		 * 交通银行
		 */
		JIAOTONG_BANK_COUNTER(5, "交通银行"),
		/**
		 * 农业银行
		 */
		NONGYE_BANK_COUNTER(6, "农业银行"),
		/**
		 * 兴业银行
		 */
		XINGYE_BANK_COUNTER(7, "兴业银行"),
		/**
		 * 华夏银行
		 */
		HUAXIA_BANK_COUNTER(8, "华夏银行"),
		/**
		 * 邮政银行
		 */
		YOUZHEN_BANK_COUNTER(9, "邮政银行"),
		/**
		 * 农村信用社
		 */
		NCXYS_BANK_COUNTER(10, "农村信用社"),
		/**
		 * 中国民生银行
		 */
		MINGSHENG_BANK_COUNTER(11, "民生银行"),
		/**
		 * 微信
		 */
		WEIXIN(13, "微信"),
		/**
		 * 支付宝
		 */
		ALIPAY(12, "支付宝");
		
		private static final List<Map<String, Object>> lookup = Lists.newArrayList();
		private static final Map<Integer, String> bankTypeMap = Maps.newConcurrentMap();

		static {
			Map<String, Object> rechargeBankTypeMap = null;
			for (RechargeBankType rechargeBankType : RechargeBankType.values()) {
				if (rechargeBankType == WEIXIN || rechargeBankType == ALIPAY) {
					continue;
				}
				rechargeBankTypeMap = new HashMap<String, Object>();
				rechargeBankTypeMap.put("id", rechargeBankType.getValue());
				rechargeBankTypeMap.put("name", rechargeBankType.getName());
				bankTypeMap.put(rechargeBankType.getValue(), rechargeBankType.getName());
				lookup.add(rechargeBankTypeMap);
			}
		}

		private Integer value;
		private String name;

		RechargeBankType(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static List<Map<String, Object>> getLookup() {
			return lookup;
		}

		public static Map<Integer, String> getBanktypemap() {
			return bankTypeMap;
		}
	}

	/**
	 * 交易具体类型
	 */
	public enum TransactionDetailType {
		/**
		 * 银行充值
		 */
		BANKRECHARGE(11, "银行充值"),
		/**
		 * 支付宝充值
		 */
		ALIPAYRECHARGE(12, "支付宝充值"),
		/**
		 * 微信充值
		 */
		WECHATRECHARGE(13, "微信充值"),
		/**
		 * 人工充值
		 */
		ARTIFICIALRECHARGE(14, "人工充值"),
		/**
		 * 佣金取款
		 */
		COMMISSIONTAKE(15, "佣金取款"),
		/**
		 * 账户取款
		 */
		ACCOUNTTAKE(16, "账户取款"),
		/**
		 * 额度转换
		 */
		QUOTACONVERSION(17, "额度转换"),
		/**
		 * 签到返利
		 */
		CHECKINREBATE(18, "签到返利"),
		/**
		 * 推荐返利
		 */
		RECOMMENDEDREBATE(19, "推荐返利"),
		/**
		 * 奖池派奖
		 */
		PRIZEPOOLAWARD(20, "奖池派奖"),

		/**
		 * 资金归集
		 */
		INTEGRATE(21, "资金归集"),
		/**
		 * 取消取款订单
		 */
		TAKE_MONEY_CANCEL(22, "取消取款订单"),
		/**
		 * 佣金转金币
		 */
		EXCHANGE(23, "佣金转金币"),
		/**
		 * 实名返利
		 */
		USERINFO(24, "实名返利"),
		/**
		 * 取消佣金取现
		 */
		TAKE_COMMISSION_CANCEL(25, "取消佣金取现"),
		/**
		 * 游戏返利
		 */
		GAMEREBATE(26, "游戏返利"),
		/**
		 * 撤销充值
		 */
		REVOKE_RECHARGE(27, "撤销充值"),
		/**
		 * 手续费返还
		 */
		FEE_REFUND(28, "手续费返还"),
		/**
		 * QQ充值
		 */
		QQRECHARGE(29, "QQ充值"),
		/**
		 * 京东充值
		 */
		JINGDONGRECHARGE(30, "京东充值"),
		/**
		 * 快捷充值
		 */
		QUICKRECHARGE(31, "快捷充值"),
		/**
		 * 银联充值
		 */
		UNIONRECHARGE(32, "银联充值"),
		/**
		 * 会员下注返水
		 */
		USERRETURNWATER(33, "会员下注返水"),
		/**
		 * 代理商下注返佣
		 */
		AGENTSBETCOMMISSION(34, "代理商下注返佣"),
		/**
		 * 代理商充值返返佣
		 */
		AGENTSRECHARGECOMMISSION(35, "代理商充值返返佣"),
		/**
		 * 资金归集
		 */
		COINEXCHAGE(36, "金币转余额"),
		/**
		 * 用户充值代理商返佣撤销
		 */
		AGENTSRECHARGECOMMISSIONCANCEL(37, "用户充值代理商返佣撤销"),
		/**
		 * 对局奖励
		 */
		USERMATCHAWARD(38, "用户对局奖励"),
		/**
		 * 用户分享App奖励
		 */
		USERSHARE(39, "用户分享App奖励"),
		/**
		 * 用户救济金
		 */
		USERRELIEF(40, "用户救济金"),
		/**
		 * 用户鼓励金
		 */
		USERENCOURAGE(41, "用户鼓励金"),
		/**
		 * 人工取款
		 */
		ADMINTAKE(42, "人工取款"),
		
		/**
		 * 任务领取
		 */
		TASKRECEIVE(43, "任务领取"),
		/**
		 * 余额宝转入
		 */
		YUEBAOSWITCHINTO(44, "余额宝转入"),

		KAIYUANLOGIN(51, "游戏带入"),
		KAIYUANLOGOUT(52, "开元游戏带出"),
		/**
		 * 余额宝转出
		 */
		YUEBAOSWITCHOUT(45, "余额宝转出"),
		/**
		 * 兑换码兑换
		 */
		GIFTEXCHANGE(46, "兑换码兑换"),
		/**
		 * 代理商划拨
		 */
		PROXY_TRANSFER(47, "代理商划拨"),
		/**
		 * 邮件领取
		 */
		MESSAGE_RECEIVE(48, "邮件领取"),
		/**
		 * 邮件领取
		 */
		LUCKY_RECEIVE(49, "幸运抽奖(单次)"),
		/**
		 * 邮件领取
		 */
		LUCKY_RECEIVE_TEN(53, "幸运十连抽"),
		/**
		 * 单个红包领取
		 */
		ONE_ENVELOPE_RECEIVE(50, "单个红包领取"),
		/**
		 * 多个红包领取
		 */
		MANY_ENVELOPE_RECEIVE(51, "多个红包领取"),
		
		//==========================1000以上的预留支付用==================
		/**
		 * 银联云闪付
		 */
		UNIONCLOUD(1001, "银联云闪付");

		private int value;
		private String name;

		TransactionDetailType(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 取款通道
	 */
	public enum TakeMoneyChannel {
		/**
		 * 支付宝
		 */
		ALIPAY(0, "支付宝"),
		/**
		 * 银行
		 */
		BANK(1, "银行"),
		/**
		 * 人工
		 */
		ADMIN(2, "人工");

		private static final List<Map<String, Object>> lookup = Lists.newArrayList();

		static {
			// Map<String, Object> channelMap = null;
			// for (TakeMoneyChannel channel : TakeMoneyChannel.values()) {
			// channelMap = new HashMap<String, Object>();
			// channelMap.put("id", channel.getValue());
			// channelMap.put("name", channel.getName());
			// lookup.add(channelMap);
			// }
			Map<String, Object> channelMap = new HashMap<String, Object>();
			// for (TakeMoneyChannel channel : TakeMoneyChannel.values()) {
			// channelMap = new HashMap<String, Object>();
			// channelMap.put("id", channel.getValue());
			// channelMap.put("name", channel.getName());
			// lookup.add(channelMap);
			// }
			channelMap = new HashMap<String, Object>();
			channelMap.put("id", BANK.getValue());
			channelMap.put("name", BANK.getName());
			lookup.add(channelMap);
		}

		private int value;
		private String name;

		TakeMoneyChannel(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static List<Map<String, Object>> getLookup() {
			return lookup;
		}
	}

	/**
	 * 取款类型
	 */
	public enum TakeMoneyType {
		/**
		 * 余额取款
		 */
		TAKE_MONEY(0, "余额取款"),
		/**
		 * 佣金取款
		 */
		COMMISSION(1, "佣金取款");

		private Integer value;
		private String name;

		TakeMoneyType(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 站内信类别
	 */
	public enum MessageContentType {
		/**
		 * 用户站内信
		 */
		USER_CONTENT(1, "用户站内信"),
		/**
		 * 管理员站内信
		 */
		ADMIN_CONTENT(2, "管理员站内信");

		private Integer value;
		private String name;

		MessageContentType(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 站内信对象
	 */
	public enum MessageTarget {
		/**
		 * 指定用户
		 */
		USER(1, "指定用户"),
		/**
		 * 指定层级
		 */
		HIERARCHY(2, "指定层级"),
		/**
		 * 所有用户
		 */
		ALL(3, "所有用户");

		private Integer value;
		private String name;

		MessageTarget(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 签到对象
	 */
	public enum signStatus {
		/**
		 * 已签
		 */
		SIGNED(0, "已签"),
		/**
		 * 不可签
		 */
		NOT_ALLOWED_SIGN(1, "不可签"),
		/**
		 * 可签
		 */
		ALLOW_SIGN(2, "可签");

		private Integer value;
		private String name;

		signStatus(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 网址域名类型
	 */
	public enum DomainType {
		/**
		 * 官网
		 */
		OFFICAL(1, "官网"),
		/**
		 * 管理后台
		 */
		MANAGEMENT(2, "管理后台"),

		/**
		 * 推广链接
		 */
		RECOMMENDATION(3, "推广链接");

		private static final List<Map<String, Object>> lookup = Lists.newArrayList();

		static {
			Map<String, Object> domainTypeMap = null;
			for (DomainType domainType : DomainType.values()) {
				domainTypeMap = new HashMap<String, Object>();
				domainTypeMap.put("id", domainType.getValue());
				domainTypeMap.put("name", domainType.getName());
				lookup.add(domainTypeMap);
			}
		}

		private int value;
		private String name;

		DomainType(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public static List<Map<String, Object>> getLookup() {
			return lookup;
		}
	}

	/**
	 * 奖励金类型
	 */
	public enum UserActivityAwardType {
		/**
		 * 对局奖励
		 */
		MATCHAWARD(1),
		/**
		 * 推荐实名好友
		 */
		RECOMMEND(2),
		/**
		 * 分享App
		 */
		SHARE(3),
		/**
		 * 约好友对战
		 */
		MAKEFRIENDS(4),
		/**
		 * 救济金
		 */
		RELIEF(5),
		/**
		 * 鼓励金
		 */
		ENCOURAGE(6);

		private int value;

		UserActivityAwardType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	/**
	 * 头像框类型
	 */
	public enum HeadFrameType {
		/**
		 * vip
		 */
		VIP("vip");

		private String value;

		HeadFrameType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
