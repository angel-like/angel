package com.xmsy.server.zxyy.manager.modules.manager.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.param.SysUserDetail;


/**
 * 系统用户
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUserEntity> {
	
	PageUtils findSysUserDetails(PageParam pageParam, SysUserDetail sysUserDetail);


	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
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
	 * 保存用户
	 */
	void save(SysUserEntity user);
	
	/**
	 * 保存代理商
	 */
	void saveAgency(SysUserEntity user);
	
	/**
	 * 修改用户
	 * @param operateUserId 
	 */
	void update(SysUserEntity user);
	/**
	 * 修改代理商
	 */
	void updateAgency(SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);
	
	/**
	 * 删除代理商
	 */
	void deleteAgency(Long userId);


	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);
	/**
	 * 插入 新增的代理商
	 * 		同时代理商与角色关系表新增一条记录
	 * @param user
	 */
	void saveProxy(SysUserEntity user);

	//更新用户余额金币
	void updateTotalCoins(String name,Long total);
	/**
	 * 划拨时更新代理商的余额与售卖金额
	 * @param name
	 * @param proxyBalance
	 * @param proxySaleAmount
	 */
	void updateProxyTotalCoins(String name,Long proxyBalance,Long proxySaleAmount);
}
