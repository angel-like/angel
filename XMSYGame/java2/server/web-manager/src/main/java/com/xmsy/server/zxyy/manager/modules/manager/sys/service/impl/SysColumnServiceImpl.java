/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xmsy.server.zxyy.manager.modules.manager.sys.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.sys.dao.SysColumnDao;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysColumnEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysColumnService;


@Service("sysColumnService")
public class SysColumnServiceImpl extends ServiceImpl<SysColumnDao, SysColumnEntity> implements SysColumnService {


	@Override
	public List<SysColumnEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysColumnEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}

		List<SysColumnEntity> userMenuList = new ArrayList<>();
		for(SysColumnEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysColumnEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}



	@Override
	public List<SysColumnEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}



	@Override
	public void delete(Long menuId){

		this.deleteById(menuId);

	}

	@Override
	public void updateStatus(SysColumnEntity sysColumnEntity) {
		if(sysColumnEntity.getStatus()){
			sysColumnEntity.setStatus(false);
		}else {
			sysColumnEntity.setStatus(true);
		}
		this.updateById(sysColumnEntity);
	}


	@Override
	public List<SysColumnEntity> queryAllSon(Long parentId) {
		//获取一级节点
		@SuppressWarnings("unused")
		List<SysColumnEntity> sysColumnEntities = queryListParentId(parentId);




		return null;
	}
	/**
	 * 获取所有菜单列表
	 */
	@SuppressWarnings("unused")
	private List<SysColumnEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysColumnEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysColumnEntity> getMenuTreeList(List<SysColumnEntity> menuList,List<Long> menuIdList){
		List<SysColumnEntity> subMenuList = new ArrayList<SysColumnEntity>();
		
		for(SysColumnEntity entity : menuList){
			//目录

				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(),menuIdList),menuIdList));

			subMenuList.add(entity);
		}
		
		return subMenuList;
	}


	/**
	 * 获取某个菜单列表
	 */
	@SuppressWarnings("unused")
	private List<SysColumnEntity> getAllMenuList( Long parentId){
		//查询根菜单列表
		List<SysColumnEntity> menuList = queryListParentId(parentId);
		//递归获取子菜单
		//递归获取子菜单
		getMenuTreeList(menuList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysColumnEntity> getMenuTreeList(List<SysColumnEntity> menuList){
		List<SysColumnEntity> subMenuList = new ArrayList<SysColumnEntity>();

		for(SysColumnEntity entity : menuList){
			//目录

			entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId())));

			subMenuList.add(entity);
		}

		return subMenuList;
	}


}
