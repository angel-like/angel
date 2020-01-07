package com.xmsy.server.zxyy.calculate.listener.register;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.RegisterMessage;
import com.xmsy.server.zxyy.calculate.constant.SysConstant;
import com.xmsy.server.zxyy.calculate.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.service.UserAgentHierarchyService;
import com.xmsy.server.zxyy.calculate.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.calculate.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationtree.service.UserRecommendationTreeService;
import com.xmsy.server.zxyy.calculate.utils.EventRetryUtil;
import com.xmsy.server.zxyy.calculate.utils.IPQueryUtil;
import com.xmsy.server.zxyy.calculate.utils.InviteCode;

import lombok.extern.slf4j.Slf4j;

/**
 * .监听用户注册操作
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class UserRegisteredListener3 {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private UserRecommendationRecordService userRecommendationRecordService;
	@Autowired
	private UserAgentHierarchyService userAgentHierarchyService;
	@Autowired
	private EventRetryUtil eventRetryUtil;
	@Autowired
	private UserRecommendationTreeService userRecommendationTreeService;

	/**
	 * 监听
	 *
	 * @param receiveMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = SysConstant.REGISTER_QUEUE)
	public void receiveMessage(RegisterMessage registerMessage, Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			if (userRegisterOperation(registerMessage)) {
				// 手动签收
				log.info("UserRegisteredListener3 注册监听事件，接收到消息:{} deliveryTag {}", registerMessage, deliveryTag);
				channel.basicAck(deliveryTag, false);
				SysConstant.MESSAGE.remove(registerMessage.getMessageId());
			} else {
				eventRetryUtil.retry(deliveryTag, registerMessage, channel, "UserRegisteredListener");
			}
		} catch (Exception e) {
			eventRetryUtil.retry(deliveryTag, registerMessage, channel, "UserRegisteredListener");
		}
	}

	@Transactional
	private boolean userRegisterOperation(RegisterMessage registerMessage) {
		// 验证填写的邀请码是否存在,存在就获取上级ID及账号
		Long superiorsId = null;// 上级ID
		String superiorsAccount = null;// 上级账号
		if (!StringUtils.isEmpty(registerMessage.getInvitationCode())) {
			UserRecommendationEntity userRecommendationEntity = validateRecommender(
					registerMessage.getInvitationCode());
			if (null != userRecommendationEntity) {
				UserEntity superiorsEntity = userDao.selectById(userRecommendationEntity.getUserId());
				log.info("[userRegisterOperation3] 上级代理状态是否正常 {}", superiorsEntity.getAgentEnable());
				if (superiorsEntity.getAgentEnable() == true) {// 如果上级是正常代理，那么就有上级ID
					superiorsId = userRecommendationEntity.getUserId();
					superiorsAccount = userRecommendationEntity.getUserAccount();
				}
			}
		}
		String ipData[] = IPQueryUtil.queryIp(registerMessage.getIp());
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(registerMessage.getUserId());
		try {
			if (ipData != null) {
				if(!ipData[0].equals("中国")) {//地址不属于中国
					userInfo.setUserAddressStatus(false);
				}
				userInfo.setUserAddress(ipData[0] + ipData[1] + ipData[2]);
			}
		} catch (Exception e) {
			log.error("[ip解析返回错误]", e);
		}
		if (!StringUtils.isEmpty(userInfo.getUserAddress())) {
			userInfoService.updateUserAddressByUserId(userInfo);
		}
		if (superiorsId != null) {
			// 新增邀请记录并返回id
			userRecommendationRecordService.insertRecord(registerMessage.getUserId(), registerMessage.getUserAccount(),
					registerMessage.getInvitationCode(), superiorsId, superiorsAccount);
			// 修改推荐人的推荐详情
			userRecommendationService.updateSuperiors(superiorsId);
			// 修改用户上级id
			userDao.updateUserSuperiorsId(registerMessage.getUserId(), superiorsId);
		}
		// 新增代理关系树
		userRecommendationTreeService.insertBatch(registerMessage.getUserId(), superiorsId);
		// 获取系统默认代理等级
		UserAgentHierarchyEntity userAgentHierarchy = userAgentHierarchyService.getDefaultHierarchy();
		if (null == userAgentHierarchy) {
			log.error("[userRegisterOperation3] 默认代理等级为空 null == userAgentHierarchy");
			return false;
		}
		int retry = 0;
		while (retry < 3) {
			try {
				//1.校验邀请码是否存在
				Boolean flag=true;
				String newCode="";//邀请码
				while(flag) {
					newCode = InviteCode.createRecommendationCode();
					UserRecommendationEntity selectOne = userRecommendationService
							.selectOne(new EntityWrapper<UserRecommendationEntity>(
									new UserRecommendationEntity().setRecommendationCode(newCode)));
					if(selectOne==null) {
						flag=false;
						break;
					}
				}
				// 为用户新增邀请码
				userRecommendationService.insertRecommendationCode(registerMessage.getUserId(),
						registerMessage.getUserAccount(), newCode, userAgentHierarchy.getId());
				break;
			} catch (Exception e) {
				log.error("[userRegisterOperation3] insertRecommendationCode ", e);
				retry++;
			}
		}
		if (retry >= 3) {
			return false;
		}
		UserEntity user = new UserEntity();
		user.setId(registerMessage.getUserId());
		user.setToken(registerMessage.getToken());
		// 新增一条登陆成功记录
		UserLoginEntity userLogin = new UserLoginEntity();
		userLogin.setUserId(registerMessage.getUserId());
		userLogin.setIp(registerMessage.getIp());
		try {
			if (ipData != null) {
				userLogin.setNation(ipData[0]);
				userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				user.setRegisterIpAddress(ipData[0] + ipData[1] + ipData[2]);
			}
		} catch (Exception e) {
			log.error("[ip解析返回错误]", e);
		}
		userLogin.setHallId(registerMessage.getHallId());
		userLogin.setToken(registerMessage.getToken());
		userLogin.setLoginStatus(SysConstant.SUCCESS);
		userLogin.setDeviceType(registerMessage.getDeviceType());
		userLogin.setDeviceCode(registerMessage.getRegisterDeviceCode());
		userLogin.setEdition(registerMessage.getEdition());
		userDao.updateById(user);
		userLoginService.insert(userLogin);
		return true;

	}

	// 通过推荐码获取人员ID
	private UserRecommendationEntity validateRecommender(String invitationCode) {
		log.info("[userRegisterOperation3] validateRecommender {}", invitationCode);
		UserRecommendationEntity entity = new UserRecommendationEntity();
		entity.setRecommendationCode(invitationCode);
		UserRecommendationEntity userRecommendationEntity = userRecommendationService
				.selectOne(new EntityWrapper<UserRecommendationEntity>(entity));
		return userRecommendationEntity;
	}
}
