package com.xmsy.server.zxyy.webhome.constant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局定义
 * 
 * @author Administrator
 *
 */
public class SysConstant {
	/**
	 * 游戏名称默认Map
	 */
	public static Map<Long, String> gameMap = new HashMap<Long, String>();
	/**
	 * 游戏场次默认Map
	 */
	public static Map<Long, String> gradeMap = new HashMap<Long, String>();
	/**
	 * 游戏房间默认Map
	 */
	public static Map<Long, String> roomMap = new HashMap<Long, String>();
	/**
	 * 游戏id和房间名称对应的map
	 */
	public static Map<Long, String> gameRoomMap = new HashMap<Long, String>();
	/**
	 * 游戏大厅默认Map
	 */
	public static Map<Integer, String> hallMap = new HashMap<Integer, String>();
	/**
	 * 游戏大厅Ip和端口默认Map
	 */
	public static Map<Integer, String> hallAddressMap = new HashMap<Integer, String>();

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
	public static String VIP = "VIP";

	/**
	 * 账号类型(试玩)
	 */
	public static String TRIAL = "TRIAL";
	/**
	 * 账号类型(普通用户)
	 */
	public static String USER = "USER";

	public static String TOURIST = "TOURIST";
	/**
	 * 账号状态(停押)
	 */
	public static String NOBET = "NOBET";
	/**
	 * 登入
	 */
	public static String LOGON = "logon";
	/**
	 * 密码错误次数
	 */
	public static String PASSWORD_ERROR_LIMIT = "passwordErrorLimit";

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
	 * 游戏记录轮播
	 */
	public static String GAMERECORD = "gameRecord";
	/**
	 * 交易记录轮播图
	 */
	public static String TRANSACTIONRECORD = "transactionRecord";
	/**
	 * 充值轮播图片
	 */
	public static String RECHARGERECORD = "recharge";

	/**
	 * 轮播图片
	 */
	public static String EXHIBITION = "exhibition";
	/**
	 * 对联图片
	 */
	public static String COUPLETS = "couplets";
	/**
	 * 取消按钮
	 */
	public static String CANCEL = "cancel";
	/**
	 * 底部图片
	 */
	public static String BOTTOM = "bottom";
	/**
	 * 域名图片
	 */
	public static String DOMAIN = "domain";
	/**
	 * 首页支付图
	 */
	public static String PAY = "pay";
	/**
	 * 优惠活动图
	 */
	public static String PROMOTIONS = "promotions";
	/**
	 * APP下载
	 */
	public static String APPDOWNLOAD = "AppDownload";
	/**
	 * APP教程
	 */
	public static String APPTUTORIAL = "appTutorial";
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
	/**
	 * 网站信息
	 */
	public static Integer WEBINT_CONTENT = 1;
	/**
	 * 网站联系方式
	 */
	public static Integer CONTACTINT_CONTENT = 0;
	/**
	 * h5联系方式
	 */
	public static Integer h5_CONTACTINT_CONTENT = 2;
	/**
	 * 官方自媒体
	 */
	public static Integer OFFICAL_MEDIA_INT = 3;
	/**
	 * 合作媒体
	 */
	public static Integer COOPRATION_MEDIA_INT = 4;
	
	/** 一级菜单的上级ID */
	public static final Long FATHER = 0L;

	/** 一级菜单 */
	public static final Integer ONELEVEL = 0;

	/** 二级菜单 */
	public static final Integer TWOLEVEL = 1;

	/** 游戏类导航 */
	public static final String GAME = "game";

	/** 菜单类导航 */
	public static final String MENU = "menu";

	/** 密码错误 */
	public static final String PASSWORD_ERROR = "password_error";

	/** 密码错误 */
	public static final int PASSWORD_ERROR_NUM = 10;

	/** 微信登陆 */
	public static final String WECHAT = "1";
	/** QQ */
	public static final String QQ = "2";

	/** 新浪微博登陆 */
	public static final String SINA = "3";

	/** 电脑 */
	public static final String WEB = "web";
	/** 移动端 */
	public static final String MOBILE = "mobile";

	/** 待确认 */
	public static final String TOBECONFIRMED = "unConfirmed";
	/** 已确认 */
	public static final String CONFIRMED = "confirmed";

	/** 未审核 */
	public static final String NOTREATMENT = "0";
	/** 已放款 */
	public static final String ALREADYPROCESSED = "1";
	/** 不通过 */
	public static final String NOTPASS = "2";

	/** 存款方式(银行转账) */
	public static final String BANK = "1";
	/** 存款方式(网银转账) */
	public static final String INTERNET = "2";
	/** 存款方式 (第三方转账) */
	public static final String OTHER = "3";

	/**
	 * app注册是是否验证手机，id=2（注册必填项）
	 */
	public static final Long REGISTER_PHONE = 2L;

	/**
	 * 假
	 */
	public static final Boolean ENABLE_FALSE = false;
	/**
	 * 真
	 */
	public static final Boolean ENABLE_TRUE = true;

	/**
	 * 层级类型 1：风控层级
	 * 
	 */
	public static final Integer HIERARCHYTYPE_1 = 1;
	/**
	 * 交易类型 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 * 
	 */
	public static final Integer TRANSACTION_RECHAGE = 0;
	/**
	 * 交易类型 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 * 
	 */
	public static final Integer TRANSACTION_TAKE = 1;
	/**
	 * 交易类型 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 * 
	 */
	public static final Integer TRANSACTION_ELIMINATE = 2;
	/**
	 * 交易类型 交易类型(0：存款 1:取款 2:冲销 3:返利)
	 * 
	 */
	public static final Integer TRANSACTION_REBATE = 3;
	/**
	 * 具体类型 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利
	 * 19推荐返利20完善信息返利
	 */
	public static final Integer TRANSACTION_DETAIL_BANK = 11;
	/**
	 * 具体类型 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利
	 * 19推荐返利20完善信息返利
	 */
	public static final Integer TRANSACTION_DETAIL_RECOMMEND = 19;

