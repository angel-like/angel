package com.xmsy.server.zxyy.manager.modules.manager.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.common.validator.Assert;
import com.xmsy.server.zxyy.manager.common.validator.ValidatorUtils;
import com.xmsy.server.zxyy.manager.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.manager.common.validator.group.UpdateGroup;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.service.ProxyTransferRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysRoleEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.param.SysUserDetail;
import com.xmsy.server.zxyy.manager.modules.manager.sys.form.PasswordForm;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysRoleService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysUserRoleService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysUserService;
import com.xmsy.server.zxyy.manager.utils.DateUtils;

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
	@Autowired
	private ProxyTransferRecordService proxyTransferRecordService;

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
	//=====================================代理商====================================
	/**
	 * 获取代理商列表
	 * @param pageParam
	 * @param sysUserDetail
	 * @return
	 */
	@GetMapping("/listProxyAgent")
	@RequiresPermissions("sys:user:list")
	public R listProxyAgent(PageParam pageParam, SysUserEntity sysUser) {
		Page<SysUserEntity> page = new Page<SysUserEntity>(pageParam.getPage(), pageParam.getLimit());
		sysUser.setRoleIds(SysConstant.PROXYID);//默认查询代理商
		Page<SysUserEntity> pageList = sysUserService.selectPage(page, new EntityWrapper<SysUserEntity>(sysUser));
		return R.ok().put("page", new PageUtils(pageList));
	}
	
	/**
	 * 修改代理商
	 */
	@SysLog("修改代理商")
	@PostMapping("/updateproxy")
	@RequiresPermissions("sys:user:update")
	public R updateproxy(@RequestBody SysUserEntity user) {
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(null);
		} else {
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		
		sysUserService.updateById(user);
		return R.ok();
	}
	/**
	 * 保存代理商     --删除直接调用上面的删除方法就行
	 */
	@SysLog("保存代理商")
	@PostMapping("/saveproxy")
	@RequiresPermissions("sys:user:save")
	public R saveproxy(@RequestBody SysUserEntity user) {
		user.setCreateUserId(getUserId());
		sysUserService.saveProxy(user);
		return R.ok();
	}
	
	 /**
     * 启用
     */
    @RequestMapping("/openEnable/{id}")
    @RequiresPermissions("sys:user:openEnable")
    public R openEnable(@PathVariable("id") Long id){
    	SysUserEntity user = new SysUserEntity();
    	user.setUserId(id);;
    	user.setStatus(1);
    	sysUserService.updateById(user);
        return R.ok();
    }
    /**
     * 禁用
     */
    @RequestMapping("/closeEnable/{id}")
    @RequiresPermissions("sys:user:closeEnable")
    public R closeEnable(@PathVariable("id") Long id){
    	SysUserEntity user = new SysUserEntity();
    	user.setUserId(id);;
    	user.setStatus(0);
    	sysUserService.updateById(user);
        return R.ok();
    }
	//======================================Home页面统计==============================
	@GetMapping("/proxylogininfo")//获取当前登录的代理商信息
	public R proxyLoginInfo() {
		SysUserEntity sysUser = getUser();//获取当前登录的代理商实体
		Map<String,Object> map=new HashMap<String,Object>();
		if(sysUser.getRoleIds().equals(SysConstant.PROXYID)) {
			map.put("proxyAccount", sysUser.getMobile());
			map.put("proxyName", sysUser.getUsername());
			map.put("proxyBalance", sysUser.getProxyBalance());
		}
		return R.ok().put("map",map);
	}
	
	/**
	 * 代理商 今日划拨统计
	 */
	@RequestMapping("/sumTodayRechargeAmount")
	//@RequiresPermissions("takemoneystatistics:takemoneystatistics:sumTakeMoney")
	public R sumTodayRechargeAmount() {//Tue Jul 23 16:23:04 GMT+08:00 2019     格式化为字符串的"2019-07-23"
		SysUserEntity sysUser = getUser();
		Map<String, Object> map = proxyTransferRecordService.sumTransferMoneyAmount(DateUtils.format(DateUtils.getToday()),
				null,sysUser.getUserId());
		return R.ok().put("map", map);
	}
	
	/**
	 * 代理商 昨日划拨统计
	 */
	@RequestMapping("/sumYesterdayTransferMoneyAmount")
	//@RequiresPermissions("takemoneystatistics:takemoneystatistics:sumTakeMoney")
	public R sumYesterdayTransferMoneyAmount() {//Tue Jul 23 16:23:04 GMT+08:00 2019     格式化为字符串的"2019-07-23"
		SysUserEntity sysUser = getUser();
		Map<String, Object> map = proxyTransferRecordService.sumTransferMoneyAmount(DateUtils.format(DateUtils.getYesterday()), 
				DateUtils.format(DateUtils.getYesterday()),sysUser.getUserId());
		return R.ok().put("map", map);
	}
	/**
	 * 代理商 七日划拨统计
	 */
	@RequestMapping("/sumWeekTransferMoneyAmount")
	//@RequiresPermissions("takemoneystatistics:takemoneystatistics:sumTakeMoney")
	public R sumWeekTransferMoneyAmount() {//Tue Jul 23 16:23:04 GMT+08:00 2019     格式化为字符串的"2019-07-23"
		SysUserEntity sysUser = getUser();
		Map<String, Object> map = proxyTransferRecordService.sumTransferMoneyAmount(DateUtils.format(DateUtils.getDay(-6)), 
				null,sysUser.getUserId());
		return R.ok().put("map", map);
	}
	
	/**
	 * 代理商 总划拨统计
	 */
	@RequestMapping("/sumTotalTransferMoneyAmount")
	//@RequiresPermissions("takemoneystatistics:takemoneystatistics:sumTakeMoney")
	public R sumTotalTransferMoneyAmount() {//Tue Jul 23 16:23:04 GMT+08:00 2019     格式化为字符串的"2019-07-23"
		SysUserEntity sysUser = getUser();
		Map<String, Object> map = proxyTransferRecordService.sumTransferMoneyAmount(null, null,sysUser.getUserId());
		return R.ok().put("map", map);
	}
	
	
}
