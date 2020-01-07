package com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.entity.WebhomeMenuTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单模板表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 19:32:58
 */
public interface WebhomeMenuTemplateService extends IService<WebhomeMenuTemplateEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    //根据菜单Id获取列表
    List<WebhomeMenuTemplateEntity> selectListForMenuId(Long menuId);
}

