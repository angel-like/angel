package com.xmsy.server.zxyy.webhome.modules.manager.userold.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.userold.dao.UserOldDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userold.entity.UserOldEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userold.service.UserOldService;


@Service("userOldService")
public class UserOldServiceImpl extends ServiceImpl<UserOldDao, UserOldEntity> implements UserOldService {


}
