package com.xmsy.server.zxyy.calculate.listener.fortune;


import java.util.Date;

import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.BaseMessage;
import com.xmsy.common.bean.message.FortuneMessage;
import com.xmsy.server.zxyy.calculate.common.utils.DateUtils;
import com.xmsy.server.zxyy.calculate.constant.SysConstant;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.service.EnvelopeRecordService;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.service.EnvelopeTaskConfigService;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.service.FortuneActiviConfigService;
import com.xmsy.server.zxyy.calculate.modules.manager.push.PushService;
import com.xmsy.server.zxyy.calculate.modules.manager.push.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.calculate.utils.EventRetryUtil;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.EnvelopeTaskConfigEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;

/**
 * .监听财神降临
 * 
 * @author aye
 *
 */

@Component
public class fortuneActivityListener1 {
	@Autowired
	private EventRetryUtil eventRetryUtil;
	@Autowired
    private FortuneActiviConfigService fortuneActiviConfigService;
	@Autowired
    private EnvelopeTaskConfigService envelopeTaskConfigService;
	@Autowired
    private EnvelopeRecordService envelopeRecordService;
	@Resource
	private PushService pushService;
	/**
	 * 监听
	 *
	 * @param receiveMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = SysConstant.FORTUNE_QUEUE)
	public void receiveMessage(FortuneMessage fortuneMessage, Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		//System.out.println(fortuneMessage.getMessageId()+"---"+fortuneMessage.getUserId());
		try {
			//1.获取当前时间 在 活动时间内   （只有一个活动，但有多个事件）
			Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>();
			entityWrapper.le("start_time", DateUtils.formatTime(new Date()));
			entityWrapper.ge("end_time", DateUtils.formatTime(new Date()));
			FortuneActiviConfigEntity fortuneActivi = fortuneActiviConfigService.selectOne(entityWrapper);
			if(fortuneActivi==null) {
				channel.basicAck(deliveryTag, false);//消息确认
				return;
			}
			//2.通过活动id+事件码  获取活动对应的事件    红包任务后台配置
			EnvelopeTaskConfigEntity EnvelopeTaskParam= new EnvelopeTaskConfigEntity();
			EnvelopeTaskParam.setActivityId(fortuneActivi.getId());
			EnvelopeTaskParam.setEventCode(fortuneMessage.getEventCode());
			EnvelopeTaskConfigEntity EnvelopeTask = envelopeTaskConfigService
					.selectOne(new EntityWrapper<EnvelopeTaskConfigEntity>(EnvelopeTaskParam));
			if(EnvelopeTask==null) {//事件被删掉 直接返回
				channel.basicAck(deliveryTag, false);//消息确认
				return;
			}
			//3.1 获取在活动区间     用户的所有打码数据得到的红包    + 现有的红包  =判断是否能再新增红包  
			Integer unEnvelopeNum=0;
			if(fortuneMessage.getValidBet()!=null&&fortuneMessage.getValidBet()!=0) {
				unEnvelopeNum=envelopeRecordService.gainValidBetEnvelope(fortuneMessage.getUserId(),fortuneActivi, EnvelopeTask);
			}
			//3.2 获取在活动区间  用户累计充值金额   ...
			if(fortuneMessage.getRechargeAmount()!=null&&fortuneMessage.getRechargeAmount()>0) {
				unEnvelopeNum+=envelopeRecordService.gainRechargeAmountEnvelope(fortuneMessage.getUserId(),fortuneActivi, EnvelopeTask,fortuneMessage.getRechargeAmount());
			}
			//3.3 获取在活动区间  用户累计分享游戏次数
			//4 推送新增的红包个数
			if(unEnvelopeNum>0) {
				UserInfoMessage userInfoMessage=new UserInfoMessage();
				userInfoMessage.setUid(fortuneMessage.getUserId());//会员id
				userInfoMessage.setUnEnvelopeNum(unEnvelopeNum);//新获得的未开启红包数量
				pushService.pushUserInfo(userInfoMessage);
			}
			channel.basicAck(deliveryTag, false);//消息确认
		} catch (Exception e) {
			BaseMessage baseMessage = new BaseMessage();
			baseMessage.setMessageId(fortuneMessage.getMessageId().toString());
			eventRetryUtil.retry(deliveryTag,baseMessage, channel, "fortuneActivityListener1");
		}
		
	}
}
