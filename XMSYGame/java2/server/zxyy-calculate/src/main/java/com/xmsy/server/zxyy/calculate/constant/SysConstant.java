package com.xmsy.server.zxyy.calculate.constant;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 全局定义
 * 
 * @author Administrator
 *
 */
public class SysConstant {

	/**
	 * 状态(成功)
	 */
	public static String SUCCESS = "success";
	// 重试次数
	public static final int RETRY_TIME = 3;
	// 重试计数器
	public static final Map<String, Integer> MESSAGE = Maps.newConcurrentMap();
	/** 注册监听队列 */
	public static final String REGISTER_QUEUE = "register";
	/** 注册监听队列 */
	public static final String LOGIN_QUEUE = "login";
	/** 充值监听队列 */
	public static final String RECHAGE_QUEUE = "recharge";
	/**  充值返佣队列*/
	public static final String RECHARGE_REBATE_QUEUE = "recharge-rebate";
	/** 充值VIP自动升级队列 */
	public static final String RECHARGE_VIP_QUEUE = "recharge-vip";
	/**邮件  消息队列（一键领取  异步调用）*/
	public static final String MESSAGE_QUEUE = "message";
	/** 天降财神红包队列 */
	public static final String FORTUNE_QUEUE = "fortune";
	public static final Long COIN_PROPID=1L;//道具id  1代表金币 
	
	public static final Long ROOMCARD_PROPID=2L;//道具id  2代表房卡 
}
