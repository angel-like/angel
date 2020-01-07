package com.xmsy.server.zxyy.game.modules.manager.sys.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.common.exception.RRException;
import com.xmsy.server.zxyy.game.common.utils.Constant;
import com.xmsy.server.zxyy.game.common.utils.PageUtils;
import com.xmsy.server.zxyy.game.common.utils.Query;
import com.xmsy.server.zxyy.game.common.utils.SpringUtil;
import com.xmsy.server.zxyy.game.modules.manager.sys.dao.SysUserDao;
import com.xmsy.server.zxyy.game.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.game.modules.manager.sys.entity.param.SysUserDetail;
import com.xmsy.server.zxyy.game.modules.manager.sys.service.SysRoleService;
import com.xmsy.server.zxyy.game.modules.manager.sys.service.SysUserRoleService;
import com.xmsy.server.zxyy.game.modules.manager.sys.service.SysUserService;

/**
 * 系统用户
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String) params.get("username");
		Long createUserId = (Long) params.get("createUserId");

		Page<SysUserEntity> page = this.selectPage(new Query<SysUserEntity>(params).getPage(),
				new EntityWrapper<SysUserEntity>().like(StringUtils.isNotBlank(username), "username", username)
						.eq(createUserId != null, "create_user_id", createUserId));

		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		// sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		baseMapper.insertUser(user);
		// 检查角色是否越权
		checkRole(user);
		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(null);
		} else {
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		List<Long> roles = user.getRoleIdList();
		if (!CollectionUtils.isEmpty(roles)) {
			user.setRoleIds(Joiner.on(",").join(roles));
		}
		this.updateById(user);
		// 检查角色是否越权
		checkUpdateRole(user);
		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		this.deleteBatchIds(Arrays.asList(userId));
		sysUserRoleService.deleteByMap(ImmutableMap.of("user_id", userId[0]));
	}

	@Override
	@Transactional
	public void deleteAgency(Long userId) {
		baseMapper.deleteById(userId);
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}

	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user) {
		if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
			return;
		}
		// 如果不是超级管理员，则需要判断用户的角色是否自己创建
		if (user.getCreateUserId() == Constant.SUPER_ADMIN) {
			return;
		}
		SysRoleService sysRoleService = SpringUtil.getApplicationContext().getBean(SysRoleService.class);
		// 查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());
		// 判断是否越权
		if (!roleIdList.containsAll(user.getRoleIdList())) {
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}

	/**
	 * 检查角色是否越权
	 */
	private void checkUpdateRole(SysUserEntity user) {
		SysUserEntity entity = baseMapper.selectById(user.getUserId());
		if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
			return;
		}
		// 如果不是超级管理员，则需要判断用户的角色是否自己创建
		if (entity.getCreateUserId() == Constant.SUPER_ADMIN) {
			return;
		}
		SysRoleService sysRoleService = SpringUtil.getApplicationContext().getBean(SysRoleService.class);
		// 查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(entity.getCreateUserId());
		// 判断是否越权
		if (!roleIdList.containsAll(user.getRoleIdList())) {
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}

	@Override
	public PageUtils findSysUserDetails(PageParam pageParam, SysUserDetail sysUserDetail) {
		Page<SysUserDetail> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return new PageUtils(page.setRecords(this.baseMapper.findSysUserDetails(page, sysUserDetail)));
	}

	@Override
	@Transactional
	public void saveAgency(SysUserEntity user) {
		user.setCreateTime(new Date());
		// sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		baseMapper.insertUser(user);
		List<Long> roleIdList = Lists.newArrayList(Constant.AGENCY_ROLE);
		// 检查角色是否越权
		checkRole(user);
		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), roleIdList);
	}

	@Override
	@Transactional
	public void updateAgency(SysUserEntity user) {
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(null);
		} else {
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		baseMapper.updateById(user);
	}

}
