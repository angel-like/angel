package com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.entity.WebhomeFriendshipTemplateEntity;

/**
 * 友情链接内容
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-13 15:31:14
 */
public interface WebhomeFriendshipTemplateService extends IService<WebhomeFriendshipTemplateEntity> {

	List<WebhomeFriendshipTemplateEntity> selectTemplateList(Long friendshipId);

}
