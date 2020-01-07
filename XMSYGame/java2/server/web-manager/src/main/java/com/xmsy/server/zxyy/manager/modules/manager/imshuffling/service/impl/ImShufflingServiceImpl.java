package com.xmsy.server.zxyy.manager.modules.manager.imshuffling.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.imshuffling.dao.ImShufflingDao;
import com.xmsy.server.zxyy.manager.modules.manager.imshuffling.entity.ImShufflingEntity;
import com.xmsy.server.zxyy.manager.modules.manager.imshuffling.service.ImShufflingService;

@Service("imShufflingService")
public class ImShufflingServiceImpl extends ServiceImpl<ImShufflingDao, ImShufflingEntity>
		implements ImShufflingService {

}