	/**
	 * 优惠类型 首充优惠
	 */
	public static final Integer RECHAGE_PREFERENTIAL_FIRST = 1;
	/**
	 * 优惠类型 满送优惠
	 */
	public static final Integer RECHAGE_PREFERENTIAL_GIFT = 2;

	/**
	 * 消息类型 1：指定用户id
	 */
	public static final Integer MESSAGE_TYPE_USER = 1;
	/**
	 * 消息类型 2：指定层级
	 */
	public static final Integer MESSAGE_TYPE_HIERARCHY = 2;
	/**
	 * 消息类型 2：所有
	 */
	public static final Integer MESSAGE_TYPE_ALL = 3;
	/**
	 * 现金价格预设配置类型 0：充值
	 */
	public static final Integer RECHARGE_PRICE_TYPE = 0;
	/**
	 * 现金价格预设配置类型 1：提现
	 */
	public static final Integer TAKE_PRICE_TYPE = 1;
	/**
	 * 游戏名称缓存前缀
	 */
	public static final String GAME_CACHE_PREFIX = "game_";
	/**
	 * 游戏场次名称缓存前缀
	 */
	public static final String GRADE_CACHE_PREFIX = "grade_";
	/**
	 * 房间名称缓存前缀
	 */
	public static final String ROOM_CACHE_PREFIX = "room_";

	/**
	 * 友情链接跳转类型url
	 */
	public static final Integer FRIENDSHIP_URL_TYPE = 1;
	/**
	 * 友情链接跳转类型children
	 */
	public static final Integer FRIENDSHIP_CHILDREN_TYPE = 2;
	/**
	 * 用于时间拼接开始
	 */
	public static final String START_TIME = " 00:00:00";
	/**
	 * 用于时间拼接结束
	 */
	public static final String END_TIME = " 23:59:59";

	/**
	 * 数据字典/路径管理code
	 */
	public static final String urlCode = "URL";
	/**
	 * 数据字典/路径管理code/h5支付code
	 */
	public static final String H5PayCode = "H5Pay";
	/**
	 * 数据字典/路径管理code/app活动url
	 */
	public static final String AppActivityUrl = "AppActivityUrl";
	/**
	 * 数据字典/路径管理code/app邀请注册路径
	 */
	public static final String RECOMMENDATIONURL = "recommendationUrl";

	/**
	 * 数据字典/奖励类型/实名返利
	 */
	public static final Integer RewardType1 = 1;
	/**
	 * 数据字典/奖励类型/用户返水
	 */
	public static final Integer RewardType2 = 2;
	/**
	 * 邮件发送对象/指定用户
	 */
	public static final Integer TARGET_OBJECT_1 = 1;
	/**
	 * 邮件发送对象/指定用户层次
	 */
	public static final Integer TARGET_OBJECT_2 = 2;
	/**
	 * 邮件发送对象/所有用户
	 */
	public static final Integer TARGET_OBJECT_3 = 3;

	/** 游戏服游戏信息变更监听队列 */
	public static final String GAME_QUEUE = "game";
	// 重试次数
	public static final int RETRY_TIME = 3;
	// 重试计数器
	public static final Map<String, Integer> MESSAGE = Maps.newConcurrentMap();

	// 登录设备类型 pc
	public static final String DEVICE_TYPE_PC = "pc";
	// 登录设备类型 android
	public static final String DEVICE_TYPE_ANDROID = "android";
	// 登录设备类型 iphone
	public static final String DEVICE_TYPE_IPHONE = "iphone";
	// 登录设备类型 其他
	public static final String DEVICE_TYPE_OTHER = "其他";

	/**
	 * 匹配房间id
	 */
	public static final Long ROOM_ID_1 = 1l;
	/**
	 * 房卡房间id
	 */
	public static final Long ROOM_ID_2 = 2l;
	/**
	 * 百人房间id
	 */
	public static final Long ROOM_ID_3 = 3l;

	/**
	 * 人工存款
	 */
	public static final int ADMIN_OPERATION_TYPE_0 = 0;
	/**
	 * 人工取款
	 */
	public static final int ADMIN_OPERATION_TYPE_1 = 1;
	/**
	 * 人工取款 充值
	 */
	public static final int ADMIN_RECHARGE_TYPE_0 = 0;
	/**
	 * 人工取款 赠送
	 */
	public static final int ADMIN_RECHARGE_TYPE_1 = 1;
	
	/**
	 * 游戏id==10
	 */
	public static final Long GAME_IDE_10= 10l;

	// 财富榜key
	public static final String FORTUNEKEY = "fortuneList";
	// 派奖榜key
	public static final String PIEAWARKEY = "pieAwardList";
	/**
	 * 33网站信息
	 */
	public static final String H5CONTACT = "h5Contact";//h5联系方式
	public static final String WEBSITE = "website";//网站信息
	public static final String CONTACT = "Contact";//网站联系方式
	public static final String OFFICAL_MEDIA = "officalMedia";//官方自媒体
	public static final String COOPRATION_MEDIA = "cooperationMedia";//合作媒体
	
	public static final Long COIN_PROPID=1L;//道具id  1代表金币 
	
	public static final Long ROOMCARD_PROPID=2L;//道具id  2代表房卡 
	
	public static final String USER_TOURIST = "TOURIST";//用户类型为游客
}
