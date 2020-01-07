package com.xmsy.server.zxyy.manager.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	public 	static  Map<Long, String> gameMap=new HashMap<Long, String>();
	/**
	 * 游戏场次默认Map
	 */
	public	static  Map<Long, String> gradeMap=new HashMap<Long, String>();
	/**
	 * 游戏房间默认Map
	 */
	public	static  Map<Long, String> roomMap=new HashMap<Long, String>();
	/**
	 * 游戏大厅默认Map
	 */
	public	static  Map<Integer, String> hallMap=new HashMap<Integer, String>();
	/**
	 * 游戏大厅Ip和端口默认Map
	 */
	public	static  Map<Integer, String> hallAddressMap=new HashMap<Integer, String>();
	
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
	/**
	 * 账号状态(停押)
	 */
	public static String NOBET = "NOBET";
	
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
	 * 层级类型
	 * 1：风控层级
	 * 
	 */
	public static final Integer HIERARCHYTYPE_1 = 1;
	
	public static final Integer HIERARCHYTYPE_0 = 0;
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
	 * 19推荐返利20完善信息返利
	 */
	public static final Integer TRANSACTION_DETAIL_BANK = 11;
	/**
	 * 具体类型
	 * 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利
	 * 19推荐返利20完善信息返利
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
	 * 消息类型
	 * 1：指定用户id
	 */
	public static final Integer MESSAGE_TYPE_USER = 1;
	/**
	 * 消息类型
	 * 2：指定层级
	 */
	public static final Integer MESSAGE_TYPE_HIERARCHY = 2;
	/**
	 * 消息类型
	 * 2：所有
	 */
	public static final Integer MESSAGE_TYPE_ALL = 3;
	/**
	 *现金价格预设配置类型
	 * 0：充值
	 */
	public static final Integer RECHARGE_PRICE_TYPE = 0;
	/**
	 * 现金价格预设配置类型
	 * 1：提现
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
	
	//登录设备类型 pc
	public static final String DEVICE_TYPE_PC = "pc";
	//登录设备类型 android
	public static final String DEVICE_TYPE_ANDROID = "android";
	//登录设备类型 iphone
	public static final String DEVICE_TYPE_IPHONE = "IOS";
	//登录设备类型 其他
	public static final String DEVICE_TYPE_OTHER = "其他";
	
	/**
	 * 匹配房间id
	 */
	public static final Long ROOM_ID_1 = 1l;
	
	public static final Long ROOM_ID_2 = 2l;
	
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
	
	
	// 财富榜key
	public static final String FORTUNEKEY = "fortuneList";
	// 派奖榜key
	public static final String PIEAWARKEY = "pieAwardList";
	
	//游戏ID
	public static final String GAMEID = "gameId";
	public static final Long DOU_DI_ZHU_ID=1L;//斗地主
	public static final Long RUSH_VILLAGE_NIU_NIU_ID=2L;//抢庄牛牛
	public static final Long TONG_BI_NIU_NIU_ID=3L;//通比牛牛
	public static final Long HUNDRED_PEOPLE_NIU_NIU_ID=4L;//百人牛牛
	public static final Long HUNDRED_PEOPLE_PAI_JIU_ID=5L;//百人牌九
	public static final Long HUNDRED_PEOPLE_FLOWER_ID=6L;//百人炸金花
	public static final Long THIRTEEN_WATER_ID=7L;//十三水
	public static final Long FLOWER_ID=8L;//炸金花
	public static final Long DRAGON_TIGER_ID=9L;//龙虎斗
	public static final Long HUNDRED_FAMLIY_ID=10L;//百家乐
	public static final Long TWO_EIGHT_ID=11L;//二八杠
	public static final Long HUNDRED_PEOPLE_THREE_GONG_ID=12L;//百人三公
	public static final Long ROOM_CARD_NIU_NIU_ID=13L;//房卡牛牛
	public static final Long ROOM_CARD_THIRTEEN_WATER_ID=14L;//房卡十三水
	public static final Long HUNDRED_PEOPLE_CLASSIC_NIU_NIU_ID=15L;//百人经典牛牛
	public static final Long DE_ZHOU_POOKER_ID=16L;//德州扑克
	public static final Long HUNDRED_PEOPLE_DICE_ID=17L;//百人骰宝
	public static final Long SEA_TREASURE_ID=201L;//海底宝藏
	//房间id
	public static final String ROOMID = "roomId";
	public static final String ROOMMATEID="1";//匹配场
	public static final String ROOMCARDID="2";//房卡场
	public static final String ROOMHUNDREDId="3";//百人场
	public static final String LABAID="4";//拉霸场
	//33娱乐类别
	public static final String ACTIVITY="activity";//活动优惠
	public static final String ACTIVITY_CATEGORY="3";
	public static final String NOTICE="notice";//公告类
	public static final String NOTICE_CATEGORY="1,2,4,5,11,13";
	public static final String HELP="help";//帮助中心
	public static final String HELP_CATEGORY="7,8,9,10,12";
	public static final String GAME_MANAGE="game";//游戏管理
	public static final String GAME_CATEGORY="6";
	//系统用户对应的角色ID
	public static final String SYSMANAGERID="1";//系统管理员角色ID
	public static final String PROXYID="5";//代理商角色id
	//app打包缓存key
	public static final String APP_PACK_KEY="appPack:";
	//天降财神充值事件
	public static final String FORTUNE_EVENT_RECHARGE="recharge-money";
}
