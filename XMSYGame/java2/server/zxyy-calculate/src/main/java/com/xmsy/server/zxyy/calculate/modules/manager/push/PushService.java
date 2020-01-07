package com.xmsy.server.zxyy.calculate.modules.manager.push;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xmsy.server.zxyy.calculate.constant.HallUrlConstant;
import com.xmsy.server.zxyy.calculate.modules.manager.push.def.ClientMessage;
import com.xmsy.server.zxyy.calculate.modules.manager.push.def.GameServerMessage;
import com.xmsy.server.zxyy.calculate.modules.manager.push.def.MessageDef;
import com.xmsy.server.zxyy.calculate.modules.manager.push.def.MessageEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.push.userinfo.UserInfoMessage;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * .发送推送信息
 * 
 * @author aleng
 *
 */
@Slf4j
@Component
public class PushService {

	/**
	 * 推送用户信息
	 */
	public void pushUserInfo(UserInfoMessage userInfoMessage) {
		try {
			GameServerMessage server = new GameServerMessage(userInfoMessage);
			ClientMessage client = new ClientMessage(MessageDef.USER_INFO_MESSAGE, System.nanoTime(), userInfoMessage);
			MessageEntity message = new MessageEntity(server, client);
			log.info("[pushUserInfo] message {}", JSON.toJSONString(message));
//			String result = HttpRequest.post("http://10.0.0.104:8500/msgtoclient").timeout(5000)
//					.body(JSON.toJSONString(message)).execute().body();
			String result = HttpRequest.post(HallUrlConstant.getPUSH_URL()).timeout(5000)
					.body(JSON.toJSONString(message)).execute().body();
			log.info("[pushUserInfo] result {}", result);
		} catch (Exception e) {
			log.error("[pushUserInfo]", e);
		}
	}

	/**
	 * 推送额度转换
	 */
	public void pushExchange(UserInfoMessage userInfoMessage) {
		try {
			GameServerMessage server = new GameServerMessage(userInfoMessage);
			ClientMessage client = new ClientMessage(MessageDef.USER_EXCHANGER_MESSAGE, System.nanoTime(),
					userInfoMessage);
			MessageEntity message = new MessageEntity(server, client);
			log.info("[pushExchange] message {}", JSON.toJSONString(message));
			String result = HttpRequest.post(HallUrlConstant.getPUSH_URL()).timeout(5000)
					.body(JSON.toJSONString(message)).execute().body();
			log.info("[pushExchange] result {}", result);
		} catch (Exception e) {
			log.error("[pushExchange]", e);
		}
	}
	
	public static void main(String[] args) {
		UserInfoMessage userInfoMessage =new UserInfoMessage();
		userInfoMessage.setUid(1000l);
		userInfoMessage.setCurrentRechargeAmount(1000l);
		userInfoMessage.setVipId(1l);
		userInfoMessage.setVipName("vip1");
		userInfoMessage.setNextVipId(2l);
		userInfoMessage.setNextVipName("vip2");
		new PushService().pushUserInfo(userInfoMessage);
	}

//	public static void main(String[] args) {
//		new PushService().pushUserInfo(null);
//	}
}
