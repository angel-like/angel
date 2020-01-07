package com.xmsy.server.zxyy.schedule.utils;

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
public class SysConstant {

//	@Value("${spring.datasource.showSql}")
//	private boolean jdbc_showsql;
//	@Value("${spring.datasource.driverClassName}")
//	private String datasource_driver;
//	@Value("${spring.datasource.webhomeUrl}")
//	private String webhomeUrl;
//	@Value("${spring.datasource.robotUrl}")
//	private String robotUrl;
//	@Value("${spring.datasource.username}")
//	private String datasource_user;
//	@Value("${spring.datasource.password}")
//	private String datasource_password;
	
	@Value("${push.url}")
	private String push_url;
	@Value("${schedule.rankinglist.recharge}")
	private Long rankinglist_recharge;
	@Value("${schedule.rankinglist.piwaward}")
	private Long rankinglist_piwaward;
	@Value("${schedule.rankinglist.wealth}")
	private Long rankinglist_wealth;
	
	
	@PostConstruct
	public void init() {
//		JDBC_SHOWSQL =jdbc_showsql;
//		DATASOURCE_DRIVER =datasource_driver;
//		WEBHOME_DATASOURCE_URL = webhomeUrl;
//		ROBOT_DATASOURCE_URL = robotUrl;
//		DATASOURCE_USER = datasource_user;
//		DATASOURCE_PASSWORD = datasource_password;
		PUSH_URL=push_url;
		RANKINGLIST_RECHARGE=rankinglist_recharge;
		RANKINGLIST_PIWAWARD=rankinglist_piwaward;
		RANKINGLIST_WEALTH=rankinglist_wealth;
		log.info("PUSH_URL  {}", PUSH_URL);
		log.info("JDBC_SHOWSQL  {}", JDBC_SHOWSQL);
		log.info("DATASOURCE_DRIVER  {}", DATASOURCE_DRIVER);
		log.info("WEBHOME_DATASOURCE_URL  {}", WEBHOME_DATASOURCE_URL);
		log.info("ROBOT_DATASOURCE_URL  {}", ROBOT_DATASOURCE_URL);
		log.info("DATASOURCE_USER  {}", DATASOURCE_USER);
		log.info("DATASOURCE_PASSWORD  {}", DATASOURCE_PASSWORD);
		log.info("RANKINGLIST_RECHARGE  {}", RANKINGLIST_RECHARGE);
		log.info("RANKINGLIST_PIWAWARD  {}", RANKINGLIST_PIWAWARD);
		log.info("RANKINGLIST_WEALTH  {}", RANKINGLIST_WEALTH);
	}
	
	//是否启动debug
	private static  Boolean JDBC_SHOWSQL = false;
	//数据库驱动类名
	private static  String DATASOURCE_DRIVER = null;
	//webhome数据库链接
	private static  String WEBHOME_DATASOURCE_URL = null;
	//robot数据库链接
	private static  String ROBOT_DATASOURCE_URL = null;
	//数据库用户名
	private static  String DATASOURCE_USER = null;
	//数据库密码
	private static  String DATASOURCE_PASSWORD = null;
		
	//推送消息url
	private static  String PUSH_URL = null;
	//充值排行榜
	private static  Long RANKINGLIST_RECHARGE = null;
	//派奖排行榜
	private static  Long RANKINGLIST_PIWAWARD = null;
	//财富排行榜
	private static  Long RANKINGLIST_WEALTH = null;
	
	
	

	
	
	
//	public static Boolean getJDBC_SHOWSQL() {
//		return JDBC_SHOWSQL;
//	}
//	public static String getDATASOURCE_DRIVER() {
//		return DATASOURCE_DRIVER;
//	}
//	
//	public static String getWEBHOME_DATASOURCE_URL() {
//		return WEBHOME_DATASOURCE_URL;
//	}
//	public static String getROBOT_DATASOURCE_URL() {
//		return ROBOT_DATASOURCE_URL;
//	}
//	public static String getDATASOURCE_USER() {
//		return DATASOURCE_USER;
//	}
//	public static String getDATASOURCE_PASSWORD() {
//		return DATASOURCE_PASSWORD;
//	}
	
	public static Long getRANKINGLIST_RECHARGE() {
		return RANKINGLIST_RECHARGE;
	}
	public static String getPUSH_URL() {
		return PUSH_URL;
	}
	public static Long getRANKINGLIST_PIWAWARD() {
		return RANKINGLIST_PIWAWARD;
	}
	public static Long getRANKINGLIST_WEALTH() {
		return RANKINGLIST_WEALTH;
	}

	/**
	 * 数据字典/奖励类型/用户返水
	 */
	public static final Integer RewardType2 = 2;
	/** 客户端传过来的金比例100：1 */
	public static final long CLIENT_COIN_RATE = 100L;
	/** 金币和钱的比例的10000：1 */
	public static final long COIN_RATE = 10000L;
	

}
