package com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.entity.WebContactManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.entity.WebhomeContactEntity;

import java.util.List;
import java.util.Map;

/**
 * 联系管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-01 16:52:13
 */
public interface WebhomeContactService extends IService<WebhomeContactEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<WebContactManagementEntity> selectListByWeb();
}

