package com.xmsy.server.zxyy.manager.modules.manager.userquestion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.userquestion.dao.UserQuestionDao;
import com.xmsy.server.zxyy.manager.modules.manager.userquestion.entity.UserQuestionEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userquestion.service.UserQuestionService;
import org.springframework.stereotype.Service;

@Service("userQuestionService")
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionDao, UserQuestionEntity> implements UserQuestionService {



}