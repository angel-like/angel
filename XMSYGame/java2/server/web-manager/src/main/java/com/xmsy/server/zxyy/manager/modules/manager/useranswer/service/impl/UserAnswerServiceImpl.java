package com.xmsy.server.zxyy.manager.modules.manager.useranswer.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.useranswer.dao.UserAnswerDao;
import com.xmsy.server.zxyy.manager.modules.manager.useranswer.entity.UserAnswerEntity;
import com.xmsy.server.zxyy.manager.modules.manager.useranswer.service.UserAnswerService;



@Service("userAnswerService")
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerDao, UserAnswerEntity> implements UserAnswerService {


}