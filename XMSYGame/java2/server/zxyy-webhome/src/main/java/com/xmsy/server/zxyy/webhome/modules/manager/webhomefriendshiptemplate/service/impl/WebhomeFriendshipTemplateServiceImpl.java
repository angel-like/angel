package com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.dao.WebhomeFriendshipTemplateDao;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.entity.WebhomeFriendshipTemplateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.service.WebhomeFriendshipTemplateService;

@Service("webhomeFriendshipTemplateService")
public class WebhomeFriendshipTemplateServiceImpl
		extends ServiceImpl<WebhomeFriendshipTemplateDao, WebhomeFriendshipTemplateEntity>
		implements WebhomeFriendshipTemplateService {

	@Override
	public List<WebhomeFriendshipTemplateEntity> selectTemplateList(Long friendshipId) {
		return baseMapper.selectTemplateList(friendshipId);
	}

}
