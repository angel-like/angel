package com.xmsy.server.zxyy.manager.modules.manager.playertasks.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.playertasks.dao.PlayerTasksDao;
import com.xmsy.server.zxyy.manager.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.manager.modules.manager.playertasks.service.PlayerTasksService;


@Service("playerTasksService")
public class PlayerTasksServiceImpl extends ServiceImpl<PlayerTasksDao, PlayerTasksEntity> implements PlayerTasksService {


}
