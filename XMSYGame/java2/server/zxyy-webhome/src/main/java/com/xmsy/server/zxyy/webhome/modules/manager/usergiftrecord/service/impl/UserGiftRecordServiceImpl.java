package com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.dao.UserGiftRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.service.UserGiftRecordService;

import lombok.extern.slf4j.Slf4j;

@Service("userGiftRecordService")
@Transactional
@Slf4j
public class UserGiftRecordServiceImpl extends ServiceImpl<UserGiftRecordDao, UserGiftRecordEntity>
		implements UserGiftRecordService {

	@Autowired
	private UserService userService;

	@Autowired
	private MessageUserService messageUserService;
	
	@Autowired
	private PushService pushService;

	@Override
	public int insertGetId(UserGiftRecordEntity entity) {
		return this.baseMapper.insertGetId(entity);
	}

	@Override
	public UserGiftRecordEntity queryGiftRecord(Long userId, Long activityId) {
		return baseMapper.queryGiftRecord(userId, activityId);
	}

	@Override
	public void UserPropReceived(Long messageId, Long userId) {
		UserEntity user = userService.selectById(userId);
		MessageUserEntity messageUserParam = new MessageUserEntity();
		messageUserParam.setUserId(userId);
		messageUserParam.setMessageId(messageId);
		MessageUserEntity messageUser = messageUserService.selectById(messageId);

		if (messageUser.getActivityType() == Constant.ACTIVITYTYPE_1) {
			if (messageUser.getReceive()) {
				return;
			}
			// 将是否领取改成true
			messageUser.setReceive(true);
			messageUser.updateById();
			UserGiftRecordEntity userGiftRecordParam = new UserGiftRecordEntity();
			userGiftRecordParam.setId(messageUser.getActivityId());
			userGiftRecordParam.setUserId(messageUser.getUserId());
			UserGiftRecordEntity userGiftRecordEntity = this
					.selectOne(new EntityWrapper<UserGiftRecordEntity>(userGiftRecordParam));
			if (userGiftRecordEntity.getReceive()) {
				return;
			}
			// 将是否领取改成true
			userGiftRecordEntity.setReceive(true);
			userGiftRecordEntity.updateById();
			if (userGiftRecordEntity.getType() == Constant.ACTIVITYTYPE_1) {
				int detailType = 0;
				if (userGiftRecordEntity.getDetailType() == Constant.UserActivityAwardType.MATCHAWARD.getValue()) {
					detailType = Constant.TransactionDetailType.USERMATCHAWARD.getValue();
				} else if (userGiftRecordEntity.getDetailType() == Constant.UserActivityAwardType.SHARE.getValue()) {
					detailType = Constant.TransactionDetailType.USERSHARE.getValue();
				} else if (userGiftRecordEntity.getDetailType() == Constant.UserActivityAwardType.MAKEFRIENDS
						.getValue()) {
					detailType = Constant.TransactionDetailType.USERMATCHAWARD.getValue();
				} else if (userGiftRecordEntity.getDetailType() == Constant.UserActivityAwardType.RELIEF.getValue()) {
					detailType = Constant.TransactionDetailType.USERRELIEF.getValue();
				} else if (userGiftRecordEntity.getDetailType() == Constant.UserActivityAwardType.ENCOURAGE
						.getValue()) {
					detailType = Constant.TransactionDetailType.USERENCOURAGE.getValue();
				}
				userService.updateUserCoin(user, userGiftRecordEntity.getNum().longValue(),
						Constant.TransactionType.REBATE.getValue(), detailType);
				UserEntity userUpdateParam = new UserEntity();
				userUpdateParam.setId(user.getId());
				userUpdateParam.setCoin(userGiftRecordEntity.getNum().longValue());
				UserInfoMessage message = new UserInfoMessage(userUpdateParam,null);
				log.info("[邮件中领取金币道具-推送消息] message {}", message);
				pushService.pushUserInfo(message);
			}
		}

	}

}
