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

package com.xmsy.server.zxyy.manager.modules.manager.sys.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysColumnEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysColumnService;

/**
 * 栏目管理
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/column")
public class SysColumnController extends AbstractController {
	@Autowired
	private SysColumnService sysColumnService;

	/**
	 * 所有栏目列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:column:list")
	public List<SysColumnEntity> list(){
//		List<sysColumnEntity> menuList = sysColumnService.getUserMenuList(getUserId());
		List<SysColumnEntity> menuList = sysColumnService.selectList(null);
		for(SysColumnEntity sysColumnEntity : menuList){
			SysColumnEntity parentMenuEntity = sysColumnService.selectById(sysColumnEntity.getParentId());
			if(parentMenuEntity != null){
				sysColumnEntity.setParentName(parentMenuEntity.getName());
			}
		}

		return menuList;
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:column:select")
	public R select(){
		//查询列表数据
		List<SysColumnEntity> menuList = sysColumnService.queryNotButtonList();
		
		//添加顶级菜单
		SysColumnEntity root = new SysColumnEntity();
		root.setMenuId(0L);
		root.setName("一级栏目");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:column:info")
	public R info(@PathVariable("menuId") Long menuId){
		SysColumnEntity menu = sysColumnService.selectById(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存栏目")
	@PostMapping("/save")
	@RequiresPermissions("sys:column:save")
	public R save(@RequestBody SysColumnEntity menu){
		//数据校验
		verifyForm(menu);
		
		sysColumnService.insert(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改栏目")
	@PostMapping("/update")
	@RequiresPermissions("sys:column:update")
	public R update(@RequestBody SysColumnEntity menu){
		//数据校验
		verifyForm(menu);
				
		sysColumnService.updateById(menu);
		
		return R.ok();
	}
	/**
	 * 修改
	 */
	@SysLog("栏目状态更新")
	@GetMapping("/updateStatus/{menuId}")
	@RequiresPermissions("sys:column:update")
	public R updateStatus(@PathVariable("menuId") Long menuId){
		//判断是否有子菜单或按钮
		SysColumnEntity menu = sysColumnService.selectById(menuId);
		       sysColumnService.updateStatus(menu);

		List<SysColumnEntity> menuList = sysColumnService.queryAllSon(menuId);
		if(menuList.size() > 0&&menuList!=null){
			for (SysColumnEntity sysColumnEntity : menuList) {
				sysColumnEntity.setStatus(menu.getStatus());
				sysColumnService.updateById(sysColumnEntity);
			}

		}



		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除栏目")
	@PostMapping("/delete/{menuId}")
	@RequiresPermissions("sys:column:delete")
	public R delete(@PathVariable("menuId") long menuId){


		//判断是否有子菜单或按钮
		List<SysColumnEntity> menuList = sysColumnService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单");
		}

		sysColumnService.delete(menuId);

		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysColumnEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("栏目名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级栏目不能为空");
		}
		

			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("链接地址不能为空");
			}


			return ;
		}

}
