package com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.dao.UserGameNumberDao;
import com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.entity.UserGameNumberEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.service.UserGameNumberService;


@Service("userGameNumberService")
public class UserGameNumberServiceImpl extends ServiceImpl<UserGameNumberDao, UserGameNumberEntity> implements UserGameNumberService {


}
