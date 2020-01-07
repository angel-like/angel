package com.xmsy.network.jpush.push;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * .极光推送
 * 
 * @author chenjisi
 * @since 2017年8月13日
 */
public class JiguangPush {

	private static final Logger log = LoggerFactory.getLogger(JiguangPush.class);
	// private static String masterSecret = "2e35eff7c456111525103034";
	// private static String appKey = "580f76bdf049ac7ea00d920d";
	// private static final String ALERT = "钢炮xxx？";
	// private static final String REGISTRATION_Id = "1507bfd3f7b5961449f";

	/**
	 * 极光推送
	 */
	public static void main(String args[]) {
		// log.info("对注册id" + REGISTRATION_Id + "的用户推送信息");
		// GlobalResult result = pushNotification(appKey, masterSecret, REGISTRATION_Id,
		// ALERT);
		// if (ResultUtils.isSuccess(result)) {
		// log.info("对注册id" + REGISTRATION_Id + "的信息推送成功！");
		// } else {
		// log.error("对注册id" + REGISTRATION_Id + "的信息推送失败！result {}", result);
		// }

	}
	/**
	 * 极光推送广播
	 */
	// public static void main(String args[]) {
	// log.info("=================广播======================");
	// PushResult broadcastResult = pushBroadcast(appKey, masterSecret, ALERT);
	// System.out.println(broadcastResult);
	// }

