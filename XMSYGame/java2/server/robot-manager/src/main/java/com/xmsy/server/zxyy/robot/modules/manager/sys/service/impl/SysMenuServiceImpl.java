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

package com.xmsy.server.zxyy.robot.modules.manager.sys.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.robot.common.utils.Constant;
import com.xmsy.server.zxyy.robot.common.utils.MapUtils;
import com.xmsy.server.zxyy.robot.modules.manager.sys.dao.SysMenuDao;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysMenuEntity;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysRoleMenuEntity;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysMenuService;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysRoleMenuService;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysUserService;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public void delete(Long menuId){
		//删除菜单
		this.deleteById(menuId);
		//删除菜单与角色关联
		sysRoleMenuService.deleteByMap(new MapUtils().put("menu_id", menuId));
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();
		
		for(SysMenuEntity entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}

	@Override
	public List<SysMenuEntity> getRooleMenuList(Long userId) {
		List<Long> rooleId=new ArrayList<Long>();
		SysUserEntity user=sysUserService.selectById(userId);
		String[] str = user.getRoleIds().split(",");
		Long[] rooleIds = (Long[]) ConvertUtils.convert(str,Long.class);
		List<SysRoleMenuEntity> menuRoole=sysRoleMenuService.selectBatchRooleIds(rooleIds);
		if(CollectionUtils.isEmpty(menuRoole)) {
			return null;
		}
		for(SysRoleMenuEntity entity:menuRoole) {
			rooleId.add(entity.getMenuId());
		}
		return baseMapper.selectBatchIds(rooleId);
	}
}
