package com.xmsy.server.zxyy.calculate.modules.manager.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.xmsy.server.zxyy.calculate.common.annotation.SysLog;
import com.xmsy.server.zxyy.calculate.common.exception.RRException;
import com.xmsy.server.zxyy.calculate.common.utils.Constant;
import com.xmsy.server.zxyy.calculate.common.utils.PageUtils;
import com.xmsy.server.zxyy.calculate.common.utils.R;
import com.xmsy.server.zxyy.calculate.common.validator.ValidatorUtils;
import com.xmsy.server.zxyy.calculate.modules.manager.sys.entity.SysRoleEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.sys.service.SysRoleMenuService;
import com.xmsy.server.zxyy.calculate.modules.manager.sys.service.SysRoleService;

/**
 * 角色管理
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 如果不是超级管理员，则只查询自己创建的角色列表
		if (getUserId() != Constant.SUPER_ADMIN) {
			params.put("createUserId", getUserId());
		}
		PageUtils page = sysRoleService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select() {
		Map<String, Object> map = new HashMap<>();
		// 如果不是超级管理员，则只查询自己所拥有的角色列表
		if (getUserId() != Constant.SUPER_ADMIN) {
			map.put("create_user_id", getUserId());
		}
		List<SysRoleEntity> list = sysRoleService.selectByMap(map);
//		// 不显示代理商（代理商有专门的菜单栏）
//		Iterator<SysRoleEntity> it = list.iterator();
//		SysRoleEntity entity = null;
//		while (it.hasNext()) {
//			entity = it.next();
//			if (Constant.AGENCY_ROLE == entity.getRoleId()) {
//				it.remove();
//			}
//		}
		return R.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId) {
		SysRoleEntity role = sysRoleService.selectById(roleId);
		// 查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		return R.ok().put("role", role);
	}

	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRoleEntity role) {
		ValidatorUtils.validateEntity(role);
		role.setCreateUserId(getUserId());
		sysRoleService.save(role);
		List<SysRoleEntity> result = sysRoleService.selectByMap(ImmutableMap.of("role_name", role.getRoleName()));
		if (CollectionUtils.isNotEmpty(result)) {
			sysRoleMenuService.saveOrUpdate(result.get(0).getRoleId(), role.getMenuIdList());
		}
		return R.ok();
	}

	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRoleEntity role) {
		ValidatorUtils.validateEntity(role);
//		if (Constant.SUPER_ROLE == role.getRoleId() || Constant.FINANCE_ROLE == role.getRoleId()
//				|| Constant.AGENCY_ROLE == role.getRoleId() || Constant.COMMON_ROLE == role.getRoleId()) {
//			throw new RRException("系统的基础角色不能修改");
//		} else {
//			role.setCreateUserId(getUserId());
//			sysRoleService.update(role);
//		}
		role.setCreateUserId(getUserId());
		sysRoleService.update(role);
		return R.ok();
	}

	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds) {
		if (Constant.SUPER_ROLE == roleIds[0] || Constant.FINANCE_ROLE == roleIds[0]
				|| Constant.AGENCY_ROLE == roleIds[0] || Constant.COMMON_ROLE == roleIds[0]) {
			throw new RRException("系统的基础角色不能删除");
		} else {
			sysRoleService.deleteBatch(roleIds);
		}
		return R.ok();
	}
}
