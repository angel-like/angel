package com.xmsy.server.zxyy.game.mq;

import java.util.UUID;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xmsy.common.bean.message.BaseMessage;
import com.xmsy.server.zxyy.game.config.RabbitmqConfig;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .mq客户端，发送信息
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class MqClient {

	public static final int retry_time = 3;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 注册消息推送
	 * 
	 * @param message
	 */
	public void gamePush() {
		BaseMessage message = new BaseMessage();
		String messageId = UUID.randomUUID().toString();
		message.setMessageId(messageId);
		message.setCreateTime(DateUtil.now());
		int retry = 0;
		while (retry < retry_time) {
			try {
				rabbitTemplate.convertAndSend("", RabbitmqConfig.GAME_QUEUE, message, new CorrelationData(messageId));
				break;
			} catch (Exception e) {
				retry++;
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					log.error("[游戏管理信息更新推送] registerPush message {}", message, e);
				}
				log.error("[游戏管理信息更新推送] Thread.sleep(500L); ");
			}
		}
		log.info("[游戏管理信息更新推送] message {}", message);
	}
}
