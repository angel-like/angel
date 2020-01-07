package com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.service.impl;

import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.param.MessageRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.common.utils.Constant.MessageContentType;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.dao.MessageManagementDao;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.service.MessageUserService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Transactional
@Service("messageManagementService")
public class MessageManagementServiceImpl extends ServiceImpl<MessageManagementDao, MessageManagementEntity>
		implements MessageManagementService {

	@Autowired
	private MessageUserService messageUserService;

	@Override
	@Transactional
	public void save(MessageManagementEntity messageManagement, Long userId) {
		log.info("[MessageManagementServiceImpl] messageManagement {}", messageManagement);
		messageManagement.setContentType(MessageContentType.USER_CONTENT.getValue());
		this.baseMapper.insertEntityReturnId(messageManagement);
		MessageUserEntity messageUser = new MessageUserEntity();
		messageUser.setMessageId(messageManagement.getId());
		messageUser.setUserAccount(messageManagement.getUserAccount());
		messageUser.setUserId(userId);
		messageUser.setStatus(false);
		messageUser.setDeleteMessage(false);
		messageUser.setContentType(messageManagement.getContentType());
		messageUser.setContent(messageManagement.getContent());
		messageUser.setTitle(messageManagement.getTitle());
		messageUser.setEffectTime(messageManagement.getEffectTime());
		messageUser.setFailureTime(messageManagement.getFailureTime());
		messageUserService.insert(messageUser);
	}

	@Override
	public List<MessageManagementEntity> findMemberMessageByEffect(Integer contentType, Long userId) {
		return this.baseMapper.findMemberMessageByEffect(contentType,userId);
	}

	@Override
	public List<MessageManagementEntity> findMemberMessageByUser(MessageRequestVo vo) {
		return this.baseMapper.findMemberMessageByUser(vo);
	}
}
