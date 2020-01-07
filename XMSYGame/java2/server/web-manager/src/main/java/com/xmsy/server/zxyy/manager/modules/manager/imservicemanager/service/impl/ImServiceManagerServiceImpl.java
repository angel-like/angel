package com.xmsy.server.zxyy.manager.modules.manager.imservicemanager.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.imservicemanager.dao.ImServiceManagerDao;
import com.xmsy.server.zxyy.manager.modules.manager.imservicemanager.entity.ImServiceManagerEntity;
import com.xmsy.server.zxyy.manager.modules.manager.imservicemanager.service.ImServiceManagerService;

@Service("imServiceManagerService")
public class ImServiceManagerServiceImpl extends ServiceImpl<ImServiceManagerDao, ImServiceManagerEntity>
		implements ImServiceManagerService {

}
