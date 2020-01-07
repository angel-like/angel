package com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.entity.EnclosureResult;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.entity.WebhomeEnclosureEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件附件表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-31 15:05:47
 */
public interface WebhomeEnclosureService extends IService<WebhomeEnclosureEntity> {

    PageUtils queryPage(Map<String, Object> params);

	Long insertGetId(WebhomeEnclosureEntity entity);
    List<EnclosureResult> getGroupInfo();
}

