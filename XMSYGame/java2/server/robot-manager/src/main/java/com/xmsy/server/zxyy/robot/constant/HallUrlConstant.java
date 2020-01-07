package com.xmsy.server.zxyy.robot.constant;

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
	@Value("${gamemanager.game-select-for-robot}")
	private String gamemanager_gameselectforrobot;
	@Value("${gamemanager.game-select}")
	private String game_select;
	@Value("${gamemanager.room-select}")
	private String room_select;
	@Value("${gamemanager.token}")
	private String gamemanager_token;

	// ======================================机器人管理服===============================
	@Value("${robotmanager.base-url}")
	private String robotmanager_baseurl;
	@Value("${robotmanager.game-list}")
	private String robotmanager_gamelist;
	
	// ======================================游戏大厅===============================
	@Value("${hall.url}")
	private String hall_url;

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

	@PostConstruct
	public void init() {

		QINIU_ACCESS_KEY = qiniu_access_key;
		QINIU_SECRET_KEY = qiniu_secret_key;
		QINIU_BUCKET_PULIC = qiniu_bucket_public;
		QINIU_URL = qiniu_url;
		log.info("qiniu: [url] {} [publickey] {} [access_key] {} [secret_key] {} [bucket_public] {}", qiniu_url,
				qiniu_publickey, qiniu_access_key, qiniu_secret_key, qiniu_bucket_public);

		GAME_MANAGER_URL = gamemanager_baseurl;
		GAME_MANAGER_TOKEN = gamemanager_token;
		GAME_MANAGER_GAME_SELECT_FOR_ROBOT=gamemanager_baseurl + gamemanager_gameselectforrobot;
		GAME_MANAGER_GAME_SELECT=gamemanager_baseurl + game_select;
		GAME_MANAGER_ROOM_SELECT=gamemanager_baseurl + room_select;
		log.info(
				"gamemanager: [base-url] {} [hall-list]  {} [game-list] {} [game-select] {} [game-select-for-robot] {} [game_menu] {} [token] {} ",
				gamemanager_baseurl, gamemanager_token);


		HALL_URL = hall_url;
		log.info("hall: [url] {}", hall_url);
		
		ROBOTMANAGER_BASEURL = robotmanager_baseurl;
		log.info("robot: [url] {}", robotmanager_baseurl);
		ROBOTMANAGER_GAMELIST = robotmanager_baseurl+robotmanager_gamelist;
		log.info("robot: [url] {}", robotmanager_gamelist);
		
		
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
	// 游戏管理服token
	private static String GAME_MANAGER_TOKEN = null;
	// 游戏管理机器人管理后台
	private static String GAME_MANAGER_GAME_SELECT_FOR_ROBOT = null;
	// 游戏管理服游戏下拉
	private static String GAME_MANAGER_GAME_SELECT = null;
	// 游戏管理服房间下拉
	private static String GAME_MANAGER_ROOM_SELECT = null;
	
	

	/*
	 * =================机器人管理服===============================================
	 */
	private static String ROBOTMANAGER_BASEURL = null;
	private static String ROBOTMANAGER_GAMELIST = null;

	/*
	 * =================游戏大厅===============================================
	 * 
	 */
	private static String HALL_URL = null;

	public static String getQINIU_ACCESS_KEY() {
		return QINIU_ACCESS_KEY;
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

	public static String getGAME_MANAGER_GAME_SELECT_FOR_ROBOT() {
		return GAME_MANAGER_GAME_SELECT_FOR_ROBOT;
	}
	public static String getGAME_MANAGER_GAME_SELECT() {
		return GAME_MANAGER_GAME_SELECT;
	}

	

	public static String getHALL_URL() {
		return HALL_URL;
	}

	public static String getROBOTMANAGER_BASEURL() {
		return ROBOTMANAGER_BASEURL;
	}

	public static String getROBOTMANAGER_GAMELIST() {
		return ROBOTMANAGER_GAMELIST;
	}

	public static String getGAME_MANAGER_ROOM_SELECT() {
		return GAME_MANAGER_ROOM_SELECT;
	}
	
}
