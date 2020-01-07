package com.xmsy.server.zxyy.webhome.modules.manager.playertasks.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.dao.PlayerTasksDao;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.service.PlayerTasksService;


@Service("playerTasksService")
public class PlayerTasksServiceImpl extends ServiceImpl<PlayerTasksDao, PlayerTasksEntity> implements PlayerTasksService {

	@Override
	public List<PlayerTasksEntity> queryByType(String type) {
		return baseMapper.queryByType(type);
	}

	@Override
	public Integer queryPropNum(Long taskId) {
		return baseMapper.queryPropNum(taskId);
	}


}
