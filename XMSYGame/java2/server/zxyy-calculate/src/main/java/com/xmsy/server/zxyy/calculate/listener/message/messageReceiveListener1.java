package com.xmsy.server.zxyy.calculate.listener.message;


import com.xmsy.network.redis.utils.RedisLockUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.BaseMessage;
import com.xmsy.server.zxyy.calculate.constant.SysConstant;
import com.xmsy.server.zxyy.calculate.utils.EventRetryUtil;
/*import com.xmsy.server.zxyy.calculate.common.exception.RRException;
import com.xmsy.server.zxyy.calculate.common.utils.ErrorCode;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.entity.MessageManagementEntity;*/
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.entity.MessageUserEntity;
import  com.xmsy.server.zxyy.calculate.modules.manager.usergiftrecord.service.UserGiftRecordService;

/**
 * .监听用户注册操作
 * 
 * @author Administrator
 *
 */

@Component
public class messageReceiveListener1 {
	@Autowired
	private EventRetryUtil eventRetryUtil;
	/*@Autowired
	private MessageManagementService messageManagementService;*/
	@Autowired
	private UserGiftRecordService UserGiftRecordService;
	@Autowired
	private RedisLockUtil redisUtil;
	/**
	 * 监听
	 *
	 * @param receiveMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = SysConstant.MESSAGE_QUEUE)
	public void receiveMessage(MessageUserEntity messageUser, Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		//System.out.println(messageUser.getMessageId()+"---"+messageUser.getUserAccount());
		try {
			/*//1.先判断消息id
			MessageManagementEntity messageManagementEntity = messageManagementService.selectById(messageUser.getId());
			if (messageManagementEntity == null || messageManagementEntity.getId() <= 0) {
				throw new RRException(ErrorCode.messageCode.MESSAGE_ID_ERRO.getErrMsg(),
						ErrorCode.messageCode.MESSAGE_ID_ERRO.getCode());
			}*/
			//去调用其 小玲写的接口
			UserGiftRecordService.UserPropReceived(messageUser.getId(), messageUser.getUserId());
			redisUtil.releaseLock(messageUser.getUserId().toString()+"MessageProp",messageUser.getUserId().toString());
			channel.basicAck(deliveryTag, false);//消息确认
		} catch (Exception e) {
			BaseMessage baseMessage = new BaseMessage();
			baseMessage.setMessageId(messageUser.getMessageId().toString());
			eventRetryUtil.retry(deliveryTag,baseMessage, channel, "messageReceiveListener1");
		}
		
	}
}
