package com.xmsy.server.zxyy.payment.service.listener;

import java.io.IOException;


import com.xmsy.server.zxyy.payment.service.constant.PayConstant;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.server.zxyy.payment.service.constant.SysConfigConstant;

import lombok.extern.slf4j.Slf4j;


/**
 * .监听支付配置变化
 *
 * @author aleng
 *
 */
@Slf4j
@Component
public class PayConfigListener {

	public static final String PAY_CONFIG_QUEUE = "sysconfig";
	/*@Resource
	private PayConfigRedis payConfigRedis;*/

	/**
	 * 监听游戏服游戏信息更新
	 *
	 * @param
	 *
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = PAY_CONFIG_QUEUE)
	public void receiveMessage(SysConfigMessage sysConfigMessage, Message message, Channel channel) {
		log.info("[SysConfigMessage]->data {}",sysConfigMessage);
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			if(sysConfigMessage.getType()==0){
				updatePayConfig(sysConfigMessage);
				channel.basicAck(deliveryTag, false);
			}
			else if(sysConfigMessage.getType()==1){
				/*PayConstant.CALLBACK_IP = payConfigRedis.get("callbackIp");*/
				PayConstant.CALLBACK_IP.put(sysConfigMessage.getAliasName(),sysConfigMessage.getCallbackIp());
				/*payConfigRedis.delete("callbackIp");
				payConfigRedis.saveOrUpdate(PayConstant.CALLBACK_IP);*/
				log.info("[payConfigRedis]->data {}",PayConstant.CALLBACK_IP);
			}

		} catch (IOException e) {
			log.error("receiveMessage", e);
		}
	}

	private boolean updatePayConfig(SysConfigMessage sysConfigMessage) {
		if (SysConfigConstant.PAY.equals(sysConfigMessage.getName())) {
			PayServiceBase.payServiceInit(sysConfigMessage.getChildren(), PayConstant.getPAY_SERVICE_CALLBACK_URL());
		}
		return true;
	}
}