	/**
	 * 生成极光推送对象PushPayload（采用java SDK）
	 * 
	 * @param alias
	 * @param alert
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_android_ios_alias_alert(String alias, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.addPlatformNotification(
								IosNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(false)// true-推送生产环境
																			// false-推送开发环境（测试使用参数）
						.setTimeToLive(90)// 消息在JPush服务器的失效时间（测试使用参数）
						.build())
				.build();
	}

	/**
	 * 生成极光推送对象PushPayload（采用java SDK）
	 * 
	 * @param registrationId
	 * @param alert
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_android_ios_registrationId_alert(String registrationId, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios())
				.setAudience(Audience.registrationId(registrationId))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.addPlatformNotification(
								IosNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true)// true-推送生产环境
																		// false-推送开发环境（测试使用参数）
						.setTimeToLive(864000)// 消息在JPush服务器的失效时间（测试使用参数）
						.build())
				.build();
	}

	/**
	 * 生成极光推送对象PushPayload（采用java SDK）
	 * 
	 * @param registrationId
	 * @param alert
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_android_ios_registrationIds_alert(Collection<String> registrationIds,
			String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios())
				.setAudience(Audience.registrationId(registrationIds))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.addPlatformNotification(
								IosNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true)// true-推送生产环境
																		// false-推送开发环境（测试使用参数）
						.setTimeToLive(864000)// 消息在JPush服务器的失效时间（测试使用参数）
						.build())
				.build();
	}

	/**
	 * .广播模式
	 * 
	 * @param alert
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_android_ios_all_Broadcast(String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.addPlatformNotification(
								IosNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true)// true-推送生产环境
																		// false-推送开发环境（测试使用参数）
						.setTimeToLive(864000)// 消息在JPush服务器的失效时间（测试使用参数）
						.build())
				.build();
	}

	/**
	 * .自定义消息模式
	 * 
	 * @param alert
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_android_ios_Message(String message, String registrationId) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.registrationId(registrationId))
				.setMessage(Message
						.newBuilder().setMsgContent(message).setTitle("").addExtra("message extras key", "").build())
				.setOptions(Options.newBuilder().setApnsProduction(true)// true-推送生产环境
																		// false-推送开发环境（测试使用参数）
						.setTimeToLive(864000)// 消息在JPush服务器的失效时间（测试使用参数）
						.build())
				.build();
	}

	/**
	 * 极光推送通知方法,多个用户
	 * 
	 * @param registrationIds
	 * @param alert
	 * @return PushResult
	 */
	public static GlobalResult<String> pushNotification(String appKey, String appSecret, String registrationId,
			String alert) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(appSecret, appKey, null, clientConfig);
		PushPayload payload = buildPushObject_android_ios_registrationId_alert(registrationId, alert);
		try {
			PushResult result = jpushClient.sendPush(payload);
			if (result == null) {
				return ResultUtils.getErrorResult("PushResult is null");
			}
			if (result.isResultOK()) {
				return ResultUtils.getSuccessResult();
			} else {
				return ResultUtils.getErrorResult(result.getOriginalContent());
			}
		} catch (APIConnectionException e) {
			log.error("appKey {},appSecret {}, registrationIds {}", appKey, appSecret, registrationId, e);
			log.error("Connection error. Should retry later. ", e);
			return ResultUtils.getErrorResult(e.getMessage());
		} catch (APIRequestException e) {
			log.error("appKey {},appSecret {}, registrationIds {}", appKey, appSecret, registrationId, e);
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			return ResultUtils.getErrorResult(e.getErrorMessage());
		}
	}

	/**
	 * 极光推送通知全部用户
	 * 
	 * @param alert
	 * @return GlobalResult
	 */
	public static GlobalResult<String> pushNotificationBroadcast(String appKey, String appSecret, String alert) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(appSecret, appKey, null, clientConfig);
		PushPayload payload = buildPushObject_android_ios_all_Broadcast(alert);
		try {
			PushResult result = jpushClient.sendPush(payload);
			if (result == null) {
				return ResultUtils.getErrorResult("PushResult is null");
			}
			if (result.isResultOK()) {
				return ResultUtils.getSuccessResult();
			} else {
				return ResultUtils.getErrorResult(result.getOriginalContent());
			}
		} catch (APIConnectionException e) {
			log.error("appKey {},appSecret {}, alert {}", appKey, appSecret, alert, e);
			log.error("Connection error. Should retry later. ", e);
			return ResultUtils.getErrorResult(e.getMessage());
		} catch (APIRequestException e) {
			log.error("appKey {},appSecret {}, alert {}", appKey, appSecret, alert, e);
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			return ResultUtils.getErrorResult(e.getErrorMessage());
		}
	}

	/**
	 * 极光推送通知方法,单个用户
	 * 
	 * @param registrationId
	 * @param alert
	 * @return PushResult
	 */
	public static GlobalResult<String> pushNotifications(String appKey, String appSecret,
			Collection<String> registrationIds, String alert) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(appSecret, appKey, null, clientConfig);
		PushPayload payload = buildPushObject_android_ios_registrationIds_alert(registrationIds, alert);
		try {
			PushResult result = jpushClient.sendPush(payload);
			if (result == null) {
				return ResultUtils.getErrorResult("PushResult is null");
			}
			if (result.isResultOK()) {
				return ResultUtils.getSuccessResult();
			} else {
				return ResultUtils.getErrorResult(result.getOriginalContent());
			}
		} catch (APIConnectionException e) {
			log.error("appKey {},appSecret {}, registrationIds {}", appKey, appSecret, registrationIds, e);
			log.error("Connection error. Should retry later. ", e);
			return ResultUtils.getErrorResult(e.getMessage());
		} catch (APIRequestException e) {
			log.error("appKey {},appSecret {}, registrationIds {}", appKey, appSecret, registrationIds, e);
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			return ResultUtils.getErrorResult(e.getErrorMessage());
		}
	}

	/**
	 * 极光推送自定义消息
	 * 
	 * @param registrationId
	 * @param alert
	 * @return PushResult
	 */
	public static PushResult pushMessage(String appKey, String appSecret, String registrationId, String message) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(appSecret, appKey, null, clientConfig);
		PushPayload payload = buildPushObject_android_ios_Message(message, registrationId);
		try {
			return jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
			return null;
		} catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			return null;
		}
	}

	/**
	 * 极光推送自定义消息
	 * 
	 * @param registrationId
	 * @param alert
	 * @return PushResult
	 */
	public static PushResult pushBroadcast(String appKey, String appSecret, String message) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(appSecret, appKey, null, clientConfig);
		PushPayload payload = buildPushObject_android_ios_all_Broadcast(message);
		try {
			return jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
			return null;
		} catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			return null;
		}
	}
}
