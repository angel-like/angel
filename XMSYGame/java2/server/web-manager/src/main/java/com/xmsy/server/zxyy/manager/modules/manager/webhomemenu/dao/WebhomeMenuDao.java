package com.xmsy.server.zxyy.manager.modules.manager.webhomemenu.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomemenu.entity.MenuEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomemenu.entity.WebhomeMenuEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 首页菜单管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 14:39:28
 */
@Mapper
public interface WebhomeMenuDao extends BaseMapper<WebhomeMenuEntity> {
	List<MenuEntity> selectListByWeb();
}
