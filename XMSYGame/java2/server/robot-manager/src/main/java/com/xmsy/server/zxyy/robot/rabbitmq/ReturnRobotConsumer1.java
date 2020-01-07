package com.xmsy.server.zxyy.robot.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xmsy.server.zxyy.robot.common.validator.ValidatorUtils;
import com.xmsy.server.zxyy.robot.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.manager.robot.service.RobotService;
import com.xmsy.server.zxyy.robot.rabbitmq.param.RobotParam;

import lombok.extern.slf4j.Slf4j;

/**
 * .机器人归还操作
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class ReturnRobotConsumer1 {
	@Autowired
    private RobotService robotService;
	/**
	 * 监听
	 *
	 * @param receiveMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = SysConstant.LISTENER_QUEUE)
	public void receiveMessage(String receiveMessage, Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			// 手动签收
			JSONObject obj=JSONObject.parseObject(new String(message.getBody()));
			RobotParam entity=obj.toJavaObject(RobotParam.class);
	    	ValidatorUtils.validateEntity(entity, AddGroup.class);
	    	robotService.recyclingRobot(entity);
			channel.basicAck(deliveryTag, false);
			SysConstant.MESSAGE.remove(deliveryTag);
		} catch (Exception e) {
			try {
				log.info("ReturnRobotConsumer1 接收到消息/开始回收机器人:{} deliveryTag {}", receiveMessage, deliveryTag);
				// 失败重试
				Integer retry = SysConstant.MESSAGE.get(deliveryTag);
				if (null == retry) {
					SysConstant.MESSAGE.put(deliveryTag, 0);
					channel.basicReject(deliveryTag, true);
					return;
				} else if (SysConstant.RETRY_TIME < retry) {
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
					log.error("ReturnRobotConsumer1 接收到消息之后重试5次仍然失败.receiveMessage {}", receiveMessage);
					return;
				} else {
					channel.basicReject(deliveryTag, true);
					SysConstant.MESSAGE.put(deliveryTag, SysConstant.MESSAGE.get(deliveryTag) + 1);
					return;
				}
			} catch (IOException e1) {
				log.error("ReturnRobotConsumer1 签收异常.", e1);
			}
		}
	}
}
