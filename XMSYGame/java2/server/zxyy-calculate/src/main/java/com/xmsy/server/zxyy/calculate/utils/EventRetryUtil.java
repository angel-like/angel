package com.xmsy.server.zxyy.calculate.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.BaseMessage;
import com.xmsy.server.zxyy.calculate.cache.LocalContentCache;
import com.xmsy.server.zxyy.calculate.constant.SysConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 重试操作
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class EventRetryUtil {

	@Autowired
	private LocalContentCache localContentCache;

	public final void retry(Long deliveryTag, BaseMessage receiveMessage, Channel channel, String className) {
		if (null == deliveryTag || null == receiveMessage || null == channel) {
			log.error("[EventRetryUtil] retry deliveryTag {} channel {} receiveMessage {} methodName {}", deliveryTag,
					channel, receiveMessage, className);
			return;
		}
		try {
			// 失败重试
			Integer retry = (Integer) localContentCache.get(receiveMessage.getMessageId());
			if (null == retry) {
				localContentCache.put(receiveMessage.getMessageId(), 0);
				channel.basicReject(deliveryTag, true);
				return;
			} else if (SysConstant.RETRY_TIME < retry) {
				channel.basicAck(deliveryTag, false);
				log.error("接收到消息之后重试5次仍然失败.receiveMessage {} methodName {}", receiveMessage, className);
				return;
			} else {
				channel.basicReject(deliveryTag, true);
				localContentCache.put(receiveMessage.getMessageId(),
						Integer.parseInt(localContentCache.get(receiveMessage.getMessageId()).toString()) + 1);
				return;
			}
		} catch (IOException e) {
			log.error("签收异常.methodName {}", className, e);
		}

	}

}
