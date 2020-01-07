package com.xmsy.server.zxyy.manager.modules.app.event;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;
import com.xmsy.server.zxyy.manager.modules.app.event.def.ClientMessage;
import com.xmsy.server.zxyy.manager.modules.app.event.def.GameServerMessage;
import com.xmsy.server.zxyy.manager.modules.app.event.def.MessageDef;
import com.xmsy.server.zxyy.manager.modules.app.event.def.MessageEntity;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.manager.modules.app.hierarchy.param.HierarchyRateResult;

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
			log.info("[pushUserInfo] message {}",message);
			log.info("[pushUserInfo] message {}", JSON.toJSONString(message));
			String result = HttpRequest.post(HallUrlConstant.getPUSH_URL()).timeout(5000)
					.body(JSON.toJSONString(message)).execute().body();
			log.info("[pushUserInfo] result {}", result);
		} catch (Exception e) {
			log.error("[pushUserInfo]", e);
		}
	}
	
	/**
	 * 推送用户信息
	 */
	public void pushServerUserInfo(UserInfoMessage userInfoMessage) {
		try {
			GameServerMessage server = new GameServerMessage(userInfoMessage);
			MessageEntity message = new MessageEntity(server);
			log.info("[pushUserInfo] message {}",message);
			log.info("[pushUserInfo] message {}", JSON.toJSONString(message));
			String result = HttpRequest.post(HallUrlConstant.getPUSH_URL()).timeout(5000)
					.body(JSON.toJSONString(message)).execute().body();
			log.info("[pushUserInfo] result {}", result);
		} catch (Exception e) {
			log.error("[pushUserInfo]", e);
		}
	}
	
	/**
	 * 剔除用户
	 */
	public void tickUser(Long userId) {
		tickUser(userId,null);
	}
	
	/**
	 * 剔除用户
	 */
	public void tickUser(Long userId,String msg) {
		JSONObject param = new JSONObject();
		param.put("Uid", userId);
		if(!StringUtils.isEmpty(msg)) {
			param.put("Err", msg);
		}
		try {
			String result = HttpRequest.post(HallUrlConstant.getKICK_URL()).timeout(5000).body(param.toString()).execute().body();
			log.info("剔除用户 返回结果：{}",result);
			log.info("剔除用户 返回结果：{}",result);
		} catch (Exception e) {
			log.error("官网登入发送uid到大厅", e);
//			throw new RRException(ErrorCode.UserErrorCode.HALL_REQUEST_ERRO.getErrMsg(),
//					ErrorCode.UserErrorCode.HALL_REQUEST_ERRO.getCode());
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

	/**
	 * 推送层级胜率变更
	 */
	public void pushHierarchyRateList(List<HierarchyRateResult> hierarchyRateList) {
		try {
			log.info("[pushHierarchyRateList] hierarchyRateList {}", JSON.toJSONString(hierarchyRateList));
			String result = HttpRequest.post(HallUrlConstant.getPUSH_RATEURL()).timeout(5000)
					.body(JSON.toJSONString(hierarchyRateList)).execute().body();
			log.info("[pushHierarchyRateList] result {}", result);
		} catch (Exception e) {
			log.error("[pushHierarchyRateList]", e);
		}
	}
	public static void main(String[] args) {
		UserInfoMessage userInfoMessage =new UserInfoMessage();
		userInfoMessage.setUid(1000l);
		userInfoMessage.setCoin(12000L);
		new PushService().pushUserInfo(userInfoMessage);
	}
}
