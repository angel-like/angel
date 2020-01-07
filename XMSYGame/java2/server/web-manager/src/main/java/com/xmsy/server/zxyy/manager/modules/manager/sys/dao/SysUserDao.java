package com.xmsy.server.zxyy.manager.modules.manager.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.base.BaseDao;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.param.SysUserDetail;

/**
 * 系统用户
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:34:11
 */
public interface SysUserDao extends BaseDao<SysUserEntity> {

	/**
	 * 插入返回主键
	 * 
	 * @param userId
	 *            用户ID
	 */
	void insertUser(SysUserEntity sysUserEntity);

	/**
	 * 查询用户的所有权限
	 * 
	 * @param userId
	 *            用户ID
	 */
	List<String> queryAllPerms(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 根据用户名列表，查询系统用户
	 * 
	 * @param username
	 * @return
	 */
	List<SysUserEntity> queryByUserNameList(@Param("userNameList") String[] userNameList);

	/**
	 * 获取用户角色
	 * 
	 * @param page
	 * @param sysUserDetail
	 * @return
	 */
	List<SysUserDetail> findSysUserDetails(Pagination page, @Param("sysUserDetail") SysUserDetail sysUserDetail);

	/**
	 * 修改用户信息
	 * 
	 * @param sysUser
	 * @return
	 */
	boolean updateAll(@Param("sysUser") SysUserEntity sysUser);


	SysUserEntity findAll(String name);

	void updateTotalCoins(@Param("userEntity")SysUserEntity userEntity);
	
	/**
	 * 划拨时更新代理商的余额与售卖金额
	 * @param name
	 * @param proxyBalance
	 * @param proxySaleAmount
	 */
	void updateProxyTotalCoins(@Param("userEntity")SysUserEntity userEntity);
}
