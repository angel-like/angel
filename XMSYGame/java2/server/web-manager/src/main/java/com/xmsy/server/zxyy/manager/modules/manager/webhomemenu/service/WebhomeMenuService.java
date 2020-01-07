package com.xmsy.server.zxyy.manager.modules.manager.webhomemenu.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.webhomemenu.entity.WebhomeMenuEntity;

/**
 * 首页菜单管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-02 14:39:28
 */
public interface WebhomeMenuService extends IService<WebhomeMenuEntity> {

    
    List<Object> selectMenuList();
    //获取游戏类菜单列表
	List<WebhomeMenuEntity> gameMenuList();
}

