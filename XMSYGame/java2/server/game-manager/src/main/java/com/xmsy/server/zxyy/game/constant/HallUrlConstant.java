package com.xmsy.server.zxyy.game.constant;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * 全局定义
 *
 * @author Administrator
 *
 */
@Component
public class HallUrlConstant {

	/*
	 * 游戏接口配置获取
	 */
	public static final ResourceBundle RESOURCE = ResourceBundle.getBundle("application");

	//qiniuyun
	public static final String QINIU_ACCESS_KEY =RESOURCE.getString("qiniu_access_key");
	public static final String QINIU_SECRET_KEY =RESOURCE.getString("qiniu_secret_key");
	public static final String QINIU_BUCKET_PULIC =RESOURCE.getString("qiniu_bucket_public");
	public static final String QINIU_URL =RESOURCE.getString("qiniu-url");

	/*
	 * ip
	 */
	public static final String ZXYY_USER_URL = RESOURCE.getString("zxyy_user_url");
	//zxyy大厅token
	public static final String ZXYY_TOKEN = RESOURCE.getString("zxyy_token");
	//zxyy大厅注册用户
	public static final String ZXYY_REGISTER = ZXYY_USER_URL+RESOURCE.getString("zxyy_register");


	/*
	 * =================游戏大厅===============================================
	 *
	 */
	private static String HALL_URL = null;
	@Value("${hall.url}")
	private String hall_url;
	@PostConstruct
	public void init() {
		HALL_URL = hall_url;
		ROBOTMANAGER_BASEURL = robotmanager_baseurl;

		ROBOTMANAGER_GAMELIST = robotmanager_baseurl+robotmanager_gamelist;

	}
	public static String getHALL_URL() {
		return HALL_URL;
	}


	// ======================================机器人管理服===============================
	@Value("${robotmanager.base-url}")
	private String robotmanager_baseurl;
	@Value("${robotmanager.game-list}")
	private String robotmanager_gamelist;
	/*
	 * =================机器人管理服===============================================
	 */
	private static String ROBOTMANAGER_BASEURL = null;
	private static String ROBOTMANAGER_GAMELIST = null;
	public static String getROBOTMANAGER_BASEURL() {
		return ROBOTMANAGER_BASEURL;
	}

	public static String getROBOTMANAGER_GAMELIST() {
		return ROBOTMANAGER_GAMELIST;
	}

}
