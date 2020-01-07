package com.xmsy.server.zxyy.calculate.listener.login;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.LoginMessage;
import com.xmsy.server.zxyy.calculate.common.annotation.QrOrderConfirmRepeat;
import com.xmsy.server.zxyy.calculate.constant.SysConstant;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.calculate.modules.manager.push.PushService;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.calculate.utils.EventRetryUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * .监听用户登录操作
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class UserLoginListener1 {

	@Autowired
	private EventRetryUtil eventRetryUtil;
	@Autowired
	private MessageManagementService messageManagementService;
	@Autowired
	private MessageUserService messageUserService;
	@Autowired
	private UserService userService;
	@Resource
	private PushService pushService;
	/*@Autowired
	private FortuneActiviConfigService fortuneActiviConfigService;
	@Autowired
	private EnvelopeRecordService envelopeRecordService;*/

	/**
	 * 监听
	 *
	 * @param receiveMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = SysConstant.LOGIN_QUEUE)
	public void loginMessage(LoginMessage loginMessage, Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			if (userLogin(loginMessage)) {
				// 手动签收
				log.info("UserLoginListener1 登录监听消息时间，接收到消息:{} deliveryTag {}", loginMessage, deliveryTag);
				channel.basicAck(deliveryTag, false);
				SysConstant.MESSAGE.remove(loginMessage.getMessageId());
			} else {
				eventRetryUtil.retry(deliveryTag, loginMessage, channel, "UserLoginListener1");
			}
		} catch (Exception e) {
			eventRetryUtil.retry(deliveryTag, loginMessage, channel, "UserLoginListener1");
		}




	}

	@QrOrderConfirmRepeat
	public boolean userLogin(LoginMessage loginMessage) {
		// 保存邮件
		UserEntity user = this.userService.selectById(loginMessage.getUserId());
//		MessageRequestVo vo = new MessageRequestVo();
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(1);
	   String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
	   requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(user.getId());
      requestVo.setUserAccount(user.getAccount());
	  requestVo.setRegisterDate(user.getCreateTime());
//		List<MessageManagementEntity> memberMessage = messageManagementService.findMemberMessageByEffect(1, loginMessage.getUserId());
		List<MessageManagementEntity> memberMessage = messageManagementService.findMemberMessageByUser(requestVo);
		if(memberMessage.size()>0&&memberMessage!=null) {
			for (MessageManagementEntity messageManagementEntity : memberMessage) {
				MessageUserEntity messageUser = new MessageUserEntity();
				messageUser.setMessageId(messageManagementEntity.getId());
				messageUser.setUserAccount(loginMessage.getUserAccount());
				messageUser.setUserId(loginMessage.getUserId());
				messageUser.setContentType(messageManagementEntity.getContentType());
				messageUser.setContent(messageManagementEntity.getContent());
				messageUser.setTitle(messageManagementEntity.getTitle());
				messageUser.setEffectTime(messageManagementEntity.getEffectTime());
				messageUser.setFailureTime(messageManagementEntity.getFailureTime());
				messageUser.setStatus(false);
				messageUser.setDeleteMessage(false);
				messageUserService.insert(messageUser);
			}
		}
		/*//获取当前时间 在 活动时间内   （只有一个活动，但有多个事件）   -因为这边游服未链接就推送，所以无效  全部注释掉
		Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>();
		entityWrapper.le("start_time", new Date());
		entityWrapper.ge("end_time", new Date());
		FortuneActiviConfigEntity fortuneActiviConfig = fortuneActiviConfigService.selectOne(entityWrapper);
		//查询活动期间未开启红包个数
		Integer unEnvelopeNum=0;
		if(fortuneActiviConfig!=null) {
			Wrapper<EnvelopeRecordEntity> wrapper = new EntityWrapper<EnvelopeRecordEntity>(new EnvelopeRecordEntity()
					.setUserId(user.getId()).setActivityId(fortuneActiviConfig.getId()).setStatus(0));
			unEnvelopeNum = envelopeRecordService.selectCount(wrapper);
		}
		UserInfoMessage userInfoMessage = new UserInfoMessage();
		userInfoMessage.setAccount(loginMessage.getUserAccount());
		userInfoMessage.setUid(loginMessage.getUserId());
		userInfoMessage.setUnReadNum(memberMessage.size());
		userInfoMessage.setUnEnvelopeNum(unEnvelopeNum);
		pushService.pushUserInfo(userInfoMessage);*/
		return true;
	}
}
