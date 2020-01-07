package com.xmsy.server.zxyy.webhome.modules.manager.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.common.validator.Assert;
import com.xmsy.server.zxyy.webhome.common.validator.ValidatorUtils;
import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.common.validator.group.UpdateGroup;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysRoleEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.param.SysUserDetail;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.form.PasswordForm;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.service.SysRoleService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.service.SysUserRoleService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.service.SysUserService;

/**
 * 系统用户
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(PageParam pageParam, SysUserDetail sysUserDetail) {
		// 只有超级管理员，才能查看所有管理员列表
		Map<String, Object> map = new HashMap<>();
		if (getUserId() != Constant.SUPER_ADMIN) {
			sysUserDetail.setCreateUserId(getUserId());
			// map.put("create_user_id", getUserId());
		}
		List<SysRoleEntity> sysRoles = sysRoleService.selectByMap(map);
		PageUtils result = sysUserService.findSysUserDetails(pageParam, sysUserDetail);
		@SuppressWarnings("unchecked")
		List<SysUserDetail> sysUserDetails = (List<SysUserDetail>) result.getList();
		String[] roleIds = null;
		for (SysUserDetail user : sysUserDetails) {
			roleIds = user.getRoleIds() == null ? new String[] {} : user.getRoleIds().split(",");
			setRoleName(roleIds, sysRoles, user);
		}
		return R.ok().put("page", result).put("roles", sysRoles);
	}

	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info() {
		return R.ok().put("user", getUser());
	}

	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public R password(@RequestBody PasswordForm form) {
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");
		// sha256加密
		String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
		// sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();
		// 更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if (!flag) {
			return R.error("原密码不正确");
		}
		return R.ok();
	}

	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId) {
		SysUserEntity user = sysUserService.selectById(userId);
		// 获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		return R.ok().put("user", user);
	}

	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user) {
		ValidatorUtils.validateEntity(user, AddGroup.class);
		user.setCreateUserId(getUserId());
		List<Long> roles = user.getRoleIdList();
		if (!CollectionUtils.isEmpty(roles)) {
			user.setRoleIds(Joiner.on(",").join(roles));
		} else {
			user.setRoleIds(Constant.COMMON_ROLE.toString());
			roles.add(Constant.COMMON_ROLE);
		}
		sysUserService.save(user);
		// List<SysUserEntity> result =
		// sysUserService.selectByMap(ImmutableMap.of("username", user.getUsername()));
		// if (CollectionUtils.isNotEmpty(result)) {
		// sysUserRoleService.saveOrUpdate(result.get(0).getUserId(),
		// user.getRoleIdList());
		// }
		return R.ok();
	}

	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user) {
		ValidatorUtils.validateEntity(user, UpdateGroup.class);
		if (getUserId().equals(user.getUserId())) {
			return R.error("不可修改自己权限");
		}
		sysUserService.update(user);
		return R.ok();
	}

	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds) {
		if (ArrayUtils.contains(userIds, 1L)) {
			return R.error("系统管理员不能删除");
		}

		if (ArrayUtils.contains(userIds, getUserId())) {
			return R.error("当前用户不能删除");
		}

		sysUserService.deleteBatch(userIds);

		return R.ok();
	}

	private void setRoleName(String[] roleIds, List<SysRoleEntity> sysRoles, SysUserDetail user) {
		List<String> roleNames = Lists.newArrayList();
		for (String roleId : roleIds) {
			for (SysRoleEntity role : sysRoles) {
				if (roleId.equals(role.getRoleId().toString())) {
					roleNames.add(role.getRoleName());
				}
			}
		}
		user.setRoleNames(Joiner.on(",").join(roleNames));
	}
}
