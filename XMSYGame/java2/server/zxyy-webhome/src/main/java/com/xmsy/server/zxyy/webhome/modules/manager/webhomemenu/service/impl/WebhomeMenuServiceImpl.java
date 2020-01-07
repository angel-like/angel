package com.xmsy.server.zxyy.webhome.modules.manager.webhomemenu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenu.dao.WebhomeMenuDao;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenu.entity.MenuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenu.entity.WebhomeMenuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenu.service.WebhomeMenuService;

import cn.hutool.json.JSONArray;


@Service("webhomeMenuService")
public class WebhomeMenuServiceImpl extends ServiceImpl<WebhomeMenuDao, WebhomeMenuEntity> implements WebhomeMenuService {
	@Autowired
	private LocalContentCache localContentCache;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectMenuList() {
		List<Object> arry = new ArrayList<Object>();// 一级
		 //判断缓存中是否存在菜单，不存在则往缓存中新增
		if (!StringUtils.isEmpty(localContentCache.get(SysConstant.MENU))) {
			arry = (List<Object>) localContentCache.get(SysConstant.MENU);
		} else {

			List<MenuEntity> menulist = baseMapper.selectListByWeb();// 从缓存中取到菜单列表

			JSONArray childrenArry = new JSONArray();// 一级菜单
			JSONObject childrenobj = new JSONObject();

			JSONObject lastobj = new JSONObject();

			// 第一次循环获得第一级菜单
			for (MenuEntity websiteMenuEntity : menulist) {

				if (websiteMenuEntity.getType() == SysConstant.ONELEVEL) {

					
					childrenobj = (JSONObject) JSON.toJSON(websiteMenuEntity);
					childrenArry = new JSONArray();
					// 再次循环，比较一级菜单id是否为当前数据的父节点
					for (MenuEntity entity : menulist) {
						if (String.valueOf(entity.getParentId()).equals(websiteMenuEntity.getId().toString())) {
							// 如果是父节点，将该条数据加入到一级菜单下
							lastobj = (JSONObject) JSON.toJSON(entity);
							childrenArry.add(lastobj);
						}
					}
					// 如果子节点不为空，则添加为children
					if (!childrenArry.isEmpty()) {
						childrenobj.put("children", childrenArry);
						arry.add(childrenobj);
					} else {// 否则直接将该对象保存
						arry.add(childrenobj);
					}
				}
			}
			localContentCache.put(SysConstant.MENU, arry);
		}
		return arry;
	}

	@Override
	public List<WebhomeMenuEntity> gameMenuList() {
		WebhomeMenuEntity entity=new WebhomeMenuEntity();
		entity.setParentId(SysConstant.FATHER);
		baseMapper.selectList(new EntityWrapper<WebhomeMenuEntity>(entity));
		return baseMapper.selectList(new EntityWrapper<WebhomeMenuEntity>(entity));
	}

}
