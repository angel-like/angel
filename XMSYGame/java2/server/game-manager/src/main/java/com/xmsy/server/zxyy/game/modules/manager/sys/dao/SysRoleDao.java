package com.xmsy.server.zxyy.game.modules.manager.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xmsy.server.zxyy.game.base.BaseDao;
import com.xmsy.server.zxyy.game.modules.manager.sys.entity.SysRoleEntity;

/**
 * 角色管理
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:33:33
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(@Param("createUserId")Long createUserId);
}
