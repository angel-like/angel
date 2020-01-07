package com.xmsy.server.zxyy.calculate.modules.manager.messageuser.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.dao.MessageUserDao;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.service.MessageUserService;

@Transactional
@Service("messageUserService")
public class MessageUserServiceImpl extends ServiceImpl<MessageUserDao, MessageUserEntity>
		implements MessageUserService {
}
