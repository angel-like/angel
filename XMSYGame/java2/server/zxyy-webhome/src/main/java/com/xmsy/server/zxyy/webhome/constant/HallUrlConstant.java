package com.xmsy.server.zxyy.webhome.constant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局定义
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class HallUrlConstant {

	// ======================================游戏管理服===============================
	@Value("${gamemanager.base-url}")
	private String gamemanager_baseurl;
	@Value("${gamemanager.hall-list}")
	private String gamemanager_halllist;
	@Value("${gamemanager.game-list}")
	private String gamemanager_gamelist;
	@Value("${gamemanager.game-select}")
	private String gamemanager_gameselect;
	@Value("${gamemanager.game-menu}")
	private String gamemanager_menu;
	@Value("${gamemanager.token}")
	private String gamemanager_token;
	@Value("${provider.code}")
	private Long providerCode;


	// ======================================推送服===============================
	@Value("${push.url}")
	private String push_url;

	@Value("${push.rateUrl}")
	private String push_rateurl;

	// ======================================踢人url===============================
	@Value("${kick.url}")
	private String kick_url;

	// ======================================游戏大厅===============================
	@Value("${hall.url}")
	private String hall_url;

	// ======================================支付系统===============================
	@Value("${payment.h5Url}")
	private String payment_h5url;
	@Value("${payment.pcUrl}")
	private String payment_pcurl;
	@Value("${payment.token}")
	private String payment_token;

	// ======================================七牛云配置===============================
	@Value("${qiniu.url}")
	private String qiniu_url;
	@Value("${qiniu.publickey}")
	private String qiniu_publickey;
	@Value("${qiniu.access_key}")
	private String qiniu_access_key;
	@Value("${qiniu.secret_key}")
	private String qiniu_secret_key;
	@Value("${qiniu.bucket_public}")
	private String qiniu_bucket_public;

	// ======================================本服务地址===============================
	@Value("${host.url}")
	private String host_url;
	// ======================================排行榜===============================
	@Value("${rankinglist.limit}")
	private Integer rankinglist_limit;
	@Value("${rankinglist.share}")
	private Long share_rankinglist;
	@Value("${rankinglist.recharge}")
	private Long recharge_rankinglist;
	@Value("${rankinglist.piwaward}")
	private Long piwaward_rankinglist;
	@Value("${rankinglist.wealth}")
	private Long wealth_rankinglist;

	// ======================================第三方登入===============================
	@Value("${app.wechat.appId}")
	private String wechat_appId;

	@Value("${app.wechat.appSecret}")
	private String wechat_appSecret;


	// ======================================开元接入参数 start===============================
	@Value("${kaiyuan.url}")
	private String ky_url;

	@Value("${kaiyuan.agent}")
	private String agent;

	@Value("${kaiyuan.linecode}")
	private String linecode;

	@Value("${kaiyuan.md5key}")
	private String md5key;

	@Value("${kaiyuan.deskey}")
	private String deskey;

	@Value("${kaiyuan.recordUrl}")
	private String recordUrl;

	// ======================================开元接入参数 end===============================



	@PostConstruct
	public void init() {
		QINIU_ACCESS_KEY = qiniu_access_key;
		QINIU_SECRET_KEY = qiniu_secret_key;
		QINIU_BUCKET_PULIC = qiniu_bucket_public;
		PROVIDER_CODE = providerCode;
		QINIU_URL = qiniu_url;
		log.info("qiniu: [url] {} [publickey] {} [access_key] {} [secret_key] {} [bucket_public] {}", qiniu_url,
				qiniu_publickey, qiniu_access_key, qiniu_secret_key, qiniu_bucket_public);

		GAME_MANAGER_URL = gamemanager_baseurl;
		GAME_MANAGER_TOKEN = gamemanager_token;
		GAME_MANAGER_GAME_SELECT = gamemanager_baseurl + gamemanager_gameselect;
		GAME_MANAGER_GAME_MENU = gamemanager_baseurl + gamemanager_menu;
		GAME_MANAGER_HALL_LIST = gamemanager_baseurl + gamemanager_halllist;
		GAME_MANAGER_GAME_LIST = gamemanager_baseurl + gamemanager_gamelist;

		log.info(
				"gamemanager: [base-url] {} [hall-list]  {} [game-list] {} [game-select] {} [game_menu] {} [token] {} ",
				gamemanager_baseurl, gamemanager_halllist, gamemanager_gamelist, gamemanager_gameselect,
				gamemanager_menu, gamemanager_token);

		PUSH_URL = push_url;
		PUSH_RATEURL = push_rateurl;
		log.info("push: [url] {}", push_url);
		log.info("push: [rateurl] {}", push_rateurl);

		KICK_URL = kick_url;
		log.info("kick: [url] {}", kick_url);

		HALL_URL = hall_url;
		log.info("hall: [url] {}", hall_url);

		PAYMENT_H5URL = payment_h5url;
		PAYMENT_PCURL = payment_pcurl;
		PAYMENT_TOKEN = payment_token;

		log.info("payment: [url] PAYMENT_H5URL: {} PAYMENT_PCURL : {} ", payment_h5url, payment_pcurl);

		HOST_URL = host_url;
		log.info("host: [url] {}", host_url);

		RANKINGLIST_LIMIT = rankinglist_limit;
		SHARE_RANKINGLIST = share_rankinglist;
		RECHARGE_RANKINGLIST = recharge_rankinglist;
		PIWAWARD_RANKINGLIST = piwaward_rankinglist;
		WEALTH_RANKINGLIST = wealth_rankinglist;
		log.info("SHARE_RANKINGLIST {}", SHARE_RANKINGLIST);
		log.info("RECHARGE_RANKINGLIST {}", RECHARGE_RANKINGLIST);
		log.info("PIWAWARD_RANKINGLIST {}", PIWAWARD_RANKINGLIST);
		log.info("WEALTH_RANKINGLIST {}", WEALTH_RANKINGLIST);

		WECHAT_APPID = wechat_appId;
		WECHAT_APPSECRET = wechat_appSecret;
		log.info("WECHAT_APPID {}", wechat_appId);
		log.info("WECHAT_APPSECRET {}", wechat_appSecret);

		KY_URL =ky_url;
		AGENT =agent;
		LINECODE =linecode;
		MD5KEY =md5key;
		DESKEY=deskey;
		RECORDURL =recordUrl;
	}

	/*
	 * =================七牛云===============================================
	 */
	private static String QINIU_ACCESS_KEY = null;
	private static String QINIU_SECRET_KEY = null;
	private static String QINIU_BUCKET_PULIC = null;
	private static String QINIU_URL = null;

	/*
	 * =================游戏管理服===============================================
	 */
	// 游戏管理服url
	private static String GAME_MANAGER_URL = null;
	// 游戏管理服获取大厅列表url
	private static String GAME_MANAGER_HALL_LIST = null;
	// 游戏管理服获取游戏列表url
	private static String GAME_MANAGER_GAME_LIST = null;
	// 游戏管理服token
	private static String GAME_MANAGER_TOKEN = null;
	// app广告跳转进入的游戏列表
	private static String GAME_MANAGER_GAME_SELECT = null;
	// 用户游戏权限菜单
	private static String GAME_MANAGER_GAME_MENU = null;

	/*
	 * =================推送服===============================================
	 */
	private static String PUSH_URL = null;
	private static String PUSH_RATEURL = null;

	/*
	 * =================踢人url===============================================
	 */
	private static String KICK_URL = null;

	/*
	 * =================游戏大厅===============================================
	 * 
	 */
	private static String HALL_URL = null;

	/*
	 * =================支付系统===============================================
	 * 
	 */
	private static String PAYMENT_H5URL = null;
	private static String PAYMENT_PCURL = null;
	private static String PAYMENT_TOKEN = null;
	/*
	 * =================本服务地址===============================================
	 * 
	 */
	private static String HOST_URL = null;
	/*
	 * =================排行榜单id===============================================
	 * 
	 */
	private static Integer RANKINGLIST_LIMIT = null;
	private static Long SHARE_RANKINGLIST = null;
	private static Long RECHARGE_RANKINGLIST = null;
	private static Long PIWAWARD_RANKINGLIST = null;
	private static Long WEALTH_RANKINGLIST = null;

	/*
	 * =================第三方===============================================
	 * 
	 */
	private static String WECHAT_APPID = null;
	private static String WECHAT_APPSECRET = null;


	/*
	 * =================供应商===============================================
	 *
	 */
	private static Long PROVIDER_CODE = null;

	/*
	 * =================开元===============================================
	 *
	 */

	private static String KY_URL = null;
	private static String AGENT = null;
	private static String LINECODE = null;
	private static String MD5KEY = null;
	private static String DESKEY = null;
	private static String RECORDURL = null;

	public static String getRECORDURL() {
		return RECORDURL;
	}

	public String getGamemanager_baseurl() {
		return gamemanager_baseurl;
	}

	public static String getKyUrl() {
		return KY_URL;
	}

	public static String getAGENT() {
		return AGENT;
	}

	public static String getLINECODE() {
		return LINECODE;
	}

	public static String getMD5KEY() {
		return MD5KEY;
	}

	public static String getDESKEY() {
		return DESKEY;
	}

	public static String getPUSH_RATEURL() {
		return PUSH_RATEURL;
	}

	public static Integer getRANKINGLIST_LIMIT() {
		return RANKINGLIST_LIMIT;
	}

	public static String getQINIU_ACCESS_KEY() {
		return QINIU_ACCESS_KEY;
	}

	public static Long getSHARE_RANKINGLIST() {
		return SHARE_RANKINGLIST;
	}

	public static Long getRECHARGE_RANKINGLIST() {
		return RECHARGE_RANKINGLIST;
	}

	public static Long getPIWAWARD_RANKINGLIST() {
		return PIWAWARD_RANKINGLIST;
	}

	public static Long getWEALTH_RANKINGLIST() {
		return WEALTH_RANKINGLIST;
	}

	public static String getQINIU_SECRET_KEY() {
		return QINIU_SECRET_KEY;
	}

	public static String getQINIU_BUCKET_PULIC() {
		return QINIU_BUCKET_PULIC;
	}

	public static String getQINIU_URL() {
		return QINIU_URL;
	}

	public static String getGAME_MANAGER_URL() {
		return GAME_MANAGER_URL;
	}

	public static String getGAME_MANAGER_TOKEN() {
		return GAME_MANAGER_TOKEN;
	}

	public static String getGAME_MANAGER_GAME_SELECT() {
		return GAME_MANAGER_GAME_SELECT;
	}

	public static String getGAME_MANAGER_GAME_MENU() {
		return GAME_MANAGER_GAME_MENU;
	}

	public static String getPUSH_URL() {
		return PUSH_URL;
	}

	public static String getHALL_URL() {
		return HALL_URL;
	}

	public static String getGAME_MANAGER_HALL_LIST() {
		return GAME_MANAGER_HALL_LIST;
	}

	public static String getGAME_MANAGER_GAME_LIST() {
		return GAME_MANAGER_GAME_LIST;
	}

	public static String getHOST_URL() {
		return HOST_URL;
	}

	public static String getKICK_URL() {
		return KICK_URL;
	}

	public static String getWechat_appId() {
		return WECHAT_APPID;
	}

	public static String getWechat_appSecret() {
		return WECHAT_APPSECRET;
	}

	public static String getPAYMENT_H5URL() {
		return PAYMENT_H5URL;
	}

	public static String getPAYMENT_PCURL() {
		return PAYMENT_PCURL;
	}

	public static String getPAYMENT_TOKEN() {
		return PAYMENT_TOKEN;
	}
	public static Long getPROVIDER_CODE() {
		return PROVIDER_CODE;
	}

}
