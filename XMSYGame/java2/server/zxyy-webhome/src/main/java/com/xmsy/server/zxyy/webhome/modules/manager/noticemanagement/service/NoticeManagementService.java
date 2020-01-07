package com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.entity.NoticeManagementEntity;

/**
 * 消息管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-03 17:26:35
 */
public interface NoticeManagementService extends IService<NoticeManagementEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<Map<String, Object>> findMemberNoticeListByEffect(String token);
}

