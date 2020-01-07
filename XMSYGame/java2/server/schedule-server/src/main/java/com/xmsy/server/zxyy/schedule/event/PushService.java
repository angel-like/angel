package com.xmsy.server.zxyy.schedule.event;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xmsy.server.zxyy.schedule.event.param.ClientMessage;
import com.xmsy.server.zxyy.schedule.event.param.GameServerMessage;
import com.xmsy.server.zxyy.schedule.event.param.MessageDef;
import com.xmsy.server.zxyy.schedule.event.param.MessageEntity;
import com.xmsy.server.zxyy.schedule.event.param.UserInfoMessage;
import com.xmsy.server.zxyy.schedule.utils.SysConstant;

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
			MessageEntity message = new MessageEntity(server);
			log.info("[pushUserInfo] message {}", JSON.toJSONString(message));
			String result = HttpRequest.post(SysConstant.getPUSH_URL()).timeout(5000)
					.body(JSON.toJSONString(message)).execute().body();
			log.info("[pushUserInfo] result {}", result);
		} catch (Exception e) {
			log.error("[pushUserInfo]", e);
		}
	}
	public void pushUserInfoForClient(UserInfoMessage userInfoMessage) {
		try {
			GameServerMessage server = new GameServerMessage(userInfoMessage);
			ClientMessage client = new ClientMessage(MessageDef.USER_EXCHANGER_MESSAGE, System.nanoTime(),
					userInfoMessage);
			MessageEntity message = new MessageEntity(server, client);
			log.info("[pushUserInfoForClient] message {}", JSON.toJSONString(message));
			String result = HttpRequest.post(SysConstant.getPUSH_URL()).timeout(5000)
					.body(JSON.toJSONString(message)).execute().body();
			log.info("[pushUserInfoForClient] result {}", result);
		} catch (Exception e) {
			log.error("[pushUserInfoForClient]", e);
		}
	}
	

	public static void main(String[] args) {
		UserInfoMessage userInfoMessage =new UserInfoMessage();
		userInfoMessage.setUid(1000l);
		userInfoMessage.setHierarchyId(10L);
		new PushService().pushUserInfo(userInfoMessage);
	}
}
