package com.xmsy.server.zxyy.robot.modules.manager.sys.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.robot.common.exception.RRException;
import com.xmsy.server.zxyy.robot.common.utils.Constant;
import com.xmsy.server.zxyy.robot.common.utils.PageUtils;
import com.xmsy.server.zxyy.robot.common.utils.Query;
import com.xmsy.server.zxyy.robot.modules.manager.sys.dao.SysRoleDao;
import com.xmsy.server.zxyy.robot.modules.manager.sys.dao.SysUserDao;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysRoleEntity;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysRoleMenuService;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysRoleService;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysUserRoleService;

/**
 * 角色
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String roleName = (String) params.get("roleName");
		Long createUserId = (Long) params.get("createUserId");

		Page<SysRoleEntity> page = this.selectPage(new Query<SysRoleEntity>(params).getPage(),
				new EntityWrapper<SysRoleEntity>().like(StringUtils.isNotBlank(roleName), "role_name", roleName)
						.eq(createUserId != null, "create_user_id", createUserId));

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysRoleEntity role) {
		role.setCreateTime(new Date());
		this.insert(role);

		// 检查权限是否越权
		checkPrems(role);

		// 保存角色与菜单关系
		// sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysRoleEntity role) {
		this.updateById(role);

		// 检查权限是否越权
		checkUpdatePrems(role);

		// 更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] roleIds) {
		// 删除角色
		this.deleteBatchIds(Arrays.asList(roleIds));

		// 删除角色与菜单关联
		sysRoleMenuService.deleteBatch(roleIds);

		// 删除角色与用户关联
		sysUserRoleService.deleteBatch(roleIds);
	}

	@Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return baseMapper.queryRoleIdList(createUserId);
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleEntity role) {
		// 如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if (role.getCreateUserId() == Constant.SUPER_ADMIN) {
			return;
		}

		// 查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserDao.queryAllMenuId(role.getCreateUserId());

		// 判断是否越权
		if (!menuIdList.containsAll(role.getMenuIdList())) {
			throw new RRException("新增角色的权限，已超出你的权限范围");
		}
	}
	/**
	 * 检查权限是否越权
	 */
	private void checkUpdatePrems(SysRoleEntity role) {
		SysRoleEntity entity=baseMapper.selectById(role.getRoleId());
		// 如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if (entity.getCreateUserId() == Constant.SUPER_ADMIN) {
			return;
		}
		// 查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserDao.queryAllMenuId(entity.getCreateUserId());
		// 判断是否越权
		if (!menuIdList.containsAll(entity.getMenuIdList())) {
			throw new RRException("超出该角色权限创建人权限");
		}
	}
}
