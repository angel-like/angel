package com.xmsy.server.zxyy.calculate.common.utils;

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

	/** 超级管理员角色ID */
	public static final Long SUPER_ROLE = 1L;
	/** 财务角色ID */
	public static final Long FINANCE_ROLE = 2L;
	/** 代理商角色ID */
	public static final Long AGENCY_ROLE = 3L;
	/** 普通角色ID */
	public static final Long COMMON_ROLE = 4L;

	/** 客户端传过来的金比例100：1 */
	public static final long CLIENT_COIN_RATE = Long.valueOf(RESOURCE.getString("client-coin-exchange"));
	/** 金币和钱的比例的10000：1 */
	public static final long COIN_RATE = Long.valueOf(RESOURCE.getString("sys-coin-exchange"));
	/** 数据库存取金币比例100：1 */
	public static final long DB_COIN_RATE = 100;

	public static final String TITLE = "充值结果通知";
	public static final String SUCCUSS_CONTENT = "充值订单: %s, 金额: %s, 已被确认,请注意查收";
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户
	/** 活动类型 1：奖励金 */
	public static final int ACTIVITYTYPE_1 = 1;
	/** 发送模式 0：后台发放 1：客户领取 */
	public static final int SENDTYPE_0 = 0;
	/** 发送模式 0：后台发放 1：客户领取 */
	public static final int SENDTYPE_1 = 1;
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
		BANK(3),
		/**
		 * 第三方充值
		 */
		THIRD(2),
		/**
		 * 人工充值
		 */
		ADMIN(1);

		private int value;

		RechargeType(int value) {
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
		REVOKE(3, "订单撤销");

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
		 * 额度装换
		 */
		CHANGER(5),
		/**
		 * 归集
		 */
		INTEGRATE(6),
		/**
		 * 额度装换
		 */
		EXCHANGE(7),
		/**
		 * 取款回退
		 */
		TAKE_RETURN(8);

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
		RECHARGE_BANK_ATM(1, "ATM转账"),
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
		JIAOTONG_BANK_COUNTER(5, "建设银行"),
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
		MINGSHENG_BANK_COUNTER(11, "民生银行");

		private static final List<Map<String, String>> lookup = Lists.newArrayList();
		private static final Map<Integer, String> bankTypeMap = Maps.newConcurrentMap();

		static {
			Map<String, String> rechargeBankTypeMap = null;
			for (RechargeBankType rechargeBankType : RechargeBankType.values()) {
				rechargeBankTypeMap = new HashMap<String, String>();
				rechargeBankTypeMap.put("id", String.valueOf(rechargeBankType.getValue()));
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

		public static List<Map<String, String>> getLookup() {
			return lookup;
		}

		public static Map<Integer, String> getBanktypemap() {
			return bankTypeMap;
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
		 * 微信
		 */
		WECHAT(2, "微信");

		private static final List<Map<String, String>> lookup = Lists.newArrayList();

		static {
			Map<String, String> channelMap = null;
			for (TakeMoneyChannel channel : TakeMoneyChannel.values()) {
				channelMap = new HashMap<String, String>();
				channelMap.put("id", String.valueOf(channel.getValue()));
				channelMap.put("name", channel.getName());
				lookup.add(channelMap);
			}
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

		public static List<Map<String, String>> getLookup() {
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

}
