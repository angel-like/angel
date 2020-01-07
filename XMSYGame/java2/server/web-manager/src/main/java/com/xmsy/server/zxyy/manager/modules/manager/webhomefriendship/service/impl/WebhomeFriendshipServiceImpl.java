package com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.dao.WebhomeFriendshipDao;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.entity.WebhomeFriendshipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.service.WebhomeFriendshipService;


@Service("webhomeFriendshipService")
public class WebhomeFriendshipServiceImpl extends ServiceImpl<WebhomeFriendshipDao, WebhomeFriendshipEntity> implements WebhomeFriendshipService {

	@Override
	public List<WebhomeFriendshipEntity> selectFriendshipList() {
		// TODO Auto-generated method stub
		return baseMapper.selectFriendshipList();
	}


}
