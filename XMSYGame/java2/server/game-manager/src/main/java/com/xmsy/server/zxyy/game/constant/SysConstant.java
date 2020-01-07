package com.xmsy.server.zxyy.game.constant;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 全局定义
 * 
 * @author Administrator
 *
 */
public class SysConstant {
	
	/**
	 * 微信第三方返回正确
	 */
	public static final int oauth_successcode = 0;

	/**
	 * 默认起始页
	 */
	public static final Integer PAGE = 1;
	/**
	 * 默认每页数据个数
	 */
	public static final Integer PAGE_SIZE = 10;
	/**
	 * 游戏信息缓存
	 */
	public static List<HashMap<String, Object>> GAME_INFO = Lists.newArrayList();
	/**
	 * 游戏分类信息缓存
	 */
	public static List<HashMap<String, Object>> GAME_TYPE = Lists.newArrayList();

	/**
	 * 游戏场次信息缓存
	 */
	public static List<HashMap<String, Object>> GAME_GRADE = Lists.newArrayList();

	/**
	 * 游戏配置
	 */
	public static List<HashMap<String, Object>> GAME_GLOBAL_CONFIG = Lists.newArrayList();
	/**
	 * 游戏记录
	 */
	public static List<HashMap<String, Object>> GAME_RECORD = Lists.newArrayList();
	
	
	/**
	 * 奖励规则类型(邀请)
	 */
	public static String FIXED = "fixed";
	/**
	 * 奖励规则类型(有效投注返利)
	 */
	public static String PROPORTION = "proportion";
	/**
	 * 线下充值(转账)
	 */
	public static String RECHARGE = "recharge";
	/**
	 * 线上充值(扫码)
	 */
	public static String QRCODE = "qrcode";
	/**
	 * 取款
	 */
	public static String TAKE = "take";
	
	/**
	 * 账号类型(会员)
	 */
	public static String VIP = "vip";
	
	
	/**
	 * 账号类型(试玩)
	 */
	public static String TRIAL = "trial";
	/**
	 * 账号类型(普通用户)
	 */
	public static String USER = "user";
	/**
	 * 账号状态(停押)
	 */
	public static String NOBET = "nobet";
	
	/**
	 * 状态(成功)
	 */
	public static String SUCCESS = "success";
	/** 进行中 */
	public static final String WAIT = "wait";
	/**
	 * 状态(失败)
	 */
	public static String FAIL = "fail";
	/**
	 * logo图片
	 */
	public static String LOGO = "logo";
	
	/**
	 * 轮播图片
	 */
	public static String EXHIBITION = "exhibition";
	/**
	 * 对联图片
	 */
	public static String COUPLETS = "couplets";
	/**
	 * 是
	 */
	public static final String YES = "1";
	/**
	 * 否
	 */
	public static final String NO = "0";
	/**
	 * 展示
	 */
	public static String ENABLE_SHOW = "1";
	/**
	 * 不展示
	 */
	public static String ENABLE_DISPLAY = "2";
	
	/**
	 * 网站信息
	 */
	public static String WEBSITE_CONTENT = "website";
	/**
	 * 联系信息
	 */
	public static String CONTACT_CONTENT = "contact";
	
	/** 一级菜单的上级ID */
	public static final Long FATHER = 0L;
	
	/** 一级菜单 */
	public static final Integer ONELEVEL = 0;

	/** 二级菜单 */
	public static final Integer TWOLEVEL = 1;
	
	/** 游戏类导航*/
	public static final String GAME = "game";

	/** 菜单类导航 */
	public static final String MENU = "menu";
	
	/** 密码错误 */
	public static final String PASSWORD_ERROR = "password_error";
	
	/** 微信登陆 */
	public static final String WX = "3";
	/** 微信登陆 */
	public static final String QQ = "1";
	
	/** 新浪微博登陆 */
	public static final String XL = "2";
	/** 手机登陆 */
	public static final String SJ = "4";
	
	/** 电脑 */
	public static final String WEB = "web";
	/** 移动端 */
	public static final String MOBILE = "mobile";
	
	/** 待确认 */
	public static final String TOBECONFIRMED = "unConfirmed";
	/** 已确认 */
	public static final String CONFIRMED = "confirmed";
	
	/** 未审核*/
	public static final String NOTREATMENT = "0";
	/** 已放款 */
	public static final String ALREADYPROCESSED = "1";
	/** 不通过 */
	public static final String NOTPASS = "2";
	
	/** 存款方式(银行转账)*/
	public static final String BANK = "1";
	/** 存款方式(网银转账) */
	public static final String INTERNET = "2";
	/** 存款方式 (第三方转账)*/
	public static final String OTHER = "3";
	
	
	/**
	 * 假
	 */
	public static final Boolean ENABLE_FALSE = false;
	/**
	 * 真
	 */
	public static final Boolean ENABLE_TRUE = true;
	
	/**
	 * 交易类型
	 * 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 * 
	 */
	public static final Integer TRANSACTION_RECHAGE = 0;
	/**
	 * 交易类型
	 * 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 * 
	 */
	public static final Integer TRANSACTION_TAKE = 1;
	/**
	 * 交易类型
	 * 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 * 
	 */
	public static final Integer TRANSACTION_ELIMINATE = 2;
	/**
	 * 交易类型
	 * 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 * 
	 */
	public static final Integer TRANSACTION_REBATE = 3;
	/**
	 * 具体类型
	 * 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利
	 * 19推荐返利
	 */
	public static final Integer TRANSACTION_DETAIL_BANK = 11;
	/**
	 * 具体类型
	 * 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利
	 * 19推荐返利
	 */
	public static final Integer TRANSACTION_DETAIL_RECOMMEND = 19;
	
	/**
	 * 优惠类型
	 * 首充优惠
	 */
	public static final Integer RECHAGE_PREFERENTIAL_FIRST = 1;
	/**
	 * 优惠类型
	 * 满送优惠
	 */
	public static final Integer RECHAGE_PREFERENTIAL_GIFT = 2;
	
	/**
	 * 游戏配置
	 */
	public static final String GAMECONFIG = "gameconfig";
	/**
	 * 游戏配置/库存区间游戏概率
	 */
	public static final String INTERVALGAMERATE = "intervalGameRate";
	
}
