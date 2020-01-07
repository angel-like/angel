package com.xmsy.server.zxyy.manager.modules.manager.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.MapUtils;
import com.xmsy.server.zxyy.manager.modules.manager.sys.dao.SysUserRoleDao;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserRoleEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysUserRoleService;



/**
 * 用户与角色对应关系
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.deleteByMap(new MapUtils().put("user_id", userId));
		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}
		//保存用户与角色关系
		List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
		for(Long roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
			list.add(sysUserRoleEntity);
		}
		this.insertBatch(list);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

	@Override
	public List<Long> queryUserIdList(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}
}
