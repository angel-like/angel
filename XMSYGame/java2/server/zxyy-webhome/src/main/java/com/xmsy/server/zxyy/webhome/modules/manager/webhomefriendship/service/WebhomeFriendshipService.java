package com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendship.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendship.entity.WebhomeFriendshipEntity;

/**
 * 友情链接管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-07 11:02:39
 */
public interface WebhomeFriendshipService extends IService<WebhomeFriendshipEntity> {

	List<WebhomeFriendshipEntity> selectFriendshipList();

}

