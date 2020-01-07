package com.xmsy.server.zxyy.webhome.modules.app.userquestion.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.app.userquestion.dao.UserQuestionDao;
import com.xmsy.server.zxyy.webhome.modules.app.userquestion.entity.UserQuestionEntity;
import com.xmsy.server.zxyy.webhome.modules.app.userquestion.service.UserQuestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userQuestionService")
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionDao, UserQuestionEntity> implements UserQuestionService {


    @Override
    public List<Map<String, Object>> selectAll() {
        return this.baseMapper.selectAll();
    }
}