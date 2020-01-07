package com.xmsy.server.zxyy.manager.modules.manager.sys.service.impl;

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
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.sys.dao.SysUserDao;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserRoleEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.param.SysUserDetail;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysRoleService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysUserRoleService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysUserService;

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
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserDao sysUserDao;

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
		if(user.getPassword()!=null){
			// sha256加密
			String salt = RandomStringUtils.randomAlphanumeric(20);
			user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
			user.setSalt(salt);
		}
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
		baseMapper.updateAll(user);
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
		// 查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());
		// 判断是否越权
		if (!roleIdList.containsAll(user.getRoleIdList())) {
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}

	/**
	 * 修改时检查角色是否越权
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
		// 查询该用户上级角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(entity.getCreateUserId());
		// 判断是否越权
		if (!roleIdList.containsAll(user.getRoleIdList())) {
			throw new RRException("所选权限不在用户上级权限中");
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

	@Override
	@Transactional
	public void saveProxy(SysUserEntity user) {
		// 1.保存代理商
		user.setCreateTime(new Date());//创建时间
		user.setRoleIds(Constant.PROXY_ROLE.toString());//默认代理商角色的id
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());// sha256加密
		user.setSalt(salt);
		this.baseMapper.insert(user);//这边是userId 不是id 所以mybatisplus不能回写id
		//2.代理商与角色关系表新增一条记录
		SysUserEntity sys=new SysUserEntity();sys.setUsername(user.getUsername());
		SysUserEntity sysUser = this.baseMapper.selectOne(sys);
		SysUserRoleEntity  sysUserRole=new SysUserRoleEntity();
		sysUserRole.setRoleId(Constant.PROXY_ROLE);
		sysUserRole.setUserId(sysUser.getUserId());
		sysUserRoleService.insert(sysUserRole);
	}

	@Override
	public void updateTotalCoins(String name,Long total) {
		//先根据传过来账户名称，查询一条用户记录
		SysUserEntity entity = new SysUserEntity();
		entity.setUsername(name);
		entity.setProxyBalance(total);
		sysUserDao.updateTotalCoins(entity);//更新
	}

	@Override
	public void updateProxyTotalCoins(String name, Long proxyBalance, Long proxySaleAmount) {
		// 先根据传过来账户名称，查询一条用户记录
		SysUserEntity entity = new SysUserEntity();
		entity.setUsername(name);
		entity.setProxyBalance(proxyBalance);
		entity.setProxySaleAmount(proxySaleAmount);
		sysUserDao.updateProxyTotalCoins(entity);// 更新

	}

}
