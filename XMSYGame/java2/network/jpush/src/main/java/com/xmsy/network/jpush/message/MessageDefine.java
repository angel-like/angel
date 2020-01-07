package com.xmsy.network.jpush.message;

/**
 * .消息类型定义
 * 
 * @author chenjisi
 * @since 2017年8月22日
 */
public class MessageDefine {
	
	//-----------------------PushMessage信息模块code定义---------------------
	
	public static final String ORDER = "100"; // 订单模块 code=100
	public static final String COUPON = "200"; // 优惠卷模块 code=200
	public static final String PAYMENT = "100"; // 支付模块 code=300

	
	//-----------------------PushMessage信息类型type定义---------------------
	
	public static final String READ = "1"; // 订单模块 type=1
	public static final String UNREAD = "2"; // 订单模块 type=2
}
