package com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.dao.HierarchyGameRoleDao;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.service.HierarchyGameRoleService;


@Service("hierarchyGameRoleService")
public class HierarchyGameRoleServiceImpl extends ServiceImpl<HierarchyGameRoleDao, HierarchyGameRoleEntity> implements HierarchyGameRoleService {

	@Override
	public void deleteByHierarchyId(Long hierarchyId) {
		// TODO Auto-generated method stub
		baseMapper.deleteByHierarchyId(hierarchyId);
		
	}

	@Override
	public void deleteByGameId(Long id) {
		// TODO Auto-generated method stub
		baseMapper.deleteByGameId(id);
	}


}
