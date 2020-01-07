package com.xmsy.server.zxyy.robot.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/** 金币和钱的比例的100：1 */
	public static final long COIN_RATE = 100L;

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
		UNCONFIRMED(0),
		/**
		 * 订单取消
		 */
		CANCEL(1),
		/**
		 * 订单完成
		 */
		COMPLETE(2);
		

		private int value;

		OrderStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
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
	 * 交易具体类型
	 */
	public enum TransactionDetailType {
		/**
		 * 银行充值
		 */
		BANKRECHARGE(11),
		/**
		 * 支付宝充值
		 */
		ALIPAYRECHARGE(12),
		/**
		 * 微信充值
		 */
		WECHATRECHARGE(13),
		/**
		 * 人工充值
		 */
		ARTIFICIALRECHARGE(14),
		/**
		 * 佣金取款
		 */
		COMMISSIONTAKE(15),
		/**
		 * 账户取款
		 */
		ACCOUNTTAKE(16),
		/**
		 * 额度转换
		 */
		QUOTACONVERSION(17),
		/**
		 * 签到返利
		 */
		CHECKINREBATE(18),
		/**
		 * 推荐返利
		 */
		RECOMMENDEDREBATE(19),
		/**
		 * 奖池派奖
		 */
		PRIZEPOOLAWARD(20),

		/**
		 * 资金归集
		 */
		INTEGRATE(21),
		/**
		 * 取消取款订单
		 */
		TAKE_MONEY_CANCEL(22),
		/**
		 * 佣金转金币
		 */
		EXCHANGE(23),
		/**
		 * 实名返利
		 */
		USERINFO(24),
		/**
		 * 取消佣金取现
		 */
		TAKE_COMMISSION_CANCEL(25),
		/**
		 * 游戏返利
		 */
		GAMEREBATE(26);

		private int value;

		TransactionDetailType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
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
	public enum gameGradeType {
		/**
		 * 初级场
		 */
		ELEMENTARY(1L, "初级场"),
		/**
		 * 中级场
		 */
		MIDDLE(2L, "中级场"),
		/**
		 * 高级场
		 */
		HIGH(3L, "高级场");

		private Long value;
		private String name;

		gameGradeType(long value, String name) {
			this.value = value;
			this.name = name;
		}

		public long getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

}
